package jdev.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by srgva on 18.07.2017.
 */


@SpringBootApplication
@ComponentScan({"jdev.server.config"})

 class ServerGUI {

   // public static final Role[] roles = {USER, MANAGER, ROOT};

    public static void main(String... args){
        SpringApplication.run(ServerGUI.class, args);    }
}
