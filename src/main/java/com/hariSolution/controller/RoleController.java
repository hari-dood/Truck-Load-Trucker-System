package com.hariSolution.controller;

import com.hariSolution.DTOs.AuthResponse;
import com.hariSolution.DTOs.RoleInfoDTO;
import com.hariSolution.model.DataResponse;
import com.hariSolution.model.RoleInfo;
import com.hariSolution.service.RoleService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/createRole")
    public ResponseEntity<AuthResponse> createRole(@RequestBody @Valid RoleInfoDTO roleDto){

        AuthResponse response=this.roleService.createRole(roleDto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-allRole")
    public ResponseEntity<DataResponse>getAllRoleInformation(){
        DataResponse response=this.roleService.getAllRoleInformation();
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AuthResponse>deleteRole(@PathVariable("id") Integer role_id) throws RoleNotFoundException {

        AuthResponse response=this.roleService.deleteRole(role_id);
        return ResponseEntity.ok(response);

    }


}
