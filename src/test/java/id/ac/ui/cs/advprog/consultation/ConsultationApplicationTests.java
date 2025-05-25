package id.ac.ui.cs.advprog.consultation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsultationApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testGetAppName() {
        String appName = ConsultationApplication.getAppName();
        org.junit.jupiter.api.Assertions.assertEquals("ConsultationApplication", appName);
    }

    @Test
    void testGetAppVersion() {
        String version = ConsultationApplication.getAppVersion();
        org.junit.jupiter.api.Assertions.assertEquals("1.0", version);
    }
}
