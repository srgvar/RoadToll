package jdev.tracker;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Created by srgva on 21.07.2017.
 */
//@SpringBootApplication
@Configuration
@EnableScheduling
@EnableJpaRepositories("jdev.dto")
@EntityScan(basePackageClasses = jdev.dto.PointDTO.class)
@PropertySource("classpath:/application.properties")
@ComponentScan("jdev.tracker.services")
@EnableAutoConfiguration
public class TrackerCoreContext {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    //@Autowired
    //public PointsDbRepository pointsDbRepository;

    // Шедулер для запуска сервисов
    @Bean
    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler");
        scheduler.setPoolSize(20);
        return scheduler;
    }

}
