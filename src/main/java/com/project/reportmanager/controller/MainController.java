package com.project.reportmanager.controller;

import com.project.reportmanager.model.Report;
import com.project.reportmanager.repository.ReportRepository;
import com.project.reportmanager.service.StorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private ReportRepository reportRepository;

    // 1. Serving Web Content, JPA Veri Listeleme & Dosya Listeleme
    @GetMapping("/")
    public String showForm(Report report, Model model) {
        loadModelAttributes(model);
        return "form";
    }

    // 2. Validating Form Input, Uploading Files & DB Kayıt
    @PostMapping("/")
    public String checkInfo(@Valid Report report, BindingResult bindingResult,
                            @RequestParam("file") MultipartFile file, Model model) {

        if (bindingResult.hasErrors()) {
            loadModelAttributes(model);
            return "form";
        }

        // Veritabanına Kayıt İşlemi (JPA Write)
        reportRepository.save(report);

        // Dosya Yükleme İşlemi
        if (!file.isEmpty()) {
            storageService.save(file);
            model.addAttribute("message", "Rapor veritabanına başarıyla kaydedildi ve dosya yüklendi: " + file.getOriginalFilename());
        } else {
            model.addAttribute("message", "Rapor veritabanına kaydedildi ancak yüklenecek dosya bulunamadı.");
        }

        return "results";
    }

    // Kılavuz: Uploading Files - Dosyaların sunucudan güvenli indirilmesini sağlayan endpoint
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    // Tekrarlanan model yüklemelerini engellemek için yardımcı metot
    private void loadModelAttributes(Model model) {
        model.addAttribute("reports", reportRepository.findAll());
        model.addAttribute("files", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(MainController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));
    }
}