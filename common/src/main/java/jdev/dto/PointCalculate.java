package jdev.dto;

import static java.lang.Math.*;

/**
 *  Created by srgva on 21.07.2017.
 */
public class PointCalculate {
    private static final double RADIUS_EARTH = 6372795; // Радиус Земли :)

    /**
     * Возвращает растояние между точками, заданными
     * географическими координатами
     *
     * @param point1 -  начальная точка
     * @param point2 -  конечная точка
     * @return расстояние между точками в метрах
     */
 public static double getDistance(PointDTO point1, PointDTO point2){
     double lat1 = toRadians(point1.getLat());
     double lat2 = toRadians(point2.getLat());
     double lon1 = toRadians(point1.getLon());
     double lon2 = toRadians(point2.getLon());
     // расстояние между точками
     double distance =  RADIUS_EARTH * 2 *
             asin(
                  sqrt(
                       pow(sin((lat1 - lat2) / 2), 2) +
                       cos(lat1) * cos(lat2) *
                       pow(sin((lon1 - lon2) / 2),2)
                       )
                  );



     return distance;
 }

    /**
     * Возвращает азиум
     *
     * @param point1 -  начаьлная точка
     * @param point2 -  конечная точка
     * @return знаачение азимута в градусах
     */
/** Вычислеие  азимута */
 public static double getBearing(PointDTO point1, PointDTO point2){
     // Азимут
     double lat1 = toRadians(point1.getLat());
     double lat2 = toRadians(point2.getLat());
     double lon1 = toRadians(point1.getLon());
     double lon2 = toRadians(point2.getLon());
        double x = cos(lat1) * sin(lat2) -
                sin(lat1) * cos(lat2) * cos(lon2 - lon1);
        double y = sin(lon2 - lon1) * cos(lat2);
        double bearing = toDegrees(atan(-y / x));

          if (x < 0) bearing = bearing + 180;
            bearing = -toRadians(((bearing + 180) % 360) - 180);
            bearing = toDegrees(bearing - (2 * Math.PI * floor(bearing/(2 * Math.PI))));
          return bearing;

 }
    /**
     *  Вычисляем скорость - среднюю скорость
     *  прохождения предыдущего участка дороги будем
     *  считать мгновенной скоростью в текущей точке
     *  за неимением другой скорости, при условии
     *  посекундного получения информации для
     *  грузовика - вполне приемлемо :)
     **/
 public static double getSpeed(PointDTO point1, PointDTO point2){
     // Скорость
     return getDistance(point1, point2) / (point2.getTime()/1000 - point1.getTime()/1000);
 }

}
