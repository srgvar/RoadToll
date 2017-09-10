package jdev.server.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//import javax.servlet.http.HttpServletRequest;


/**
 * Created by srgva on 18.07.2017.
 */
// @Configuration

@SpringBootApplication
@ComponentScan({"jdev.server.controllers"})
public class ServerCore {
    public static void main(String[] args) {
        SpringApplication.run(ServerCore.class, args);
    }


}
