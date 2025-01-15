package com.hariSolution.notification;

import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class ImageToBase64 {
    public static String convertImageToBase64(String imagePath) throws IOException {
        byte[] fileContent = Files.readAllBytes(Paths.get(imagePath));
        // Add the necessary MIME type prefix for images
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(fileContent);
    }

    public static void main(String[] args) {
        try {
            String imagePath = "C:\\Users\\Admin\\Desktop\\TLMS\\Truck-load-management-system\\src\\main\\resources\\images\\truck.jpg"; // Replace with your image path
            String base64Image = convertImageToBase64(imagePath);
            System.out.println(base64Image);
        } catch (IOException e) {
            System.out.println("Error occurred while reading the image: " + e.getMessage());
        }
    }
}
