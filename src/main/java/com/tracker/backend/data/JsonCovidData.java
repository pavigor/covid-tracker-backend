package com.tracker.backend.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class JsonCovidData {
    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("confirmed")
    private long confirmed;

    @JsonProperty("deaths")
    private long deaths;

    @JsonProperty("date_value")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonProperty("stringency_actual")
    private int stringencyActual;

    @JsonProperty("stringency")
    private int stringency;

    public String getCountryCode() {
        return countryCode;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public long getDeaths() {
        return deaths;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getStringencyActual() {
        return stringencyActual;
    }

    public int getStringency() {
        return stringency;
    }

    @Override
    public String toString() {
        return "DeathData{" +
                "countryCode='" + countryCode + '\'' +
                ", confirmed=" + confirmed +
                ", deaths=" + deaths +
                ", date=" + date +
                ", stringencyActual=" + stringencyActual +
                ", stringency=" + stringency +
                '}';
    }
}
