package com.renegades.labs.jobfinder;

import com.renegades.labs.jobfinder.model.Model;
import com.renegades.labs.jobfinder.vo.Vacancy;

import java.util.List;

/**
 * Created by Виталик on 13.08.2016.
 */

public class Controller {
    private Model model;

    public Controller(Model model) {
        if (model == null)
            throw new IllegalArgumentException();
        this.model = model;
    }

    public List<Vacancy> findVacancies(String vacancyName, String cityName){
        return model.findVacancies(vacancyName, cityName);
    }
}
