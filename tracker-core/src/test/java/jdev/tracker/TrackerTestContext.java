package jdev.tracker;

import jdev.dto.repo.PointsDbRepository;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;



@Configuration
@EnableJpaRepositories("jdev.dto")
@EntityScan(basePackageClasses = jdev.dto.PointDTO.class)
@PropertySource("classpath:/application.properties")
@ComponentScan("jdev.tracker.services")
@EnableAutoConfiguration

public class TrackerTestContext {

    @Resource
    public PointsDbRepository pointsDbRepository;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
