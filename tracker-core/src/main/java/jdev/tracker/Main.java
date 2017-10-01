package jdev.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;

/**
 * Created by srgva on 17.07.2017.
 */

 public class Main {
    //@Autowired
    //public RestTemplate restTemplate = new RestTemplate();

    public static void main(String... args){
        ApplicationContext context =
                new AnnotationConfigApplicationContext(TrackerCoreContext.class);
    }
} // class

