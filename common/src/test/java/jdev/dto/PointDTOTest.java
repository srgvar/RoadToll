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
        PointDTO p1 = new PointDTO();
        PointDTO p2 = new PointDTO();


        double coord[][]={{77.1539,-139.398,-77.1804,-139.55},
                          {77.1539,120.398,77.1804,129.55},
                          {77.1539,-120.398,77.1804,129.55}
        };

        //    Lat1    lon1       lat2   lon2    distance   bearing
        // 	77.1539/-139.398  -77.1804/-139.55 17166029 180.077867811
        //  77.1539/ 120.398   77.1804/ 129.55   225883  84.7925159033
        //  77.1539/-120.398   77.1804/ 129.55	2332669 324.384112704
        double dist1, dist2;
        for(int i = 0; i < 3; i++) {
            p1.setLat(coord[i][0]);
            p1.setLon(coord[i][1]);
            p2.setLat(coord[i][2]);
            p2.setLon(coord[i][3]);
                    dist1 = PointCalculate.getDistance(p1, p2);


            System.out.printf("Distance = %-23.10f\n", dist1);
        }



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

        System.out.println("p2 json=" + p1.toJson());

    }
}
