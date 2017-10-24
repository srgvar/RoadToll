package jdev.server.controllers;

import jdev.server.services.UsersService;
import jdev.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

//@RolesAllowed("MANAGER")
@Controller /* объявляем, что данный класс является контроллером */
@RequestMapping("/admin")
public class UsersController {
    private final static Logger log = LoggerFactory.getLogger(UsersController.class);
    private UsersService usersService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    UsersController(@Autowired UsersService usersService)
    {
      this.usersService = usersService;
    }


    @RequestMapping(value = "/registerClient", method = RequestMethod.GET)
    public ModelAndView getClients() {
        String path;
        ModelAndView mav = new ModelAndView();
        mav.setViewName("pages/admin/registerClient"); /* шаблон модели в файле /WEB-INF/views/users/list.jspx */
        Iterable<User> users = usersService.getAll();
        path="registerClient/";

        mav.addObject("path", path);
        mav.addObject("users", users);
        return mav;
    }

      @RequestMapping(value = "/registerClient/add", method = RequestMethod.GET)
          public ModelAndView addClient(){
           ModelAndView modelAndView = new ModelAndView();
           User user = new User();
           modelAndView.addObject("user", user);
           modelAndView.addObject("action","add");
          modelAndView.setViewName("pages/admin/client");
          return modelAndView;
    }


    @RequestMapping(value = "/registerClient", method = RequestMethod.POST)
    public String addClient(@ModelAttribute("client") @Valid User user,
                            BindingResult bindingResult,
                            Model model,
                            final RedirectAttributes redirectAttributes) {

        User user1 = usersService.getByUsername(user.getUsername());

        if(user.isNew())
            if(user1 != null) {
                model.addAttribute("errorMsg", "user " + user1.getUsername() + " exist");
                return "redirect:/admin/registerCliend/add";
            }else
              user.setPassword(passwordEncoder.encode(user.getUsername()));
          else{
              user1 = usersService.getById(user.getId());
              user.setPassword(user1.getPassword());
          }
          usersService.save(user);
        return "redirect:/admin/registerClient";
    }

    @RequestMapping(value = "/registerClient/{id}/edit", method = RequestMethod.GET)
    public ModelAndView updateClient(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = usersService.getById(id);
        System.out.println("Edit user with ID = " + user.getId());
            modelAndView.addObject("user", user);
            modelAndView.setViewName("pages/admin/client");
        return modelAndView;
    }

    @RequestMapping(value = "/registerClient/{id}/del", method = RequestMethod.GET)
    public ModelAndView toDeleteClient(@PathVariable("id") Integer id ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/admin/delClient");
        modelAndView.addObject("user", usersService.getById(id));
        return modelAndView;
    }

    @RequestMapping(value = "/registerClient/{id}/del", method = RequestMethod.POST)
    public String deleteClient(@PathVariable("id") Integer id ){
        System.out.println("ID = " + id);
              usersService.delete(id);
        return "redirect:/admin/registerClient";
    }

}
