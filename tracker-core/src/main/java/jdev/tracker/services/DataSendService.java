package jdev.tracker.services;

import jdev.dto.PointDTO;
import jdev.dto.Response;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;


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
        String restRequest;

        // Передаем данные на сервер
        // пока есть данные в очеерди сервиса хранения
        for(PointDTO point: dataSaveService.saveQueue){
            // Передаем данные точки на сервер
            // для каждой точки из очереди сервиса хранения
            // формируем запрос к серверу
            restRequest = serverURL+"/tracker?point=" +
                    //Кодируем параметр для нормализации URL
                    URLEncoder.encode(point.toJson(),"UTF8");

            //Отправляем данные на сервер и ожидаем результат
            String response;
            try {
                response = IOUtils.toString(new URL(restRequest), "UTF8");
                if (response.equals(successResponse)) {
                    log.info(" send to server success: " + dataSaveService.saveQueue.poll());
                } else {
                    log.info(" send to server FAILURE: " + point);
                    break;
                }
            }catch(Exception e){
                log.info("ERROR to server sent: " + e.getMessage());
            }
        }
    }
}
