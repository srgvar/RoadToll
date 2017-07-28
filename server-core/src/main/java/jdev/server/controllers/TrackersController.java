package jdev.server.controllers;

import jdev.dto.PointDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by srgva on 28.07.2017.
 */

@Component
@RestController
public class TrackersController {
    private static final Logger log = LoggerFactory.getLogger(TrackersController.class);

    //@Autowired
    //RestTemplate restTemplate;

    @RequestMapping("/hi")
    String getHtml(){
    String ret = "<html>\n" +
                    "<head>\n" +
                    "    <title>HI Title</title>\n" +
                    "</head>\n" +
                    "<body>" + "GET from SimpleServlet\n"+
            "<p>Удача улыбнулась Вам!\n" +
            "</body>\n" +
            "</html>";
        return ret;
    }
    @RequestMapping(value="/tracker", method = RequestMethod.GET )
    public String getPointDTO(@RequestParam(value = "point") String pointGet){
        System.out.println(pointGet);
        /*
        String response;
        PointDTO point = new PointDTO();
        point.fromJson(pointGet);

        if (point != null) {
            response = "SUCC";
            log.info("Trackers Controller success get: " + point.toString());
        } else {
            response = "FAIL";
            log.info("Trackers Controller failure get: " + pointGet);
        }*/
        return "FAIL";
    }

}
