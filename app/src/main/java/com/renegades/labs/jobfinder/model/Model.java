package com.renegades.labs.jobfinder.model;

import com.renegades.labs.jobfinder.vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виталик on 13.08.2016.
 */

public class Model {
    private List<Provider> providers;

    public Model(List<Provider> providers) {
        if (providers == null || providers.size() == 0) {
            throw new IllegalArgumentException();
        }
        this.providers = providers;
    }

    public List<Vacancy> findVacancies(String vacancyName, String cityName){
        List<Vacancy> vacancies = new ArrayList<>();
        for (Provider provider :providers) {
            vacancies.addAll(provider.getVacancies(vacancyName, cityName));
        }
        return vacancies;

    }
}
