package com.yalyshev.sight.service;

import com.yalyshev.sight.entity.Sight;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class TourManager {

    // Holds our cities
    private static List<Sight> destinationCities = new ArrayList<Sight>();

    // Adds a destination city
 /*   //public static void addSight(Sight sight) {
        destinationCities.add(sight);
    }*/
    // Get a city
    public static Sight getSight(int index) {
        return (Sight) destinationCities.get(index);
    }

    // Get the number of destination cities
    public static int getNumberOfCities() {
        return destinationCities.size();
    }

    public static void addSightAll(List<Sight> list) {
        destinationCities.addAll(list);
    }

    public static void clear() {
        destinationCities = new ArrayList<Sight>();
    }
}

