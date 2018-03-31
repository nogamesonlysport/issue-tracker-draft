package au.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("au.com.repository")
@EntityScan("au.com.domain")
public class IssueTrackerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(IssueTrackerApplication.class, args);
    }
}
