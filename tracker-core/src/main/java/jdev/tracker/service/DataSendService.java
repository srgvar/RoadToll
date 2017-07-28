package jdev.tracker.service;

import jdev.dto.PointDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by srgva on 23.07.2017.
 */
@Service
@EnableScheduling
public class DataSendService {
    /** Логгер сервиса передачи */
    private static final Logger log = LoggerFactory.getLogger(DataSendService.class);

    // сервис хранения
    private static DataSaveService dataSaveService;

    @Value("${serverURL}")
    String serverURL;

    @Scheduled (cron = "${sendSchedule}") // параметры из файла-конфигурации (roadtoll.properties)
    private void dataSend() throws IOException {
        String restRequest;

        // Передаем данные на сервер
        System.out.println("===== Передаем данные на сервер =====");
        // пока есть данные в очеерди сервиса хранения
        for(PointDTO point: dataSaveService.saveQueue){
            // Передаем данные точки на сервер
            // System.out.println("Сервис передачи отправляет на сервер сообщение: " + point.toJson());
            // извлекаем точку из очереди сервиса хранения
            restRequest = serverURL+"/tracker";
            //?point="+"123";
                    //+ point.toJson();
            System.out.println(restRequest);
            String response = IOUtils.toString(new URL(restRequest),"UTF8");
            if(response.equals("SUCC")){
                log.info("DataSendService success send to server:" + dataSaveService.saveQueue.poll());
            }else{
                log.info("DataSendService fail send to server:" + point);
                break;
            }
        }
        System.out.println("=====================================");
    }
}
