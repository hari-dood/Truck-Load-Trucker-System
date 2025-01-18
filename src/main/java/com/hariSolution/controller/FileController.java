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

@RestController  // Marks the class as a Spring REST controller to handle HTTP requests
@RequiredArgsConstructor  // Automatically generates a constructor for required final fields (fileTransferService)
@RequestMapping("/api/v1/file")  // Specifies the base URL path for file-related operations
public class FileController {
    private final FileTransferService fileTransferService;  // Service to handle file upload and download logic

    // Endpoint for uploading a file
    @PostMapping(value = "/upload", produces = "application/json")  // Maps POST requests to /api/v1/file/upload
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Calls the service to save the file to the database and retrieves the file properties
            FileProperties fileProperties = this.fileTransferService.saveFileToDataBase(file);

            // Generates the file's download URL
            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/file/download/")  // Defines the base path for the download URL
                    .path(String.valueOf(fileProperties.getId()))  // Appends the file ID to the URL
                    .toUriString();

            // Returns a response with the file details: name, download URL, file type, and file size
            return new FileResponse(
                    fileProperties.getFileName(),  // File name
                    downloadUrl,  // Generated download URL
                    fileProperties.getFileType(),  // File type (MIME type)
                    file.getSize()  // File size in bytes
            );

        } catch (Exception e) {
            // If an error occurs, throw an exception with a custom error message and internal server error status
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed.", e);
        }
    }

    // Endpoint for downloading a file by its ID
    @GetMapping("/download/{file-id}")  // Maps GET requests to /api/v1/file/download/{file-id}
    public ResponseEntity<ByteArrayResource> downloadFromDataBase(@PathVariable("file-id") Long fileId) throws Exception {
        // Calls the service to retrieve the file's properties from the database using the file ID
        FileProperties fileProperties = this.fileTransferService.downloadFromDataBase(fileId);

        // Builds the response with the file content as a ByteArrayResource
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileProperties.getFileType()))  // Sets the correct content type (MIME type)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileProperties.getFileName() + "\"")  // Defines the filename in the content-disposition header
                .body(new ByteArrayResource(fileProperties.getData()));  // Sets the file data as the response body
    }
}
