package jdev.tracker.services;

import jdev.dto.PointDTO;
import jdev.dto.db.PointsDbRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by srgva on 23.07.2017.
 *
 * Сервис хранения данных
 */
@Service
//@EnableScheduling
@Transactional
public class DataSaveService {

    // Логгер сервиса хранения
    private static final Logger log = LoggerFactory.getLogger(DataSaveService.class);

    // Интерфейс репозитория
    private PointsDbRepository pointsDbRepository;
    // Конструктор по умолчанию
    //public DataSaveService(){}

    // Конструктор с инициализацией репозитория
    public DataSaveService(@Autowired PointsDbRepository pointsDbRepository){
       this.pointsDbRepository = pointsDbRepository;
    }

    /* Используем расписание сервиса GPS */
    @Scheduled(cron = "${saveSchedule}")
    public void saveToDb()  {
        PointDTO point, savedPoint; // = new PointDTO();
         //pointsDbRepository.;
        point = GpsService.gpsQueue.peek(); // Получаем точку от сервиса GPS
        if(!(point==null)) {
            try {

            /* Сохраняем информацию о точке в БД */
                savedPoint = pointsDbRepository.save(point);
                //
                if (point.equals(savedPoint)) {
                    log.info(" save to database point: " + GpsService.gpsQueue.take());
                } else {
                    log.error(" ERROR saving point: " + point);
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            } //try
        }//if
    }

    public PointsDbRepository getPointsDbRepository() {
        return pointsDbRepository;
    }

}
