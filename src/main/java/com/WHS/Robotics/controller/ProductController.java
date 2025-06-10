package com.WHS.Robotics.controller;

import com.WHS.Robotics.entity.Product;
import com.WHS.Robotics.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType; // MediaType import 추가
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware; // ServletContextAware 추가
import jakarta.servlet.ServletContext; // jakarta.servlet.ServletContext import 추가

import java.nio.file.Files; // Files import 추가
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController implements ServletContextAware { // implements ServletContextAware 추가

    @Autowired
    private ProductRepository productRepository;

    private ServletContext servletContext; // ServletContext 변수 선언

    @Override
    public void setServletContext(ServletContext servletContext) { // ServletContext 설정 메서드 구현
        this.servletContext = servletContext;
    }

    @GetMapping("/products")
    public String getProducts(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Product> products = productRepository.findProducts(search, 3);
        model.addAttribute("products", products);
        model.addAttribute("search", search);
        return "products";
    }

    @GetMapping("/product-image/{filename:.+}")
    public ResponseEntity<Resource> serveProductImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get("src/main/webapp/uploads/robots").resolve(filename).normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // 1. 파일 확장자를 기반으로 MIME 타입 추론
            String contentType = null;
            try {
                contentType = Files.probeContentType(imagePath);
            } catch (Exception e) {
                // MIME 타입 추론 실패 시 기본값 설정 (예: application/octet-stream)
                contentType = "application/octet-stream";
            }

            // 2. ServletContext를 사용하여 MIME 타입 추론 (더 정확한 경우 사용)
            if (contentType == null && servletContext != null) {
                contentType = servletContext.getMimeType(resource.getFile().getAbsolutePath());
            }

            // 3. SVG 파일에 대한 명시적 처리 (선택 사항이지만, 문제 해결에 도움)
            if (filename.toLowerCase().endsWith(".svg")) {
                contentType = "image/svg+xml";
            }

            // 4. Content-Type 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            if (contentType != null) {
                headers.setContentType(MediaType.parseMediaType(contentType));
            }
            headers.setContentDispositionFormData("inline", StringUtils.cleanPath(filename));


            return ResponseEntity.ok()
                    .headers(headers) // headers를 설정
                    .body(resource);
        } catch (Exception e) {
            // 로깅을 추가하여 어떤 오류가 발생했는지 확인하는 것이 좋습니다.
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}