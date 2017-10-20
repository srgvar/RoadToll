package jdev.tracker;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import jdev.dto.PointDTO;
import jdev.dto.repo.PointsDbRepository;
import jdev.tracker.services.DataSaveService;
import jdev.tracker.services.DataSendService;
import jdev.tracker.services.GpsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


/**
 * Created by srgva on 17.07.2017.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = { TrackerTestContext.class },
        loader = AnnotationConfigContextLoader.class)
public class TrackerIntegrationTest {

    private final String SERVER_ADDRESS = "http://localhost:9090";

    private final String testStrings[] =
            {"\"lat\":56.",
                    "\"lon\":84.",
                    "\"bearing\":99.",
                    "\"speed\":111.",
                    "\"autoId\":\"e070ao\"",
                    "\"time\":"};


    @Autowired
    @Resource
    private PointsDbRepository pointsDbRepository;

    @Autowired
    private GpsService gpsService;
    @Autowired
    private DataSaveService dataSaveService;
    @Autowired
    private DataSendService dataSendService;

    // Моким RestTemplate для эмуляции ответов сервера
    @Mock
    private static RestTemplate restTemplateMock = new RestTemplate();


    @Test
    public void testPointDTO () throws Exception {
        PointDTO p1 = new PointDTO();
        p1.setLat(56.49771);
        p1.setLon(84.97437);
        p1.setAutoId("e070ao");
        long currentTime = System.currentTimeMillis();
        p1.setTime(currentTime);
        p1.setSpeed(111);
        p1.setBearing(99);
        List<PointDTO> lp;

        // Тест работы сеттеров и преобразования в json-строку
        String json = p1.toJson();
        for (String testString : testStrings) {
            assertTrue(json.contains(testString));
        }

    // Интеграционный тест для треккера
        List<Coordinate> coordinates = new ArrayList<>();
        for( Double i = 1.0; i < 10.0; i++) {
         Double j = i/1000;
            coordinates.add(new Coordinate(j.toString() +
                             ", " + j.toString()));
        }
        /*for(Coordinate c: coordinates)
            System.out.println(c.toString());*/

        gpsService.setCoordinates(coordinates);
        gpsService.setAutoId("iTest001");

        for(int i = 0; i < 9; i++) {
            gpsService.put();
            Thread.sleep(1000);
        }

        dataSaveService.saveToDb();
        // очередь сервиса хранения пуста
        assertEquals(0, GpsService.getGpsQueue().size());

        List<PointDTO> allInDb = (List<PointDTO>)pointsDbRepository.findAll();
        assertEquals(9, allInDb.size());



        // заглушка для RestTemplate

        dataSendService.setRestTemplate(restTemplateMock);
        final String URL_FOR_REST_TEST = SERVER_ADDRESS + "/tracker";

        when(restTemplateMock
                .postForEntity(eq(URL_FOR_REST_TEST),
                               any(HttpEntity.class),
                               eq(PointDTO.class)))
                .thenAnswer((Answer<ResponseEntity>)
            invocation -> {
            Object[] args = invocation.getArguments();
            HttpEntity httpEntity = (HttpEntity)args[1];
            PointDTO point = (PointDTO)httpEntity.getBody();
            return new ResponseEntity <>(point, null , HttpStatus.CREATED);
        });


        // Тест сервиса передачи данных на сервер
        dataSendService.sendToServer();
        // Записей в локальной БД = 0 (все данные переданы на сервер)
        allInDb = (List<PointDTO>)pointsDbRepository.findAll();
        assertEquals(0, allInDb.size());


    }

    /* Http - заголовки для HttpEntity
    private static HttpHeaders getHeaders(){
        String plainCredentials="tracker:tracker";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        return headers;
    }*/

}
