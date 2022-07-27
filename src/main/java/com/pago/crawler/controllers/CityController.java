package com.pago.crawler.controllers;

import java.io.IOException;
import java.util.List;

import com.pago.crawler.models.City;
import com.pago.crawler.repositories.CityRepository;
import com.pago.crawler.repositories.CountryRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {

    private final CityRepository cityRepository;

    private final CountryRepository countryRepository;

    @PostMapping
    public void insertCity() throws IOException {
        String uri = "https://en.wikipedia.org/wiki/List_of_largest_cities";
        Document doc = Jsoup.connect(uri).get();
        Elements rows = doc.select("table.wikitable").get(0).select("tbody").select("td");
        for (int i = 0; i <= rows.size() - 1; i += 13) {
            City city = new City();

            city.setName(rows.get(i).text());

            city.setCountry(countryRepository.findByCountryName(rows.get(i + 1).text()));

            String populationString = rows.get(i + 4).text();
            if (!populationString.equals("—")) {
                city.setPopulation(Integer.parseInt(populationString.replaceAll(",", "")));
                city.setDensity(Double.parseDouble(rows.get(i + 6).text().replaceAll(",", "").split(" ")[0].split("\\[")[0]));
            } else {
                populationString = rows.get(i + 7).text();
                if (!populationString.equals("—")) {
                    city.setPopulation(Integer.parseInt(populationString.replaceAll(",", "")));
                    city.setDensity(Double.parseDouble(rows.get(i + 9).text().replaceAll(",", "").split(" ")[0].split("\\[")[0]));
                } else {
                    populationString = rows.get(i + 10).text();
                    if (!populationString.equals("—")) {
                        city.setPopulation(Integer.parseInt(populationString.replaceAll(",", "")));
                        city.setDensity(Double.parseDouble(rows.get(i + 12).text().replaceAll(",", "").split(" ")[0].split("\\[")[0]));
                    }
                }
            }

            cityRepository.save(city);
        }
    }

    @GetMapping
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

}
