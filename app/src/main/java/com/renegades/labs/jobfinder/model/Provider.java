package com.renegades.labs.jobfinder.model;

import com.renegades.labs.jobfinder.vo.Vacancy;

import java.util.List;

/**
 * Created by Виталик on 13.08.2016.
 */

public class Provider {
    private Strategy strategy;

    public Provider(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<Vacancy> getVacancies(String vacancyName, String cityName){
        return strategy.getVacancies(vacancyName, cityName);
    }
}
