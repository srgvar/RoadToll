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
@RestController
@RequestMapping("/test")
public class MyTestController {

    @RequestMapping(method = RequestMethod.GET)
    //  @ResponseBody
    public String getPoint(@RequestParam("point") String point){
        System.out.println(point);
        return point;
    }
}
