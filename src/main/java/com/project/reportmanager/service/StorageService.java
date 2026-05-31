package com.project.reportmanager.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
    private final Path root = Paths.get("uploads");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Depolama klasörü oluşturulamadı!", e);
        }
    }

    public void save(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Boş dosya yüklenemez.");
            }
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Dosya yüklenemedi: " + e.getMessage());
        }
    }

    // Kılavuz: Uploading Files - Tüm dosyaları Path akışı olarak listeleme
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Yüklenmiş dosyalar okunamadı!", e);
        }
    }

    // Kılavuz: Uploading Files - Dosyayı indirmek için Resource tipinde yükleme
    public Resource loadAsResource(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Dosya bulunamadı veya okunabilir değil: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Dosya yolu hatası: " + filename, e);
        }
    }
}