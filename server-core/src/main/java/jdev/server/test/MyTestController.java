package jdev.server.test;

import jdev.dto.PointDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by srgva on 30.07.2017.
 */
// TODO Мой тестовый контроллер :)
// возвращает полученную строку :))
// для тестирования запросов к серверу
// из финального релиза - убрать !!!
@RestController
public class MyTestController {
    @RequestMapping(value = "/test", method=RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
            //consumes = "text/plain;charset=ISO-8859-1",
           // produces = "text/plain;charset=ISO-8859-1")
    @ResponseBody
    public ResponseEntity<Void> getPoint(@RequestBody PointDTO request) {
        System.out.println("TEST CONTROLLER");
        System.out.println("TEST CONTROLLER - Request: " + request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
