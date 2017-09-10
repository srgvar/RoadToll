package jdev.dto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by srgva on 17.07.2017.
 */
public class PointDTOTest {
    // Тестируем классы PointDTO и PointCalculate
private final String testStrings[] = {"\"lat\":56.",
                              "\"lon\":84.",
                              "\"bearing\":99.",
                              "\"speed\":111.",
                              "\"autoId\":\"e070ao\"",
                              "\"time\":"};

/* тестовые значения координат, расстяния и азимута
                              широта 1 долгота1  широта 2 долгота 2 расстояние  азимут */
private final double testPoints[][]={{77.1539, -139.398, -77.1804, -139.55,  17166029, 180.077867811},
                             {77.1539,  120.398,  77.1804,  129.55,    225883,  84.792515903},
                             {77.1539, -120.398,  77.1804,  129.55,   2332669, 324.384112704}
                            };

    @Test
    public void PointDtoTest() throws Exception {
        String autoId = "a123bc";
        PointDTO p1 = new PointDTO();
        p1.setLat(56.49771);
        p1.setLon(84.97437);
        p1.setAutoId("e070ao");
        long currentTime = System.currentTimeMillis();
        p1.setTime(currentTime);
        p1.setSpeed(111);
        p1.setBearing(99);

        // Тест работы сеттеров и преобразования в json-строку
        String json = p1.toJson();
        for (String testString : testStrings) {
            assertTrue(json.contains(testString));
        }
        // Тест конструктора из json-строки
        PointDTO p2 = new PointDTO(json);
        //p2.setBearing(300);
        assert(p1.equals(p2));
    }

    @Test
    public void PointCalculateTest() throws Exception {
        PointDTO p1 = new PointDTO(), p2 = new PointDTO();

        // Тест calculateDistance и calculateBearing класса PointCalculate
       for(int i = 0; i < 3; i++) {
            p1.setLat(testPoints[i][0]);
            p1.setLon(testPoints[i][1]);
            p2.setLat(testPoints[i][2]);
            p2.setLon(testPoints[i][3]);

            assertEquals("Distance calculate error", testPoints[i][4], PointCalculate.calculateDistance(p1, p2),1.0);
            assertEquals("Bearing calculate error", testPoints[i][5], PointCalculate.calculateBearing(p1, p2),0.000000001);
        }

        // Тест calculateSpeed класса PointCalculate
        // расстояние между точками - 1000 метров, разница во времени - 100 секунд
        // скорость = 10 м/с
        p1.setLat(0);
        p1.setLon(0);
        p1.setTime(0);
        p2.setLat(0.008991);
        // p2.setLat(0.00899106829757);
            p2.setLon(0);
            p2.setTime(100000);

        assertEquals("Speed calculate error", 10, PointCalculate.calculateSpeed(p1, p2),1.0);
    }

}
