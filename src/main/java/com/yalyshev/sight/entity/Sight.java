package com.yalyshev.sight.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "sight")
public class Sight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "double precision")
    private double latitude;
    @Column(columnDefinition = "double precision")
    private double longitude;

    public Sight() {
    }

    /**
     * rad - Радиус Земли в метрах.
     * Находим длину малой дуги между точками на сфере.
     */
    public double distanceTo(Sight destinationSight) {

        int rad = 6372795;

        double lat1 = this.getLatitude() * Math.PI / 180.0;
        double lat2 = destinationSight.getLatitude() * Math.PI / 180.0;
        double long1 = this.getLongitude() * Math.PI / 180.0;
        double long2 = destinationSight.getLongitude() * Math.PI / 180.0;

        /** косинусы и синусы широт и разницы долгот */
        double cl1 = Math.cos(lat1);
        double cl2 = Math.cos(lat2);
        double sl1 = Math.sin(lat1);
        double sl2 = Math.sin(lat2);
        double cdelta = Math.cos(long2 - long1);
        double sdelta = Math.sin(long2 - long1);

        /** вычисления длины большого круга */
        double y = Math.sqrt(Math.pow(cl2 * sdelta, 2) + Math.pow(cl1 * sl2 - sl1 * cl2 * cdelta, 2));
        double x = sl1 * sl2 + cl1 * cl2 * cdelta;
        double ad = Math.atan2(y, x);

        return ad * rad;
    }
}
