package com.tracker.backend.data;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CovidDataRepository extends CrudRepository<CovidData, Long> {
    CovidData findByDateAndCountryCode(String date, String countryCode);

    @Modifying
    @Query(value = "insert into covid_data (date, country_code, confirmed, deaths, stringency_actual, stringency) " +
            "values(:date, :countryCode, :confirmed, :deaths, :stringencyActual, :stringency) " +
            "on duplicate key update " +
            "date=:date, " +
            "country_code=:countryCode, " +
            "confirmed=:confirmed, " +
            "deaths=:deaths," +
            "stringency_actual=:stringencyActual, " +
            "stringency=:stringency", nativeQuery = true)
    @Transactional
    void saveOrUpdate(@Param("date") String date, @Param("countryCode") String countryCode,
                      @Param("confirmed") int confirmed, @Param("deaths") int deaths,
                      @Param("stringencyActual") int stringencyActual, @Param("stringency") int stringency);

}
