package jdev.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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

 class ServerGUI {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(String... args){

        ApplicationContext ctx = SpringApplication.run(ServerGUI.class, args);

        /*System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }   */
    }
}
