package com.example.schedulingtasks;


import com.project.reportmanager.scheduler.ReportScheduler;
import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;


import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ReportSchedulerTest {

    @MockitoSpyBean
    ReportScheduler tasks; // Örnekteki ScheduledTasks yerine senin scheduler sınıfın

    @Test
    public void reportCurrentTime() {
        // En fazla 10 saniye boyunca bekler ve metodun en az 2 kez tetiklendiğini doğrular
        await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> {
            verify(tasks, atLeast(2)).fetchExternalData(); // Senin metodunun adı
        });
    }
}