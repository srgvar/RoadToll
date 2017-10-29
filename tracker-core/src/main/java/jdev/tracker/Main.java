package jdev.tracker;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

