package com.yalyshev.sight.service;

import com.yalyshev.sight.entity.Sight;
import com.yalyshev.sight.repo.SightRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AnnealingServiceImpl implements AnnealingService {

    private SightRepo sightRepo;

    public AnnealingServiceImpl(SightRepo sightRepo) {
        this.sightRepo = sightRepo;
    }

    public List<Integer> annealingRun(int dot1, int dot2) {
        //ToDo check dot1 and dot2

        TourManager.clear();
        TourManager.addSightAll(sightRepo.getAll());
        /** Установить начальную температуру */
        double temp = 10000;
        /** Скорость охлаждения */
        double coolingRate = 0.003;

        /** Инициализировать исходное решение */
        Tour currentSolution = new Tour();
        currentSolution.generateIndividual();
        currentSolution.setDotOneAndDotTwo(dot1, dot2);

        log.info("Initial solution distance: " + currentSolution.getDistance());
        /** Установить в качестве текущего наилучшего */
        Tour best = new Tour(currentSolution.getTour());


        /** Цикл до тех пор, пока система не "остынет" */
        while (temp > 1) {

            /** Создать новый тур по соседям */
            Tour newSolution = new Tour(currentSolution.getTour());
            int max = newSolution.tourSize() - 2;
            int min = 1;
            newSolution.setSight(0, currentSolution.getSight(0));
            newSolution.setSight(max, currentSolution.getSight(max));

            int tourPos1 = (int) ((Math.random() * (max + 1 - min) + min));
            int tourPos2 = (int) ((Math.random() * (max + 1 - min) + min));

            /** Получите города на выбранных позициях в туре */
            Sight citySwap1 = newSolution.getSight(tourPos1);
            Sight citySwap2 = newSolution.getSight(tourPos2);

            /** Поменяй их местами */
            newSolution.setSight(tourPos2, citySwap1);
            newSolution.setSight(tourPos1, citySwap2);

            /** Получите энергию решений */
            double currentEnergy = currentSolution.getDistance();
            double neighbourEnergy = newSolution.getDistance();

            /** Решите, должны ли мы принять соседа */
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                currentSolution = new Tour(newSolution.getTour());
            }

            /** Следите за найденным лучшим решением */
            if (currentSolution.getDistance() < best.getDistance()) {
                best = new Tour(currentSolution.getTour());
            }

            /** Охлаждение системы */
            temp *= 1 - coolingRate;
        }

        log.info("Final solution distance: " + best.getDistance());
        log.info("Tour: " + best);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < best.getTour().size(); i++) {
            result.add(best.getSight(i).getId());
        }
        return result;
    }

    /**
     * Если новое решение лучше, примим его
     * Если новое решение хуже, рассчитайте вероятность принятия
     */
    private double acceptanceProbability(double energy, double newEnergy, double temperature) {
        if (newEnergy < energy) {
            return 1.0;
        }
        return Math.exp((energy - newEnergy) / temperature);
    }
}
