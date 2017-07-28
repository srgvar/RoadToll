package jdev.dto;

/**
 * Created by srgva on 17.07.2017.
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PointDTO {
        /** Текущие координаты:        */
        private double lat; // широта
        private double lon; // долгота
        private double bearing; // азимут
        private double speed; //мгновенная скорость

        private String autoId; // регистрационный номер автомобиля
        private long time; // текущее время


    public PointDTO(){} // пустой конструктор
    /** getters и setters для полей lat, lon, autoId, time */
    public double getLat() { return lat;}

    public void setLat(double lat) { this.lat = lat; }


    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(String autoId) {
        this.autoId = autoId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /** Преобразование в строку JSON используя ObjectMapper */
    public String toJson()  {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /** Представление объекта в виде строки */
    @Override
    public String toString() {
             return "PointDTO{" +
                    "lat=" + lat +
                    ", lon=" + lon +
                    ", bearing=" + bearing +
                    ", speed=" + speed +
                    ", autoId='" + autoId + '\'' +
                    ", time=" + time + '}';
        }

    /** Преобразование JSON-строки в объект PointDTO.
     * Используем ObjectMapper из библиотеки com.fasterxml... */
    public PointDTO fromJson(String content)  {
              ObjectMapper mapper = new ObjectMapper();
                 try {
                   return mapper.readValue(
                               content, PointDTO.class);
                 } catch (IOException e) {
                     e.printStackTrace();
                   }
               return null;
    }

    }


