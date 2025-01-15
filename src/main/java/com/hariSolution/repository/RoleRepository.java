package com.hariSolution.repository;


import com.hariSolution.model.RoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleInfo, Integer> {

   Optional<RoleInfo> findByName(String roleName);
}
