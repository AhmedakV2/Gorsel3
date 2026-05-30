package com.project.reportmanager;

import com.project.reportmanager.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Zamanlanmış görevleri açar
public class ReportManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportManagementSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init(); // Uygulama kalkarken uploads klasörünü açar
        };
    }
}