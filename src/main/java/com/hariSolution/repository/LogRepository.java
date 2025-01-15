package com.hariSolution.repository;

import com.hariSolution.model.LogInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogInfo,Long> {
}
