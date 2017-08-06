package jdev.server.test;

import jdev.dto.PointDTO;
import jdev.dto.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by srgva on 30.07.2017.
 */
// Мой тестовый контроллер :)
// возвращает полученную строку :))
// для тестирования запросов к серверу
// из финального релиза - убрать !!!
@RestController
@RequestMapping("/test")
public class MyTestController {

    @RequestMapping(method = RequestMethod.GET)
    //  @ResponseBody
    public String getRequest(@RequestParam("point") String request){
        System.out.println(request);
        return request;
    }
}
