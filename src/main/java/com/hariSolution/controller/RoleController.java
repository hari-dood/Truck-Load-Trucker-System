package com.hariSolution.controller;

import com.hariSolution.DTOs.AuthResponse;
import com.hariSolution.DTOs.RoleInfoDTO;
import com.hariSolution.model.DataResponse;
import com.hariSolution.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController  // Marks the class as a Spring REST controller
@RequestMapping("/api/v1/role")  // Specifies the base URL for all role-related API endpoints
@RequiredArgsConstructor  // Automatically generates a constructor for required final fields (roleService)
public class RoleController {

    private final RoleService roleService;  // Service to handle business logic related to roles

    // Endpoint for creating a new role
    @PostMapping("/createRole")  // Maps POST requests to /api/v1/role/createRole
    public ResponseEntity<AuthResponse> createRole(@RequestBody @Valid RoleInfoDTO roleDto) {
        // The @Valid annotation ensures that the input data is validated against the RoleInfoDTO constraints
        // Calls the service to handle role creation logic and returns the response
        AuthResponse response = this.roleService.createRole(roleDto);
        return ResponseEntity.ok(response);  // Returns the created role's response
    }

    // Endpoint for getting all role information
    @GetMapping("/get-allRole")  // Maps GET requests to /api/v1/role/get-allRole
    public ResponseEntity<DataResponse> getAllRoleInformation() {
        // Calls the service to fetch all role information and returns it
        DataResponse response = this.roleService.getAllRoleInformation();
        return ResponseEntity.ok(response);  // Returns the fetched role data with HTTP status 200 (OK)
    }

    // Endpoint for deleting a role by its ID
    @DeleteMapping("/delete/{id}")  // Maps DELETE requests to /api/v1/role/delete/{id}
    public ResponseEntity<AuthResponse> deleteRole(@PathVariable("id") Integer role_id) throws RoleNotFoundException {
        // The @PathVariable annotation binds the role ID from the URL to the method's parameter
        // Calls the service to delete the role and returns the response
        AuthResponse response = this.roleService.deleteRole(role_id);
        return ResponseEntity.ok(response);  // Returns a response confirming the role has been deleted
    }
}
