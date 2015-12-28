package be.domaindrivendesign;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootApplication
@SpringApplicationConfiguration(classes = DemoConfiguration.class)
@WebAppConfiguration
@IntegrationTest("server.port:8080")
@DatabaseSetup("/datasets/etablissement/etablissements_testdto.xml")
public class DemoApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);
    }
}
