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
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProductController implements ServletContextAware { // implements ServletContextAware 추가

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ServletContext servletContext;

    @GetMapping("/products")
    public String getProducts(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Product> products = productRepository.findProducts(search, 3);
        model.addAttribute("products", products);
        model.addAttribute("search", search);
        return "products";
    }

    @GetMapping("/product-image")
    public ResponseEntity<Resource> serveProductImage(@RequestParam String filename) {
        try {
            String realPath = servletContext.getRealPath("/uploads/robots");
            Path imagePath = Paths.get(realPath).resolve(filename).normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
            String contentDisposition = "inline; filename=\"" + StringUtils.cleanPath(filename) + "\"";
            System.out.println("contentDisposition: " + contentDisposition);

            return ResponseEntity.ok()
                    .headers(headers) // headers를 설정
                    .body(resource);
          
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}