package com.tracker.backend.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(CovidDataId.class)
public class CovidData {
    //@GeneratedValue(strategy= GenerationType.IDENTITY)
    //private Long id;
    @Id
    private String date;
    @Id
    private String countryCode;
    private Integer confirmed;
    private Integer deaths;
    private Integer stringencyActual;
    private Integer stringency;


    public CovidData() {}

    public CovidData(String date, String countryCode, int confirmed, int deaths, int stringency, int stringencyActual) {
        this.date = date;
        this.countryCode = countryCode;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.stringency = stringency;
        this.stringencyActual = stringencyActual;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    //public void setId(Long id) {
       // this.id = id;
    //}

    public Integer getStringencyActual() {
        return stringencyActual;
    }

    public void setStringencyActual(Integer stringencyActual) {
        this.stringencyActual = stringencyActual;
    }

    public Integer getStringency() {
        return stringency;
    }

    public void setStringency(Integer stringency) {
        this.stringency = stringency;
    }
}
