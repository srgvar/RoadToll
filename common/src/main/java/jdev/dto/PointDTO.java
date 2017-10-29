package jdev.dto;

/*
  Created by srgva on 17.07.2017.
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "points")
public class PointDTO {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id")
    private Long id;

    @JsonProperty()
    @Column(name = "lat")
    private Double lat; // широта

    @JsonProperty()
    @Column(name = "lon")
    private Double lon; // долгота

    @JsonProperty()
    @Column(name = "bearing")
    private Double bearing; // азимут

    @JsonProperty()
    @Column(name = "speed")
    private Double speed; //мгновенная скорость

    @JsonProperty()
    @Column(name = "autoid")
    private String autoId; // регистрационный номер автомобиля

    @JsonProperty()
    @Column(name = "timestamp")
    private Long timeStamp; // текущее время

    public PointDTO() {

    } // пустой конструктор


    public PointDTO(String json){
        new PointDTO();
        PointDTO pointDTO = new PointDTO();
        pointDTO = PointDTO.fromJson(json);
        this.lat = pointDTO.getLat();
        this.lon = pointDTO.getLon();
        this.bearing = pointDTO.getBearing();
        this.speed = pointDTO.getSpeed();
        this.autoId = pointDTO.getAutoId();
        this.timeStamp = pointDTO.getTimeStamp();
    }

    /**
     * getters и setters для полей lat, lon, autoId, timeStamp
     *
     */
    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public Double getLat() {
        return lat;
    }

    public void setLat(@JsonProperty("lat") Double lat) {
        this.lat = lat;
    }


    public Double getLon() {
        return lon;
    }

    public void setLon(@JsonProperty("lon") Double lon) {
        this.lon = lon;
    }

    public String getAutoId() {
        return autoId;
    }

    public void setAutoId(@JsonProperty("autoId") String autoId) {
        this.autoId = autoId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(@JsonProperty("timeStamp") Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Double getBearing() {
        return bearing;
    }

    public void setBearing(@JsonProperty("bearing") Double bearing) {
        this.bearing = bearing;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(@JsonProperty("speed") Double speed) {
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
                "lat = " + lat +
                ", lon = " + lon +
                ", bearing = " + bearing +
                ", speed = " + speed +
                ", autoId = '" + autoId + '\'' +
                ", timeStamp = " + timeStamp + '}';
    }
    /**
     * Преобразование JSON-строки в объект PointDTO.
     * используем ObjectMapper из библиотеки com.fasterxml...

     */


    public static PointDTO fromJson(String content) {
        ObjectMapper mapper = new ObjectMapper();
        //PointDTO pointDTO = new PointDTO();
        try {
            PointDTO pointDTO = new PointDTO();
            pointDTO = mapper.readValue(content, PointDTO.class);
            System.out.println("fromJson ->" + pointDTO);
            return pointDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean equals(PointDTO pointDTO){
    // Транспортное средство может находиться в определенное время
    // только в одном месте, вне зависимости от скорости
    // и начального азимута (при современном уровне технического прогресса:)

       return ((this.autoId.equals(pointDTO.getAutoId())) &&
                ( this.timeStamp == pointDTO.getTimeStamp()));
    }

}
