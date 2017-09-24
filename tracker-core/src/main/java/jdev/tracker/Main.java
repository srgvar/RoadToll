package jdev.tracker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.hibernate.cfg.annotations.reflection.JPAMetadataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.query.JpaParameters;
import org.springframework.data.web.config.SpringDataJacksonConfiguration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Created by srgva on 17.07.2017.
 */
//@SpringBootApplication
//@EnableScheduling
//@EnableJpaRepositories("jdev.dto.db")
//@EntityScan(basePackageClasses = jdev.dto.db.PointDB.class)
//@PropertySource("classpath:/application.properties")
//@ComponentScan("jdev.tracker.services")

 public class Main {
    public static void main(String... args){
        ApplicationContext context =
           new AnnotationConfigApplicationContext(TrackerCoreContext.class);
    }
} // class

