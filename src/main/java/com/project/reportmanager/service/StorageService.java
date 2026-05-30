package com.project.reportmanager.service;

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
        try { Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename())); }
        catch (Exception e) { throw new RuntimeException("Dosya yüklenemedi!"); }
    }
}