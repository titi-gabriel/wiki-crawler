package com.pago.crawler.controllers;

import java.io.IOException;
import java.util.List;

import com.pago.crawler.models.Country;
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
@RequestMapping("/countries")
public class CountryController {

    private final CountryRepository countryRepository;

    @PostMapping
    public void insertCountry() throws IOException {
        String uri = "https://en.wikipedia.org/wiki/List_of_countries_by_population_in_2010";
        Document doc = Jsoup.connect(uri).get();
        Elements rows = doc.select("table.wikitable").get(1).select("tbody").select("td");
        for (int i = 7; i <= rows.size(); i += 6) {
            Country country = new Country();
            country.setCountryName(rows.get(i).text());

            country.setPopulation(Integer.parseInt(rows.get(i + 1).text().replaceAll(",", "")));

            String areaString = rows.get(i + 3).text();

            if (!areaString.isEmpty()) {
                country.setArea(Double.parseDouble(areaString.replaceAll(",", "")));
            }
            countryRepository.save(country);
        }
    }

    @GetMapping
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
}
