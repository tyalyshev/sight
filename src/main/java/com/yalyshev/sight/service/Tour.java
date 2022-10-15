package com.yalyshev.sight.service;

import com.yalyshev.sight.entity.Sight;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Component
public class Tour {
    /**
     * Проводит нашу экскурсию по городам
     */
    private ArrayList<Sight> tour = new ArrayList<Sight>();
    /**
     * Cache
     */
    private double distance = 0.0d;

    /**
     * Создаем пустой тур
     */
    public Tour() {
        for (int i = 0; i < TourManager.getNumberOfCities(); i++) {
            tour.add(null);
        }
    }

    /**
     * Создает тур из другого тура
     */
    @SuppressWarnings("unchecked")
    public Tour(ArrayList<Sight> tour) {
        this.tour = (ArrayList<Sight>) tour.clone();
    }

    /**
     * Возвращает информацию о туре
     */
    public ArrayList<Sight> getTour() {
        return tour;
    }

    /**
     * Creates a random individual
     */
    public void generateIndividual() {
        for (int cityIndex = 0; cityIndex < TourManager.getNumberOfCities(); cityIndex++) {
            setSight(cityIndex, TourManager.getSight(cityIndex));
        }
    }

    /**
     * Получает город из тура
     */
    public Sight getSight(int tourPosition) {
        return (Sight) tour.get(tourPosition);
    }

    /**
     * Устанавливает город в определенное положение в рамках тура
     * Если туры были изменены, нам нужно сбросить фитнес и расстояние
     */
    public void setSight(int tourPosition, Sight city) {
        tour.set(tourPosition, city);
        distance = 0;
    }

    /**
     * Получает общее расстояние тура
     * Пройдите по городам нашего тура
     * определяем расстояние.
     */
    public double getDistance() {
        if (Double.compare(distance, 0.0d) == 0) {
            double tourDistance = 0.0d;
            for (int cityIndex = 0; cityIndex < tourSize(); cityIndex++) {
                Sight fromSight = getSight(cityIndex);
                Sight destinationSight;
                if (cityIndex + 1 < tourSize()) {
                    destinationSight = getSight(cityIndex + 1);
                } else {
                    destinationSight = getSight(tourSize() - 1);
                }
                tourDistance += fromSight.distanceTo(destinationSight);
            }
            distance = tourDistance;
        }
        return distance;
    }

    /**
     * Устанавливаем выбранные города в начало и конец тура
     */
    public void setDotOneAndDotTwo(int dot1, int dot2) {
        Sight citySwap1 = this.getSight(dot1 - 1);
        Sight citySwap2 = this.getSight(dot2 - 1);
        Sight tempSight1 = this.getSight(0);
        Sight tempSight2 = this.getSight(tourSize() - 1);
        this.setSight(0, citySwap1);
        this.setSight(tourSize() - 1, citySwap2);
        this.setSight(dot1 - 1, tempSight1);
        this.setSight(dot2 - 1, tempSight2);
    }

    /**
     * Получение количество городов в туре
     */
    public int tourSize() {
        return tour.size();
    }

    @Override
    public String toString() {
        StringBuilder geneString = new StringBuilder();
        for (int i = 0; i < tourSize(); i++) {
            geneString.append(getSight(i).getId() + ";");
        }
        return geneString.toString();
    }
}
