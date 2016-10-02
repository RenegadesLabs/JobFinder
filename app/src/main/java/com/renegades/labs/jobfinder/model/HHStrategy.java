package com.renegades.labs.jobfinder.model;

import com.renegades.labs.jobfinder.vo.Vacancy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Виталик on 15.08.2016.
 */

public class HHStrategy implements Strategy {
    private static final String URL_FORMAT = "http://hh.ua/search/vacancy?text=%s+%s&page=%d";

    @Override
    public List<Vacancy> getVacancies(String vacancyName, String cityName) {
        List<Vacancy> vacancies = new ArrayList<>();

        try {
            int page = 0;

            while (true) {
                Document doc = getDocument(vacancyName, cityName,  page++);
                if (doc == null) break;

                Elements elements = doc.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy");
                if (elements.size() == 0) break;

                for (Element element : elements) {

                    Vacancy vacancy = new Vacancy();
                    vacancy.setUrl(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").attr("href"));
                    vacancy.setTitle(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-title").text());
                    vacancy.setCity(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-address").text());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                    vacancy.setSiteName(doc.title());
                    Element salaryElement = element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-compensation").first();
                    String salary = "";
                    if (salaryElement != null) {
                        salary = salaryElement.text();
                    }
                    vacancy.setSalary(salary);
                    vacancies.add(vacancy);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vacancies;
    }

    protected Document getDocument(String vacancyName, String cityName, int page) throws IOException {
        String url = String.format(Locale.ENGLISH, URL_FORMAT, vacancyName, cityName, page);
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36";
        return Jsoup.connect(url).userAgent(userAgent).referrer("http://google.com.ua").ignoreContentType(true).ignoreHttpErrors(true).get();
    }
}
