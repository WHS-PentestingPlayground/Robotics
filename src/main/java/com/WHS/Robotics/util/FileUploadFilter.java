package com.WHS.Robotics.util;

import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.ServletContext;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileUploadFilter {
    public static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");

    public static String validateAndSaveImage(MultipartFile file, String path, ServletContext servletContext) throws Exception {
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
        if ((path + fileName).equals("/uploads/img/")) {
            return null;
        }

        String uploadDir = servletContext.getRealPath("/");
        File dest = new File(uploadDir, path + fileName);
        File parent = dest.getParentFile();
        if (!parent.exists()) parent.mkdirs();
        file.transferTo(dest);

        return path + fileName;
    }

    public static String filterXSS(String value) {
        if (value == null) return null;
        return value
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll("\"", "&quot;")
            .replaceAll("'", "&#x27;")
            .replaceAll("/", "&#x2F;");
    }
} 