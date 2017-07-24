package jdev.tracker.service;

import jdev.dto.PointDTO;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
//import java.util.logging.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by srgva on 23.07.2017.
 */
@Service
@EnableScheduling
public class DataSendService {
    /** Логгер сервиса передачи */
    private static final Logger log = LoggerFactory.getLogger(DataSendService.class);
    private static BlockingDeque<PointDTO> saveQueue;
    /** АДрес очереди  сервиса хранения */
    void setSaveQueue(BlockingDeque<PointDTO> saveQueue){
        this.saveQueue = saveQueue;
    }

    @Scheduled (cron = "${saveSchedule}") // параметры из файла-конфигурации (roadtoll.properties)
    private void dataSend(){
        // Передаем данные на сервер
        System.out.println("Передаем данные на сервер:");
        for(PointDTO point: saveQueue){
           log.info("DataSendService send point: " + point.toJson());
            point = saveQueue.poll();
        }
        System.out.println("-----Send OK!-------");

    }


}
