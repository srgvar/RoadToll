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
    private String kmlFileName; //имя файла с координатами - в файле application.properties

    @Value("${autoId}")
    private String autoId; // номер авто - в файле application.properties

    /** Логгер сервиса GPS */
    private static final Logger log = LoggerFactory.getLogger(GpsService.class);

    /* Предыдущая точка */
    private PointDTO previousPoint = new PointDTO();

    /* Список координат, полученных из kml - файла */
    private List<Coordinate> coordinates;

    /* Очередь для помещения точек с координатами, скоростью и азимутом сервисом  GPS
    * и для чтения сервисом хранения */
    static final LinkedBlockingDeque<PointDTO> gpsQueue = new LinkedBlockingDeque<>(100);

    public GpsService(){}
    /** Инициализация сервиса:
     * получаение списка координат из файла и
     */
    @PostConstruct
    private void init(){
        // получаем список координат
        coordinates = readCoordinates(kmlFileName);
    }

    /** формирование точки и помещение её в очередь сервиса GPS*/
    @Scheduled(cron = "${gpsSchedule}") //Шедулер сервиса GPS
    public void put() {
        PointDTO point = new PointDTO(); // новая точка
        point.setTime(System.currentTimeMillis()); // текущее время
        point.setAutoId(autoId); // Номер авто
        if (coordinates.iterator().hasNext()){ // получаем координаты
            Coordinate coordinate =  coordinates.iterator().next();
            point.setLat(coordinate.getLatitude()); // широта
            point.setLon(coordinate.getLongitude()); // долгота

            /* Вычисляем азимут */
            point.setBearing(PointCalculate.calculateBearing(previousPoint, point));
            /* Вычисляем скорость */
            point.setSpeed(PointCalculate.calculateSpeed(previousPoint, point));
            try {
                gpsQueue.put(point); // помещаем точку в очередь сервиса GPS
                log.info(" generate point: " + point.toString());
            } catch (InterruptedException e) {
                log.error(" exception: " + e.getMessage());
                e.printStackTrace();
            }
            // удаляем текущую точку из списка
            coordinates.remove(coordinate);

            /* Предыдущая точка становится текущей - для последующих вычислений
             *  азимута и скорости следующей точки
             */
            previousPoint = point;
        }
    }

    /** Читаем список координат из kml - файла */
    public List<Coordinate> readCoordinates(String kmlFileName) {
        File file = new File(new File("").getAbsolutePath()+kmlFileName); // файл

        List <Coordinate> coordinates; // список координат
        final Kml kml = Kml.unmarshal(file); // начинаем разбор файла

        // Объект Folder может содержать список объектов Feature
        Folder folder =  (Folder) kml.getFeature();
        List <Feature> features = folder.getFeature();
        Placemark placemark;

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

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> lCoordinates) {
        coordinates = lCoordinates;
    }

    public static LinkedBlockingDeque<PointDTO> getGpsQueue() {
        return gpsQueue;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String auto_Id) {
        autoId = auto_Id;
    }
}
