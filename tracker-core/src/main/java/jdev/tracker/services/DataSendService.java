package jdev.tracker.services;

import jdev.dto.PointDTO;
import jdev.dto.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;


/**
 * Created by srgva on 23.07.2017.
 */
@Service
@EnableScheduling
public class DataSendService {
    public static final String successResponse = new Response(Response.L_SUCCESS, Response.S_SUCCESS).toJson();

    /** Логгер сервиса передачи */
    private static final Logger log = LoggerFactory.getLogger(DataSendService.class);

    // сервис хранения
    private static DataSaveService dataSaveService;

    @Value("${serverURL}")
    String serverURL;

    @Scheduled (cron = "${sendSchedule}") // параметры из файла-конфигурации (roadtoll.properties)
    private void dataSend() throws IOException {
        String url = serverURL + "/tracker";
        RestTemplate restTemplate = new RestTemplate();

        // Передаем данные на сервер
        // пока есть данные в очеерди сервиса хранения
        for(PointDTO point: dataSaveService.saveQueue){
            // Передаем данные точки на сервер
            // для каждой точки из очереди сервиса хранения
            // формируем запрос к серверу
            // Отправляем данные на сервер и ожидаем результат
            try {
                ResponseEntity<?> r = restTemplate.postForEntity(url,
                        point, PointDTO.class);
                if (r.getStatusCode() == HttpStatus.CREATED) {
                    log.info(" send to server success: " + dataSaveService.saveQueue.poll());
                } else {
                    log.error(" send to server FAILURE, error code: " +  r +
                            " for point:" + point);
                    break;
                }
            }catch(Exception e){
                log.info("Send to server exception: " + e.getMessage());
            }
        }
    }
}
