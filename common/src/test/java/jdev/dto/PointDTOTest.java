package jdev.dto;

// import org.junit.Test;

// import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import static org.junit.Assert.assertTrue;


/**
 * Created by srgva on 17.07.2017.
 */
public class PointDTOTest {

    @Test
    public void toJson() throws Exception {
        PointDTO   point = new PointDTO();
        point.setLat(56.49771);
        point.setLon(84.97437);
        point.setAutoId("т070мск");
        point.setTime(System.currentTimeMillis());

        /** тесты преобразований */
        /** из объекта PointDTO в JSON - строку */
        assertTrue(point.toJson().contains("\"lat\":56"));
        assertTrue(point.toJson().contains("\"time\":"));
        System.out.println(point.toJson());
        String json = point.toJson();
        System.out.println("json = " + json);

        /** из JSON-строки в объект PointDTO */
        PointDTO point2 = new PointDTO().fromJson(json);
        point2.setAutoId("a123bcd");
        System.out.println(point2.toJson());




    }


}
