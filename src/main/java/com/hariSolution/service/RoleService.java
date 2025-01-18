package com.hariSolution.service;

import com.hariSolution.DTOs.AuthResponse; // DTO for authentication response
import com.hariSolution.DTOs.RoleInfoDTO; // DTO for role information
import com.hariSolution.mapper.AuthResponseMapper; // Mapper for creating authentication responses
import com.hariSolution.mapper.DataResponseMapper; // Mapper for creating data responses
import com.hariSolution.mapper.RoleMapper; // Mapper for converting role entities to DTOs
import com.hariSolution.model.DataResponse; // Model for data response
import com.hariSolution.model.RoleInfo; // Entity representing role information
import com.hariSolution.repository.RoleRepository; // Repository for interacting with the roles table
import lombok.RequiredArgsConstructor; // Lombok annotation for constructor injection
import org.springframework.cache.annotation.CacheEvict; // Annotation for evicting cache
import org.springframework.cache.annotation.Cacheable; // Annotation for caching results
import org.springframework.http.HttpStatus; // Enum for HTTP status codes
import org.springframework.security.access.prepost.PreAuthorize; // Annotation for securing methods with roles
import org.springframework.stereotype.Service; // Annotation to define a service class

import javax.management.relation.RoleNotFoundException; // Exception thrown if the role is not found
import java.util.Comparator; // Utility class for sorting
import java.util.LinkedHashMap; // Map implementation that preserves insertion order
import java.util.List; // List of roles
import java.util.Map; // Map interface
import java.util.stream.Collectors; // Stream API for processing collections

@Service // Marks this class as a Spring service
@RequiredArgsConstructor // Lombok will generate a constructor to inject required dependencies
public class RoleService {

    // Injecting required dependencies through constructor
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final AuthResponseMapper authResponseService;
    private final DataResponseMapper responseService;

    // Create a new role if it doesn't already exist
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Only accessible by users with 'ROLE_ADMIN'
    public AuthResponse createRole(RoleInfoDTO roleDto) {
        // Check if role already exists
        this.roleRepository.findByName(roleDto.getName()).ifPresent(
                role -> {
                    // If the role already exists, return a conflict response
                    authResponseService.createResponse(
                            "Role is already registered",
                            "Please try a different role.",
                            HttpStatus.CONFLICT);
                }
        );

        // Save the new role
        this.roleRepository.save(roleMapper.toEntity(roleDto));

        // Return a success response
        return this.authResponseService.createResponse(
                "Role created successfully",
                "The role has been registered.", HttpStatus.OK);
    }

    // Retrieve all roles, with caching enabled
    @Cacheable(value = "shortLivedCache") // Caches the result of this method
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Only accessible by users with 'ROLE_ADMIN'
    public DataResponse getAllRoleInformation() {
        // Fetch all roles from the repository
        List<RoleInfo> roles = this.roleRepository.findAll();
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();

        // Convert each role to a DTO and add it to the map
        roles.forEach(role -> {
            RoleInfoDTO roleDto = this.roleMapper.toDTO(role);
            data.put("Role_No:" + role.getId(), roleDto);
        });

        // Sort the roles by ID and return the response
        LinkedHashMap<String, Object> sortedData = getSortedRoles(data);
        return this.responseService.createResponse(sortedData, "Successfully fetched roles!", HttpStatus.OK.value(), HttpStatus.OK, "1");
    }

    // Sort the roles by their ID in ascending order
    public LinkedHashMap<String, Object> getSortedRoles(LinkedHashMap<String, Object> unsortedRoles) {
        return unsortedRoles.entrySet().stream()
                // Sort by the role ID extracted from the key (Role_No:ID)
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(key -> Integer.parseInt(key.split(":")[1]))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    // Delete a role by its ID, with cache eviction
    @CacheEvict(value = "shortLivedCache", key = "#role_Id", beforeInvocation = true) // Evicts the cache before the method is invoked
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Only accessible by users with 'ROLE_ADMIN'
    public AuthResponse deleteRole(Integer role_Id) throws RoleNotFoundException {
        // Fetch the role by its ID
        RoleInfo role = this.roleRepository.findById(role_Id).orElseThrow(() ->
                new RoleNotFoundException("Request role ID not present")
        );

        // If the role is found, delete it from the repository
        if (role.getId().equals(role_Id))
            this.roleRepository.delete(role);

        // Return a success response
        return this.authResponseService.createResponse(
                "Role has been deleted successfully",
                "The role with ID " + role_Id + " has been deleted.",
                HttpStatus.OK
        );
    }
}
