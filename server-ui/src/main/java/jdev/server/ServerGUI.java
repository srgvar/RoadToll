package jdev.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.Arrays;

/**
 * Created by srgva on 18.07.2017.
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EntityScan(basePackageClasses = {jdev.dto.PointDTO.class,
        jdev.users.User.class,
        jdev.users.UserRole.class})
@EnableJpaRepositories({"jdev.dto", "jdev.users"})
@EnableCaching

 class ServerGUI {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    public static void main(String... args){

        ApplicationContext ctx = SpringApplication.run(ServerGUI.class, args);

/*
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }   */
    }
}
