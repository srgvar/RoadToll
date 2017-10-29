package jdev.server.controllers;

import javafx.collections.transformation.SortedList;
import jdev.dto.PointDTO;
import jdev.server.services.RoutesService;
import jdev.server.services.UsersService;
import jdev.users.User;
import jdev.users.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
class ClientController {
    private User user;
    private UsersService usersService;
    private RoutesService routesService;
    //private PaymentsService paymentsService;
    private UsersRepository usersRepository;

    private ArrayList<PointDTO> pointsList;
    private RequestRoute requestRoute;// = new RequestRoute();


    ClientController(@Autowired UsersService usersService, @Autowired RoutesService routesService){
        this.usersRepository = usersRepository;
        this.routesService = routesService;
    }

    @RequestMapping(value = "/")
    public String toHome(){
      return "redirect:/home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView getHome(){
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("pages/home");
        final String userName = SecurityContextHolder.getContext().getAuthentication().getName();

      return modelAndView;
    }

    @RequestMapping(value = "/routes", method = RequestMethod.GET)
    public ModelAndView getRoutes(){
        ModelAndView modelAndView = new ModelAndView();
        if(requestRoute == null)
            requestRoute = new RequestRoute();

        modelAndView.setViewName("pages/routes");
        modelAndView.addObject("requestRoute", requestRoute);
        modelAndView.addObject("pointsList", pointsList);
        return modelAndView;
    }

    @RequestMapping(value = "/routes/save", method = RequestMethod.POST)
    public String savePoint(@ModelAttribute("point") @Valid PointDTO point,
                            BindingResult bindingResult,
                            Model model,
                            final RedirectAttributes redirectAttributes){

         if(point.isNew()){
             if(routesService.exist(point)){
                 model.addAttribute("error","Точка положения авто уже существует");
                 redirectAttributes.addAttribute("point", point);
                 redirectAttributes.addAttribute("errMsg","Точка положения авто уже существует");
                 return "redirect:/routes/add";
             } else {
                 routesService.save(point);
             }
         } else {
             routesService.save(point);
         }

        pointsList =  routesService.getScopeByAutoId(requestRoute.getAutoId(), requestRoute.getScope());
      return "redirect:/routes";
    }

    @RequestMapping(value = "/routes/add", method = RequestMethod.GET)
    public ModelAndView addPoint(){
        ModelAndView modelAndView = new ModelAndView();
        PointDTO point = new PointDTO();
        point.setTimeStamp(System.currentTimeMillis());
        point.setAutoId(requestRoute.getAutoId());
        modelAndView.addObject("point", point);
        modelAndView.addObject("action","add");
        modelAndView.setViewName("pages/point");
        return modelAndView;
    }

    @RequestMapping(value = "/routes/{id}/edit", method = RequestMethod.GET)
    public ModelAndView getPointForEdit(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/point");
        PointDTO point = routesService.getPoint(id);
        modelAndView.addObject("point", point);
        return modelAndView;
    }

    @RequestMapping(value = "/routes", method = RequestMethod.POST)
    public ModelAndView getRoutes(@ModelAttribute("routes") @Valid RequestRoute requestRoute,
                          BindingResult bindingResult,
                          Model model,
                          final RedirectAttributes redirectAttributes){
        this.requestRoute = requestRoute;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/routes");
        modelAndView.addObject("requestRoute", this.requestRoute);
        pointsList =  routesService.getScopeByAutoId(this.requestRoute.getAutoId(), this.requestRoute.getScope());
        modelAndView.addObject("pointsList", pointsList);
        return modelAndView;
    }



    @RequestMapping(value = "/routes/{id}/del")
    public ModelAndView getPoint(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/delPoint");
        PointDTO point = routesService.getPoint(id);
        modelAndView.addObject("point", point);

        return modelAndView;
    }


    @RequestMapping(value = "/routes/{id}/del", method = RequestMethod.POST)
    public ModelAndView delPoint(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/routes");
        routesService.delete(id);
        pointsList =  routesService.getScopeByAutoId(requestRoute.getAutoId(), requestRoute.getScope());
        modelAndView.addObject("requestRoute", requestRoute);
        modelAndView.addObject("pointsList", pointsList);
        return modelAndView;
    }





}
