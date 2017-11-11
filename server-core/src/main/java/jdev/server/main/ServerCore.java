package jdev.server.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

//import javax.servlet.http.HttpServletRequest;


/**
 * Created by srgva on 18.07.2017.
 */

@Configuration
@EnableAsync
@EnableAutoConfiguration
@SpringBootApplication
@PropertySource("classpath:/application.properties")
@ComponentScan({"jdev.server.controllers"})
@EntityScan("jdev.dto")
@EnableJpaRepositories("jdev.dto")
@EnableCaching
public class ServerCore {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }



    public static void main(String[] args) {
        SpringApplication.run(ServerCore.class, args);
    }


}
