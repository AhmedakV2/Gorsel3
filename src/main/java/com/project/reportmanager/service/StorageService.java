package com.project.reportmanager.service;

import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {
    private final Path root = Paths.get("uploads");

    public void init() {
        try { Files.createDirectories(root); } catch (Exception e) {}
    }

    public void save(MultipartFile file) {
        try {
            // Aynı isimde dosya varsa üzerine yazması için StandardCopyOption.REPLACE_EXISTING ekledik
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (Exception e) {
            throw new RuntimeException("Dosya yüklenemedi: " + e.getMessage());
        }
    }
}