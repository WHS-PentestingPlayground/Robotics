package com.WHS.Robotics.util;

import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.ServletContext;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileUploadFilter {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");

    public static String validateAndSaveImage(MultipartFile file, String path, ServletContext servletContext) throws Exception {
        // fileName이 비어있으면 확장자 검사 넘어감
        String fileName = (file != null) ? file.getOriginalFilename() : null;
        if (!(fileName == null || fileName.trim().isEmpty())) {
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
                throw new IllegalArgumentException("File extension is missing.");
            }
            String fileExtension = fileName.substring(dotIndex + 1).toLowerCase();
            if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
                throw new IllegalArgumentException("Only image files are allowed.");
            }
        }

        String uploadDir = servletContext.getRealPath("/");
        if (path == null || path.equals("/")) {
            path = "";
        }
        if (!path.isEmpty() && !path.endsWith("/")) {
            path += "/";
        }

        File dest = new File(uploadDir, path + fileName);
        File parent = dest.getParentFile();
        if (!parent.exists()) parent.mkdirs();
        file.transferTo(dest);

        return path + fileName;
    }
} 