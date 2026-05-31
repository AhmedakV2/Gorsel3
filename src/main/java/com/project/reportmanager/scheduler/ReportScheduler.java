package com.project.reportmanager.scheduler;

import com.project.reportmanager.dto.GitHubUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ReportScheduler {

    @Autowired
    private RestTemplate restTemplate;

    // Kılavuz: Scheduling Tasks (Her 10 saniyede bir çalışır)
    @Scheduled(fixedRate = 10000)
    public void fetchExternalData() {
        try {
            // Kılavuz: Consuming a RESTful Web Service (JSON verisi nesneye map ediliyor)
            GitHubUser user = restTemplate.getForObject("https://api.github.com/users/spring-projects", GitHubUser.class);

            log.info("==> Zamanlanmış Görev Başarıyla Çalıştı!");
            if (user != null) {
                log.info("Çekilen Kurumsal Veri -> Organizasyon: {}, Blog: {}, Konum: {}",
                        user.name(), user.blog(), user.location());
            }
        } catch (Exception e) {
            log.error("Harici API verisi tüketilirken hata oluştu: {}", e.getMessage());
        }
    }
}