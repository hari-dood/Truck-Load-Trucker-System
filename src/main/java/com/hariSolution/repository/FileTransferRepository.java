package com.hariSolution.repository;

import com.hariSolution.fileUploder.FileProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileTransferRepository extends JpaRepository<FileProperties,Long> {
}
