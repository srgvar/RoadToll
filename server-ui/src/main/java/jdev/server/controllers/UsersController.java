package jdev.server.controllers;

import jdev.server.services.UsersService;
import jdev.users.User;
import jdev.users.repo.UsersRepository;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;

@Controller /* объявляем, что данный класс является контроллером */
@RequestMapping("/admin")
class UsersController {
    private final static ThreadLocal<Logger> LOG = ThreadLocal.withInitial(() -> LoggerFactory.getLogger(UsersController.class));
    private UsersService usersService;
    @Autowired
    UserValidator userValidator;
    private Model model;
    private Model model1;


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.setValidator(userValidator);
    }

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    ArrayList<User> users = new ArrayList<>();

    private int usersType = UsersService.ALL_USERS;
    private User user;
    private String action;
    private String path;


    UsersController(@Autowired UsersService usersService)
    {
      this.usersService = usersService;
    }


    @RequestMapping(value = "/registerClient", method = RequestMethod.GET)
    public ModelAndView getClients() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("pages/admin/registerClient"); /* шаблон модели в файле /WEB-INF/views/users/list.jspx */
        this.users = usersService.getUsers(UsersService.CLIENTS);
        this.path="/registerClient";
        mav.addObject("path", path);
        mav.addObject("users", users);
        return mav;
    }

      @RequestMapping(value = "/registerClient/add", method = RequestMethod.GET)
          public ModelAndView addClient(){
           ModelAndView modelAndView = new ModelAndView();
           //if(this.user==null)
               this.user = new User();
               this.action = "add";

           modelAndView.addObject("user", user);
           modelAndView.addObject("action", action);
          modelAndView.setViewName("pages/admin/client");
          return modelAndView;
    }


    @RequestMapping(value = "/registerClient", method = RequestMethod.POST)
    public ModelAndView addClient(@ModelAttribute("client") @Valid User userForSave,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()) {

            LOG.get().info("Binding ERRORS!!! model = " + model);
            System.out.println("client = " + model.containsAttribute("client")
            + " Binding result username error = " + bindingResult.hasFieldErrors("username"));
            System.out.println("username error = " + bindingResult.getFieldError("username"));
            System.out.println(bindingResult.getFieldError("username").getCode());

            /*map.forEach((s, o) -> {

                System.out.println("map: " + s + " = " +  o.toString());
            } );*/
            String errorCode = bindingResult.getFieldError("username").getCode();
            String errName = "";
            switch (errorCode){
                case "NotEmpty.client.username":
                    errName = "Не может быть пустым!!!";
                    break;
                case "Length.client.username" :
                    errName = "Длина от 3 до 20 символов";
            }

            modelAndView.setViewName("pages/admin/client");
            modelAndView.addObject("user", userForSave);
            modelAndView.addObject("action", this.action);
            modelAndView.addObject("errName", errName);
            return modelAndView;
        } else {

            User user1 = usersService.getByUsername(userForSave.getUsername());
            System.out.println("user1 = " + user1);
            if (userForSave.isNew())
                if (user1 != null) {
                modelAndView.setViewName("pages/admin/client");
                modelAndView.addObject("user", userForSave);
                modelAndView.addObject("action",action);
                modelAndView.addObject("errorMsg", "User " + user1.getUsername() + " exist");
                return modelAndView;
                } else {
                    userForSave.setPassword(passwordEncoder.encode(userForSave.getUsername()));
                    usersService.save(userForSave);
                    modelAndView.setViewName("pages/admin/registerClient");
                    this.users = usersService.getUsers(UsersService.CLIENTS);
                    this.path="/registerClient";
                    modelAndView.addObject("path", path);
                    modelAndView.addObject("users", users);
                    redirectAttributes.addFlashAttribute("msg","Информация о " + user.getUsername() + " успешно обновлена!");
                 return modelAndView;
                }

            else {
                user1 = usersService.getById(userForSave.getId());
                userForSave.setPassword(user1.getPassword());
                usersService.save(userForSave);

                modelAndView.setViewName("pages/admin/registerClient");

                this.users = usersService.getUsers(UsersService.CLIENTS);
                this.path="/registerClient";
                modelAndView.addObject("path", path);
                modelAndView.addObject("users", users);
                redirectAttributes.addFlashAttribute("msg","Информация о " + user.getUsername() + " успешно обновлена!");
                return modelAndView;
            }
            //usersService.save(user);
            //modelAndView.setViewName("pages/admin/registerClient");
            //return modelAndView;
        }
    }

    @RequestMapping(value = "/registerClient/{id}/edit", method = RequestMethod.GET)
    public ModelAndView updateClient(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        this.user = usersService.getById(id);
            modelAndView.addObject("user", this.user);
            this.action = "edit";
            modelAndView.addObject("action", this.action);
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
              usersService.delete(id);
        return "redirect:/admin/registerClient";
    }

    @RequestMapping(value = "/registerManager", method = RequestMethod.GET)
    public ModelAndView getManagers(){
        String path;
        ModelAndView mav = new ModelAndView();
        mav.setViewName("pages/admin/registerManager"); /* шаблон модели в файле /WEB-INF/views/users/list.jspx */
        path="/registerManager";
        users = usersService.getUsers(usersType);
        mav.addObject("usersType", usersType);
        mav.addObject("users", users);
        return mav;
    }

    @RequestMapping(value = "/registerManager/all", method = RequestMethod.GET)
    public String getAll(){
        usersType = UsersService.ALL_USERS;
        return "redirect:/admin/registerManager";
    }

    @RequestMapping(value = "/registerManager/mng", method = RequestMethod.GET)
    public String getMng(){
        usersType = UsersService.MANAGERS;
        return "redirect:/admin/registerManager";
    }

    @RequestMapping(value = "/registerManager/cln", method = RequestMethod.GET)
    public String getCln(){
        usersType = UsersService.CLIENTS;
        return "redirect:/admin/registerManager";
    }

    @RequestMapping(value = "/registerManager/{id}/2cln", method = RequestMethod.POST)
    public String ToCln(@PathVariable("id") int id){
        usersService.toClients(id);
        return "redirect:/admin/registerManager";
    }

    @RequestMapping(value = "/registerManager/{id}/2mng", method = RequestMethod.POST)
    public String ToMng(@PathVariable("id") int id){
        usersService.toManagers(id);
        return "redirect:/admin/registerManager";
    }

}
