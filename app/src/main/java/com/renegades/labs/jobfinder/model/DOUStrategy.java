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
 * Created by Виталик on 13.08.2016.
 */

public class DOUStrategy implements Strategy {
    private static final String URL_FORMAT = "https://jobs.dou.ua/vacancies/?search=%s+%s";

    @Override
    public List<Vacancy> getVacancies(String vacancyName, String cityName) {
        List<Vacancy> vacancies = new ArrayList<>();

        try {
            Document doc = getDocument(vacancyName, cityName);

            Elements elements = doc.select(".l-vacancy");

            for (Element element : elements) {
                Vacancy vacancy = new Vacancy();
                vacancy.setUrl(element.select("a[href]").first().attr("abs:href"));
                vacancy.setTitle(element.getElementsByClass("vt").text());

                String city = element.getElementsByClass("cities").text();
                if (city.length() > 2) {
                    city = city.substring(2);
                }
                vacancy.setCity(city);

                String companyName = element.getElementsByClass("company").text().replace("\u00a0", "");
                vacancy.setCompanyName(companyName);

                vacancy.setSiteName("https://jobs.dou.ua/");
                Element salaryElement = element.getElementsByClass("salary").first();
                String salary = "";
                if (salaryElement != null) {
                    salary = salaryElement.text();
                }
                vacancy.setSalary(salary);
                vacancies.add(vacancy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vacancies;
    }

    protected Document getDocument(String vacancyName, String cityName) throws IOException {
        String url = String.format(Locale.ENGLISH, URL_FORMAT, vacancyName, cityName);
        String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36";
        return Jsoup.connect(url).userAgent(userAgent).referrer("http://google.ru").get();
    }
}
