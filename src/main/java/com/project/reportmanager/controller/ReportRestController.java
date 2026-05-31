package com.project.reportmanager.controller;

import com.project.reportmanager.model.Report;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportRestController {

    // Kılavuz: Building a RESTful Web Service Örneği (Parametrik ve Bağımsız Yapı)
    @GetMapping("/api/report")
    public Report getRestReport(@RequestParam(value = "name", defaultValue = "Dinamik REST Örnek Raporu") String name) {
        Report sampleReport = new Report();
        sampleReport.setId(999L);
        sampleReport.setName(name);
        return sampleReport;
    }
}