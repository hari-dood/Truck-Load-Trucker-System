package com.hariSolution.controller;

import com.hariSolution.fileUploder.FileProperties;
import com.hariSolution.fileUploder.FileResponse;
import com.hariSolution.fileUploder.FileTransferService;
import jakarta.mail.Header;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayOutputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileController {
    private final FileTransferService fileTransferService;

    @PostMapping(value = "/upload", produces = "application/json")
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileProperties fileProperties = this.fileTransferService.saveFileToDataBase(file);

            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/file/download/")
                    .path(String.valueOf(fileProperties.getId()))
                    .toUriString();

            return new FileResponse(
                    fileProperties.getFileName(),
                    downloadUrl,
                    fileProperties.getFileType(),
                    file.getSize()
            );


        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed.", e);
        }
    }


    @GetMapping("/download/{file-id}")
    public ResponseEntity<ByteArrayResource> downloadFromDataBase(@PathVariable("file-id") Long fileId) throws Exception {
        FileProperties fileProperties = this.fileTransferService.downloadFromDataBase(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileProperties.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileProperties.getFileName() + "\"")
                .body(new ByteArrayResource(fileProperties.getData()));

    }
}
