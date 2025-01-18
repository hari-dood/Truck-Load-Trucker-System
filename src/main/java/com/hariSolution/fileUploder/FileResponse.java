package com.hariSolution.fileUploder;

import lombok.Data;
import lombok.RequiredArgsConstructor;

public class FileResponse {

    // Fields representing the file's metadata for the response
    private String fileName;  // Name of the uploaded file
    private String downloadUrl;  // URL to download the file
    private String fileType;  // Type of the file (e.g., "application/pdf", "image/jpeg")
    private long fileSize;  // Size of the file in bytes

    // No-argument constructor
    // This is a default constructor that initializes the object with default values.
    public FileResponse() {
    }

    // All-argument constructor
    // This constructor initializes the object with values passed for all fields.
    public FileResponse(String fileName, String downloadUrl, String fileType, long fileSize) {
        this.fileName = fileName;
        this.downloadUrl = downloadUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    // Getters and Setters
    // These methods allow access to the private fields and modification of their values.

    public String getFileName() {
        return fileName;  // Returns the file name
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;  // Sets the file name
    }

    public String getDownloadUrl() {
        return downloadUrl;  // Returns the file's download URL
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;  // Sets the download URL
    }

    public String getFileType() {
        return fileType;  // Returns the file type (MIME type)
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;  // Sets the file type
    }

    public long getFileSize() {
        return fileSize;  // Returns the file size in bytes
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;  // Sets the file size
    }
}
