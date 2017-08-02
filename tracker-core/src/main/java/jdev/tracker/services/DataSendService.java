package jdev.tracker.services;

import jdev.dto.PointDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;


/**
 * Created by srgva on 23.07.2017.
 */
@Service
@EnableScheduling
public class DataSendService {
    /** Логгер сервиса передачи */
    private static final Logger log = LoggerFactory.getLogger(DataSendService.class);

    // сервис хранения
    @Autowired
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
            System.out.println("Сервис передачи отправляет на сервер сообщение: " + dataSaveService.saveQueue.poll().toJson());
        }
        System.out.println("=====================================");
    }
}
