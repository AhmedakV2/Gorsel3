package com.project.reportmanager.controller;

import com.project.reportmanager.model.Report;
import com.project.reportmanager.repository.ReportRepository;
import com.project.reportmanager.service.StorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

    @Autowired
    private StorageService storageService;

    // PostgreSQL veritabanı bağlantımızı enjekte ediyoruz
    @Autowired
    private ReportRepository reportRepository;

    // 1. Serving Web Content & Form Display
    @GetMapping("/")
    public String showForm(Report report) {
        return "form";
    }

    // 2. Validating Form Input, 3. Uploading Files & PostgreSQL Kayıt İşlemi
    @PostMapping("/")
    public String checkInfo(@Valid Report report, BindingResult bindingResult,
                            @RequestParam("file") MultipartFile file, Model model) {

        // Doğrulama hatası varsa (örneğin isim boşsa) aynı sayfaya geri dön
        if (bindingResult.hasErrors()) {
            return "form";
        }

        // --- YENİ EKLENEN KISIM: Accessing Data with JPA ---
        // Formdan gelen veriyi PostgreSQL veritabanına kaydediyoruz
        reportRepository.save(report);

        // Dosya yükleme işlemi
        if (!file.isEmpty()) {
            storageService.save(file);
            model.addAttribute("message", "Rapor veritabanına kaydedildi ve dosya başarıyla yüklendi: " + file.getOriginalFilename());
        } else {
            model.addAttribute("message", "Rapor veritabanına kaydedildi ancak dosya yüklenmedi.");
        }

        return "results";
    }

    // 4. Building a RESTful Web Service
    @GetMapping("/api/report")
    @ResponseBody
    public Report getRestReport() {
        // Dışarıya örnek bir JSON dönüyoruz
        Report sampleReport = new Report();
        sampleReport.setId(101L);
        sampleReport.setName("REST Örnek Raporu");
        return sampleReport;
    }
}