package jdev.tracker;

import jdev.dto.PointDTO;
import jdev.tracker.services.DataSaveService;
import jdev.tracker.services.DataSendService;
import jdev.tracker.services.GpsService;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by srgva on 17.07.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class TrackerTest {
    private final String SERVER_ADDRESS = "http://localhost:9090";

    private final String testStrings[] =
            {"\"lat\":56.",
                    "\"lon\":84.",
                    "\"bearing\":99.",
                    "\"speed\":111.",
                    "\"autoId\":\"e070ao\"",
                    "\"time\":"};

    @InjectMocks
    private GpsService gpsMock = new GpsService();
    @InjectMocks
    private DataSaveService dataSaveServiceMock = new DataSaveService();


    @InjectMocks
    private DataSendService dataSendServiceMock = new DataSendService();

    @Before
    public void init() {
        gpsMock.setAutoId("integr123");
        gpsMock.setCoordinates(gpsMock.readCoordinates(".\\\\resource\\\\tracks\\\\10158.kml"));
        for(int i = 0; i < 10; i++) {
            gpsMock.put();
        }

        for(PointDTO point : gpsMock.getGpsQueue()) {
            dataSaveServiceMock.put();
        }

        dataSendServiceMock.setServerURL(SERVER_ADDRESS);
    }

    /* Интеграционные тесты - необходим RESTFul-контроллер
     * по адресу SERVER_ADDRESS */


    @Test
    public void testPointDTO () throws IOException {

        // String autoId = "a123bc";
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

        // интеграционный тест для RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9090/tracker";
        HttpEntity<PointDTO> sendEntity = new HttpEntity<>(p1, getHeaders());

        ResponseEntity<PointDTO> response = restTemplate.postForEntity(url,  sendEntity, PointDTO.class);
        PointDTO pr1 = response.getBody();

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertTrue(p1.equals(pr1));
    }

    // интеграционный тест для треккера
    @Test
    public void integrationTest () throws IOException {

        dataSendServiceMock.dataSend();
        // Очередь сервиса хранения после передачи - пуста
        assertEquals(0, DataSaveService.getSaveQueue().size());
    }


    private static HttpHeaders getHeaders(){
        String plainCredentials="tracker:tracker";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        return headers;
    }
}
