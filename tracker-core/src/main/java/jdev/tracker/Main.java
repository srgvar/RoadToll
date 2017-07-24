package jdev.tracker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import jdev.dto.PointDTO;

/**
 * Created by srgva on 17.07.2017.
 */
public class Main {
    public static void main(String... args){
        ApplicationContext context =
           new AnnotationConfigApplicationContext(TrackerCoreContext.class);
    }
} // class

