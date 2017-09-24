package jdev.tracker.services;

import jdev.dto.PointDTO;
import jdev.dto.db.PointsDbRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by srgva on 23.07.2017.
 *
 * Сервис хранения данных
 */
@Service
@EnableScheduling
public class DataSaveService {


    /** Очередь сервиса харнения */
    private static final BlockingDeque<PointDTO> saveQueue =  new LinkedBlockingDeque<>(300);
    // Логгер сервиса хранения
    private static final Logger log = LoggerFactory.getLogger(DataSaveService.class);

    private List<PointDTO> all;

    private PointsDbRepository pointsDbRepository;

    public DataSaveService(){}


    public DataSaveService(PointsDbRepository pointsDbRepository){
       this.pointsDbRepository = pointsDbRepository;
    }






    /* Используем расписание сервиса GPS */
    @Scheduled(cron = "${gpsSchedule}")
    public void put()  {
        PointDTO point, savedPoint;

        all = (List<PointDTO>) pointsDbRepository.findAll();

        if(all==null){
            log.info("NO RECORDS");
        } else {
            all.stream().forEach(PointDTO -> log.info(PointDTO.toString()));

        }

        try {
            point = GpsService.gpsQueue.take(); // Получаем точку от сервиса GPS
            log.info(System.currentTimeMillis() + " DataSaveService " + point.toString()); //500, TimeUnit.MILLISECONDS));
            /* Сохраняем информацию о точке - в нашем случае
            * помещаем в очередь сервиса хранения */

            savedPoint = pointsDbRepository.save(point);
            if(point.equals(savedPoint)){
                saveQueue.put(point);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static BlockingDeque<PointDTO> getSaveQueue() {
        return saveQueue;
    }

}
