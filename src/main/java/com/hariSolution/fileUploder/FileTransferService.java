package com.hariSolution.fileUploder;

import com.hariSolution.repository.FileTransferRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileTransferService {

    private final FileTransferRepository transferRepository;

    public FileProperties saveFileToDataBase(MultipartFile file) throws Exception {
        // Clean and validate the file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {
            throw new IllegalArgumentException("Filename contains invalid path sequence: " + fileName);
        }

        try {
            // Create and populate the Attachment object
            FileProperties attachment = new FileProperties();
            attachment.setFileName(fileName);
            attachment.setFileType(file.getContentType());
            attachment.setData(file.getBytes());

            // Save to the repository
            return this.transferRepository.save(attachment);
        } catch (IOException e) {
            // Handle issues with file content
            throw new IOException("Failed to read file content: " + fileName, e);
        } catch (Exception e) {
            // General exception handling
            throw new Exception("Could not save file: " + fileName, e);
        }




    }


    @Cacheable(value = "shortLivedCache", key = "#fileId")
    public FileProperties downloadFromDataBase(Long fileId) throws Exception {

        return this.transferRepository.findById(fileId)
                .orElseThrow(()->new Exception("File not found with Id"+fileId));
    }
}
