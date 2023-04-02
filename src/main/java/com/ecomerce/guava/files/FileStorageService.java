package com.ecomerce.guava.files;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Logger;

public class FileStorageService {

    Logger log = Logger.getLogger(FileStorageService.class.getName());
    public static String pathString = "upload";
    public static String uploadDirectory = "upload";
    private final Path root = Paths.get(uploadDirectory);
    public static String uploadPath = "upload";


//    @Override
    public List<String> uploadOneFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
        }
        try {
            String fileName = new File(uploadPath + file.getOriginalFilename()).getName();
            file.transferTo(new File(fileName));
        } catch (IOException e) {
            log.info("Error is {} " + e.getMessage());
        }
        return null;
    }

//    @Override
    public String saveFileWithSpecificFileNameV(String fileName, MultipartFile file, String folderName) {
        try {
            Path subDirectory = Paths.get(uploadDirectory + "/" + folderName);
            if (!Files.exists(subDirectory)) {
                Files.createDirectories(subDirectory);
            }
            fileName = new File(subDirectory + "/" + fileName).getName();
//            file.transferTo(new File(fileName));
//            log.info("Path is " + fileName);
            Path targetLocation = subDirectory.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Path is " + fileName);
            return fileName;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

//    @Override
    public Resource load(String filename) {
        try {
            Path file = Paths.get(uploadDirectory)
                    .resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
