package jdev.server;

import jdev.dto.repo.PointsDbRepository;
import jdev.users.repo.RolesRepository;
import jdev.users.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by srgva on 18.07.2017.
 */


@SpringBootApplication
@ComponentScan({"jdev.server.services","jdev.server.config"})

 class ServerGUI {



   // public static final Role[] roles = {USER, MANAGER, ROOT};

    public static void main(String... args){
        SpringApplication.run(ServerGUI.class, args);    }
}
