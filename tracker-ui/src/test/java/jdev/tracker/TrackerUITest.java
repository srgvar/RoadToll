package jdev.tracker;

import jdev.dto.PointDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by srgva on 18.07.2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class TrackerUITest {
    private final String testStrings[] =
            {"\"lat\":56.",
                    "\"lon\":84.",
                    "\"bearing\":99.",
                    "\"speed\":111.",
                    "\"autoId\":\"e070ao\"",
                    "\"timeStamp\":"};

    @Test
    public void testPointDTO() {
        PointDTO p1 = new PointDTO();
        p1.setLat(56.49771);
        p1.setLon(84.97437);
        p1.setAutoId("e070ao");
        long currentTime = System.currentTimeMillis();
        p1.setTimeStamp(currentTime);
        p1.setSpeed(111.0);
        p1.setBearing(99.0);

        // Тест работы сеттеров и преобразования в json-строку
        String json = p1.toJson();
        for (String testString : testStrings) {
            assertTrue(json.contains(testString));
        }
    }


}
