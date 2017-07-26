package jdev.tracker.service;

import jdev.dto.PointDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by srgva on 23.07.2017.
 */
@Service
@EnableScheduling
public class DataSendService {
    /** Логгер сервиса передачи */
    private static final Logger log = LoggerFactory.getLogger(DataSendService.class);

    private static DataSaveService dataSaveService;

    @Scheduled (cron = "${saveSchedule}") // параметры из файла-конфигурации (roadtoll.properties)
    private void dataSend(){
        // Передаем данные на сервер
        System.out.println("Передаем данные на сервер:");
        // пока есть данные в очеерди сервиса хранения
        for(PointDTO point: dataSaveService.saveQueue){
            // Передаем данные точки на сервер
            System.out.println("Сервис передачи отправляет на сервер сообщение: " + point.toJson());
            // извлекаем точку из очереди сервиса хранения
            point = dataSaveService.saveQueue.poll();
        }
        System.out.println("-----Send OK!-------");
    }
}
