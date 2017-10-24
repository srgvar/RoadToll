package jdev.server.controllers;

import jdev.users.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ClientController {

    private UsersRepository usersRepository;

    ClientController(@Autowired UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }


    @RequestMapping(value = "home", method = RequestMethod.GET)
    public ModelAndView getHome(){
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("pages/home");
        final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        final String userFullname = usersRepository.findOneByUsername(userName).getFullname();

      modelAndView.addObject("userFullname", userFullname);

      return modelAndView;
    }

}
