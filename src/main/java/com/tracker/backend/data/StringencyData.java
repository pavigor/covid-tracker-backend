package com.tracker.backend.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringencyData {
    @JsonProperty("countries")
    private List<String> countries;

    @JsonProperty("data")
    private Map<String, Map<String, JsonCovidData>> deathData = new HashMap<>();

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    public List<String> getCountries() {
        return countries;
    }

    public Map<String, Map<String, JsonCovidData>> getDeathData() {
        return deathData;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "StringencyData{" +
                "countries=" + countries.size() +
                ", deathData=" + deathData.size() +
                '}';
    }
}
