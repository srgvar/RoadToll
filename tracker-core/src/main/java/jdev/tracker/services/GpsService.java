package jdev.tracker.services;

import de.micromata.opengis.kml.v_2_2_0.*;
import jdev.dto.PointCalculate;
import jdev.dto.PointDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
/**
 * Created by srgva on 23.07.2017.
 */
@Service
public class GpsService {

    @Value("${kmlFile}")
    String kmlFileName; //имя файла с координатами - в файле roadtoll.propertis

    @Value("${autoId}")
    String autoId; // номер авто - в файле roadtoll.propertis

    /** Логгер сервиса GPS */
    private static final Logger log = LoggerFactory.getLogger(GpsService.class);

    /* Предыдущая точка */
    PointDTO previousPoint = new PointDTO();

    /* Список координат, полученных из kml - файла */
    private List<Coordinate> coordinates;

    /* Очередь для помещения точек с координатами, скоростью и азимутом сервисом  GPS
    * и для чтения сервисом хранения */
    protected static LinkedBlockingDeque<PointDTO> gpsQueue = new LinkedBlockingDeque<>(100);


    /** Инициализация сервиса:
     * получаение списка координат из файла и
     */
    @PostConstruct
    private void init(){
        // получаем список координат
        coordinates = getCoordinates();
    }

    /** формирование точки и помещение её в очередь сервиса GPS*/
    @Scheduled(cron = "${gpsSchedule}") //Шедулер сервиса GPS
    void put() {
        PointDTO point = new PointDTO(); // новая точка
        point.setTime(System.currentTimeMillis()); // текущее время
        point.setAutoId(autoId); // Номер авто
        if (coordinates.iterator().hasNext()){ // получаем координаты
            Coordinate coordinate =  coordinates.iterator().next();
            point.setLat(coordinate.getLatitude()); // широта
            point.setLon(coordinate.getLongitude()); // долгота

            /** Вычисляем азимут */
            point.setBearing(PointCalculate.getBearing(previousPoint, point));
            /** Вычисляем скорость */
            point.setSpeed(PointCalculate.getSpeed(previousPoint, point));
            try {
                gpsQueue.put(point); // помещаем точку в очередь сервиса GPS
                log.info("GpsService generate point: " + point.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // удаляем текущую точку из списка
            coordinates.remove(coordinate);

            /** Предыдущая точка становится текущей - для последующих вычислений
             *  азимута и скорости следующей точки
             */
            previousPoint = point;
        }
    }

    /** Читаем список координат из kml - файла */
    private List<Coordinate> getCoordinates() {

        File file = new File(kmlFileName); // файл
        List <Coordinate> coordinates; // список координат
        final Kml kml = Kml.unmarshal(file); // начинаем разбор файла

        // Объект Folder может содержать список объектов Feature
        Folder folder =  (Folder) kml.getFeature();
        List <Feature> features = folder.getFeature();
        Placemark placemark = new Placemark();

        // Просматриваем все объекты Feature
        for(Feature feature : features){
            placemark = (Placemark) feature; // Приводим их к типу Placemark
            // Получаем LineString
            LineString lineString = (LineString) placemark.getGeometry();
            // Получаем из LineString список координат
            coordinates = lineString.getCoordinates();
            if(!coordinates.isEmpty()){ // если список не пуст
                return  coordinates; // возвращаем список координат
            }
        }
        return null; // возвращаем пустой список
    }
}
