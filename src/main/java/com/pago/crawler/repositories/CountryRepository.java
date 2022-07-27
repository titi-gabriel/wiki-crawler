package com.pago.crawler.repositories;

import com.pago.crawler.models.Country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country findByCountryName(String name);
}
