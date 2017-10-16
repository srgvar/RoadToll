package jdev.server;

import jdev.dto.repo.PointsDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;

@Configuration
@ComponentScan({"jdev.server.controllers"})
@EnableJpaRepositories("jdev.dto")
@EntityScan(basePackageClasses = jdev.dto.PointDTO.class)
@PropertySource("classpath:/application.properties")
@EnableAutoConfiguration
public class ServerTestContext {

    @Autowired
    @Resource
    public PointsDbRepository pointsDbRepository;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
