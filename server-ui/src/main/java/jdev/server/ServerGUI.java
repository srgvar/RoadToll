package jdev.server;

import jdev.users.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by srgva on 18.07.2017.
 */


@SpringBootApplication
@ComponentScan({"jdev.server.config"})

 class ServerGUI {
    public static final Role ROOT = new Role(1,"ROOT");
    public static final Role MANAGER = new Role(2, "MANAGER");
    public static final Role USER = new Role(3,"USER");

   // public static final Role[] roles = {USER, MANAGER, ROOT};

    public static void main(String... args){
        SpringApplication.run(ServerGUI.class, args);    }
}
