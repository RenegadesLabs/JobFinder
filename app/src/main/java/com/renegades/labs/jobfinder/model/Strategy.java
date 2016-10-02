package com.renegades.labs.jobfinder.model;

import com.renegades.labs.jobfinder.vo.Vacancy;

import java.util.List;

/**
 * Created by Виталик on 13.08.2016.
 */

public interface Strategy {
    List<Vacancy> getVacancies(String vacancyName, String cityName);
}
