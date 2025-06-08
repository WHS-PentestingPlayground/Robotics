package com.WHS.Robotics.controller;

import com.WHS.Robotics.entity.Product;
import com.WHS.Robotics.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

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
            String contentDisposition = "inline; filename=\"" + StringUtils.cleanPath(filename) + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
} 