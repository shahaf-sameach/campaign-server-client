package example.demo.config;

import example.demo.model.repository.ImpressionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ImpressionRepositoryConfiguration {

    @Bean
    public ImpressionRepository allUsersImpressionRepository() {
        return new ImpressionRepository();
    }

    @Bean
    public ImpressionRepository userImpressionRepository() {
        return new ImpressionRepository();
    }
}
