package jdev.server.main;

import jdev.dto.repo.PointsDbRepository;
import jdev.users.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//import javax.servlet.http.HttpServletRequest;


/**
 * Created by srgva on 18.07.2017.
 */

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@PropertySource("classpath:/application.properties")
@ComponentScan({"jdev.server.controllers"})
@EntityScan("jdev.dto")
@EnableJpaRepositories("jdev.dto")
public class ServerCore {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    public PointsDbRepository pointsDbRepository;
/*
    @Autowired
    public RolesRepository rolesRepository;

    @Autowired
    public UsersRepository usersRepository;
*/
    public static void main(String[] args) {
        SpringApplication.run(ServerCore.class, args);
    }


}
