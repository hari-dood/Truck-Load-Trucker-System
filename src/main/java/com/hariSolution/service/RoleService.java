package com.hariSolution.service;

import com.hariSolution.DTOs.AuthResponse;
import com.hariSolution.DTOs.RoleInfoDTO;
import com.hariSolution.mapper.AuthResponseMapper;
import com.hariSolution.mapper.DataResponseMapper;
import com.hariSolution.mapper.RoleMapper;
import com.hariSolution.model.DataResponse;
import com.hariSolution.model.RoleInfo;
import com.hariSolution.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final AuthResponseMapper authResponseService;
    private final DataResponseMapper responseService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public AuthResponse createRole(RoleInfoDTO roleDto) {

        this.roleRepository.findByName(roleDto.getName()).ifPresent(
                role-> {
                    authResponseService.createResponse(
                            "Role is already registered",
                            "Please try a different role.",
                            HttpStatus.CONFLICT);
                }
        );
        this.roleRepository.save(roleMapper.toEntity(roleDto));

        return this.authResponseService.createResponse(
                "Role created successfully",
                "The role has been registered.",HttpStatus.OK);

    }
    @Cacheable(value = "shortLivedCache")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public DataResponse getAllRoleInformation() {
        List<RoleInfo> roles = this.roleRepository.findAll();
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        roles.forEach(role -> {
            RoleInfoDTO roleDto = this.roleMapper.toDTO(role);
            data.put("Role_No:" + role.getId(), roleDto);
        });

        // Sort the roles before returning the response
        LinkedHashMap<String, Object> sortedData = getSortedRoles(data);

        return this.responseService.createResponse(sortedData, "Successfully fetched roles!", HttpStatus.OK.value(), HttpStatus.OK, "1");
    }

    public LinkedHashMap<String, Object> getSortedRoles(LinkedHashMap<String, Object> unsortedRoles) {
        return unsortedRoles.entrySet().stream()
                // Extract the role number part of the key and sort by it
                .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(key -> Integer.parseInt(key.split(":")[1]))))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    @CacheEvict(value = "shortLivedCache", key = "#role_Id", beforeInvocation = true)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public AuthResponse deleteRole(Integer role_Id) throws RoleNotFoundException {
        RoleInfo role=this.roleRepository.findById(role_Id).orElseThrow(()->
                new RoleNotFoundException("Request role ID not present")
        );
        if(role.getId().equals(role_Id))
            this.roleRepository.delete(role);

        return this.authResponseService.createResponse(
                "Role has been deleted successfully",
                "The role with ID " + role_Id + " has been deleted.",
                HttpStatus.OK
        );


    }
}
