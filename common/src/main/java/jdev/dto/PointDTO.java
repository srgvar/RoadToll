package jdev.dto;

/*
  Created by srgva on 17.07.2017.
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PointDTO {
    /**
     * Текущие координаты:
     */

    @JsonProperty()
    private double lat; // широта
    @JsonProperty()
    private double lon; // долгота
    @JsonProperty()
    private double bearing; // азимут
    @JsonProperty()
    private double speed; //мгновенная скорость
    @JsonProperty()
    private String autoId; // регистрационный номер автомобиля
    @JsonProperty()
    private long time; // текущее время


    public PointDTO() {

    } // пустой конструктор

 /*   private PointDTO(@JsonProperty("lat") double lat, @JsonProperty("lon") double lon, @JsonProperty("bearing") double bearing, @JsonProperty("speed") double speed, @JsonProperty("autoId") String autoId, @JsonProperty("time") long time) {
        this.lat = lat;
        this.lon = lon;
        this.bearing = bearing;
        this.speed = speed;
        this.autoId = autoId;
        this.time = time;

    }*/
    public PointDTO(String content){
        //this();
        PointDTO p1;
        PointDTO p2 = new PointDTO();
        p1=p2.fromJson(content);
        this.lat = p1.lat;
        this.lon = p1.lon;
        this.bearing = p1.bearing;
        this.speed = p1.speed;
        this.autoId = p1.autoId;
        this.time = p1.time;
    }

    /**
     * getters и setters для полей lat, lon, autoId, time
     */
    public double getLat() {
        return lat;
    }

    public void setLat(@JsonProperty("lat") double lat) {
        this.lat = lat;
    }


    public double getLon() {
        return lon;
    }

    public void setLon(@JsonProperty("lon") double lon) {
        this.lon = lon;
    }

    String getAutoId() {
        return autoId;
    }

    public void setAutoId(@JsonProperty("autoId") String autoId) {
        this.autoId = autoId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(@JsonProperty("time") long time) {
        this.time = time;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(@JsonProperty("bearing") double bearing) {
        this.bearing = bearing;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(@JsonProperty("speed") double speed) {
        this.speed = speed;
    }

    /**
     * Преобразование в строку JSON используя ObjectMapper
     */
    public String toJson() {
        String string = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            string =  mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * Представление объекта в виде строки
     */
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

    /**
     * Преобразование JSON-строки в объект PointDTO.
     * Используем ObjectMapper из библиотеки com.fasterxml...
     */
    //@PostConstruct
    public PointDTO fromJson(String content) {
        ObjectMapper mapper = new ObjectMapper();
        //PointDTO point = new PointDTO();
        try {
            return mapper.readValue(content, PointDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
