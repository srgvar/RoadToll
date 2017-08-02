package jdev.server.controllers;

/**
 * Created by srgva on 02.08.2017.
 */






        import jdev.dto.PointDTO;
        import jdev.dto.Response;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.ResponseBody;
        import org.springframework.web.bind.annotation.RestController;
        import java.io.UnsupportedEncodingException;
        import java.net.URLDecoder;



@RestController
@RequestMapping("/tracker")
public class TrackersController {
    private static final Logger log = LoggerFactory.getLogger(TrackersController.class);
    private PointDTO pointDTO = new PointDTO();
    Response ret;

    //String pointString = "";

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Response getPoint(@RequestParam("point") String point){
        try {
            // декодируем параметр  из url в json
            point = URLDecoder.decode(point, "UTF8");
            // формируем объект PointDTO из полученной строки
            PointDTO pointDto = new PointDTO(point);
            log.info(" success get: " + pointDto.toString());
            ret = new Response(Response.L_SUCCESS, Response.S_SUCCESS);
        } catch (UnsupportedEncodingException e) {
            log.info(" failure get: " + point);
            ret = new Response(Response.L_FAILURE, Response.S_FAILURE);
            e.printStackTrace();
        }
        return ret;
    }

}

