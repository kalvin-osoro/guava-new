package com.ecomerce.guava.utils;

//public class FileUploadUtil {
//}


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileUploadUtil {

    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(Arrays.asList("png", "jpg", "jpeg", "gif"));

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try {
            Path filePath = uploadPath.resolve(fileName);
            multipartFile.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }
    }

//    public static void cleanDir(String dir) {
//        File directory = new File(dir);
//
//        if (directory.exists()) {
//            File[] files = directory.listFiles();
//            if (files != null) {
//                for (File file : files) {
//                    file.delete();
//                }
//            }
//        } else {
//            directory.mkdir();
//        }
//    }

    public static void cleanDir(String dir) {
        File directory = new File(dir);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        } else {
            directory.mkdirs();
        }
    }

}
