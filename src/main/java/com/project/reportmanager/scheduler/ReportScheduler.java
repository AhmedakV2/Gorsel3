package com.project.reportmanager.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ReportScheduler {

    // Başlık: Scheduling Tasks (Her 10 saniyede bir çalışır)
    @Scheduled(fixedRate = 10000)
    public void fetchExternalData() {

        // Başlık: Consuming a RESTful Web Service
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Spring rehberindeki gibi dışarıdaki bir REST API'den veri okuyoruz (Örn: Github API)
            String result = restTemplate.getForObject("https://api.github.com/users/spring-projects", String.class);

            System.out.println("Zamanlanmış Görev Çalıştı!");
            System.out.println("Dış API'den (Github) çekilen veri özeti: " + result.substring(0, 100) + "...");
        } catch (Exception e) {
            System.out.println("API'ye ulaşılamadı.");
        }
    }
}