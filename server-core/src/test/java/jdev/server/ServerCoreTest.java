package jdev.server;

/*
 * Created by srgva on 18.07.2017.
 */

import jdev.dto.PointDTO;
import jdev.dto.RequestRoute;
import jdev.dto.repo.PointsDbRepository;
import jdev.server.controllers.TrackersController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = { ServerTestContext.class },
        loader = AnnotationConfigContextLoader.class)
/*
* Интеграционный тест ядра сервера
*/
public class ServerCoreTest {
    private final String testStrings[] =
            {"\"lat\":56.",
                    "\"lon\":84.",
                    "\"bearing\":99.",
                    "\"speed\":111.",
                    "\"autoId\":\"e070ao\"",
                    "\"time\":"};

    @Autowired
    private TrackersController tc;

    @Resource
    private PointsDbRepository pointsDbRepository;

    // Тестовый массив для "генерации" точек, сохранения их в БД сервера и сравнения результатов поиска
    private String testJsonPoints[] = {
            "{\"id\":0,\"lat\":43.914639,\"lon\":131.989942,\"bearing\":19.976533720678162,\"speed\":51.36256922793203,\"autoId\":\"test002\",\"timeStamp\":10000}",
            "{\"id\":0,\"lat\":43.915809,\"lon\":131.990533,\"bearing\":19.995108749416882,\"speed\":138.48235297485786,\"autoId\":\"test002\",\"timeStamp\":20000}",
            "{\"id\":0,\"lat\":43.917146,\"lon\":131.991209,\"bearing\":20.01223413157322,\"speed\":158.26588741934378,\"autoId\":\"test002\",\"timeStamp\":30000}",
            "{\"id\":0,\"lat\":43.918126,\"lon\":131.991704,\"bearing\":19.993442157460063,\"speed\":115.99251357160408,\"autoId\":\"test002\",\"timeStamp\":40000}",
            "{\"id\":0,\"lat\":43.919583,\"lon\":131.99244,\"bearing\":19.994640706051626,\"speed\":172.45149828572957,\"autoId\":\"test002\",\"timeStamp\":50000}",
            "{\"id\":0,\"lat\":43.921204,\"lon\":131.993259,\"bearing\":19.997638498442228,\"speed\":191.8663365308212,\"autoId\":\"test002\",\"timeStamp\":60000}",
            "{\"id\":0,\"lat\":43.922419,\"lon\":131.993872,\"bearing\":19.97113978023512,\"speed\":143.7867291465711,\"autoId\":\"test002\",\"timeStamp\":70000}",
            "{\"id\":0,\"lat\":43.923707,\"lon\":131.994738,\"bearing\":25.839781400375955,\"speed\":159.17463936951324,\"autoId\":\"test002\",\"timeStamp\":80000}",
            "{\"id\":0,\"lat\":43.924583,\"lon\":131.995887,\"bearing\":43.371461012974095,\"speed\":134.03849987603533,\"autoId\":\"test002\",\"timeStamp\":90000}",
            "{\"id\":0,\"lat\":43.925104,\"lon\":131.997153,\"bearing\":60.257034156899145,\"speed\":116.80810022014441,\"autoId\":\"test002\",\"timeStamp\":100000}"};



    @Test
    public void serverCoreIntegrationTest(){
        final int trackLength = 6;
        int testIndex = testJsonPoints.length - 1;

        for (String jsonPoint : testJsonPoints) {
            PointDTO point = new PointDTO(jsonPoint);
               ResponseEntity<PointDTO> response = tc.savePoint(point);
                  PointDTO savedPoint = response.getBody();

            assertTrue(point.equals(savedPoint));
        }

        final ResponseEntity<PointDTO[]> reTrack = tc.getTrack(new RequestRoute("test002", trackLength));
        PointDTO track[] = reTrack.getBody();

        /* тестовый массив просматриваем с "хвоста" - с наиболее ранним временем (time),
           а результирующий массив с начала
         */
        for(int i = 0; i < trackLength; i++, testIndex--){
            PointDTO p = new PointDTO(testJsonPoints[testIndex]);
             assertEquals(track[i].getAutoId(),p.getAutoId());
            assertEquals(track[i].getTimeStamp(),p.getTimeStamp());
            assertEquals(track[i].getLat(),p.getLat());
            assertEquals(track[i].getLon(),p.getLon());
            assertEquals(track[i].getSpeed(),p.getSpeed());
            assertEquals(track[i].getBearing(),p.getBearing());



            //assertTrue(new PointDTO(testJsonPoints[testIndex]).equals(track[i]));
        }
    }
}


