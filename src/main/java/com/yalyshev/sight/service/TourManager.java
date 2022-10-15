package com.yalyshev.sight.service;

import com.yalyshev.sight.entity.Sight;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TourManager {

    /**
     * Holds our cities
     */
    private static List<Sight> destinationCities = new ArrayList<Sight>();

    /**
     * Get a city
     */
    public static Sight getSight(int index) {
        return (Sight) destinationCities.get(index);
    }

    /**
     * Get the number of destination cities
     */
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

