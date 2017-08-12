package jdev.server.test;

import jdev.dto.PointDTO;
import org.springframework.web.bind.annotation.*;

/**
 * Created by srgva on 30.07.2017.
 */
// Мой тестовый контроллер :)
// возвращает полученную строку :))
// для тестирования запросов к серверу
// из финального релиза - убрать !!!
@RestController
public class MyTestController {
    @RequestMapping(value = "/test",
            method = RequestMethod.POST)
    @ResponseBody
    public PointDTO getPoint(@RequestBody PointDTO request) {
        System.out.println("TEST CONTROLLER - Request: " + request.toString());
        return request;
    }


 /*   public String getRequest(@RequestParam("point") String request){
        System.out.println(request);
        return request;
    }*/
}
