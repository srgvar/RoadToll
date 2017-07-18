package jdev.dto;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by srgva on 17.07.2017.
 */
public class PointDTOTest {
    @Test
    public void toJson() throws Exception {
        String autoId = "a123bcd";

        PointDTO   point = new PointDTO();
        point.setLat(56.49771);
        point.setLon(84.97437);
        point.setAutoId("т070мск");
        point.setTime(System.currentTimeMillis());

        /** тесты преобразований */
        /** из объекта PointDTO в JSON - строку */
        assertTrue(point.toJson().contains("\"lat\":56"));
        assertTrue(point.toJson().contains("\"time\":"));
        System.out.println(point.toString());

        String json = point.toJson(); // получаем JSON-строку
        System.out.println("point json = " + json);

        /** из JSON-строки в объект PointDTO */
        PointDTO point2 = new PointDTO().fromJson(json);
        point2.setAutoId("a123bcd"); // зададим авто другой номер
        assertEquals(autoId, point2.getAutoId());
        System.out.println("point2 json=" + point2.toJson());

    }
}
