package com.tracker.backend.data;

import com.tracker.backend.data.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

@Service
public class DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    private RestTemplateBuilder builder = new RestTemplateBuilder();
    private RestTemplate restTemplate = builder.build();
    private static final String COVID_TRACKER_API = "https://covidtrackerapi.bsg.ox.ac.uk/api/v2/stringency/date-range/";
    @Autowired
    private CovidDataRepository repository;

    /**
     * Get covid data for the current day
     */
    public int updateData() {
        LocalDate to = LocalDate.now().minusDays(1);
        String url = COVID_TRACKER_API + to + "/" + to;
        StringencyData data = restTemplate.getForObject(url, StringencyData.class);
        int[] result = {0};
        if (data.getStatus() != null) {
            throw new DataException("Failed to load data: " + data.getMessage());
        } else {
            data.getDeathData().entrySet().forEach(entry -> {
                entry.getValue().entrySet().forEach(jsonEntry -> {
                    JsonCovidData json = jsonEntry.getValue();
                    if (repository.findByDateAndCountryCode(json.getDate().toString(), json.getCountryCode()) == null) {
                        CovidData covidData = new CovidData(json.getDate().toString(), json.getCountryCode(), (int) json.getConfirmed(), (int) json.getDeaths(), json.getStringency(), json.getStringencyActual());
                        save(covidData);
                        result[0]++;
                    }
                });

            });
            return result[0];
        }
    }

    public void save(CovidData data) {
        repository.saveOrUpdate(data.getDate(), data.getCountryCode(), data.getConfirmed(), data.getDeaths(), data.getStringencyActual(), data.getStringency());
    }

    @PostConstruct
    public void loadData() {
        LocalDate to = LocalDate.now().minusDays(2);
        LocalDate from = to.with(firstDayOfYear());
        logger.info("Import data from " + from + " to " + to);
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();
        StringencyData stringencyData = restTemplate.getForObject(COVID_TRACKER_API + from + "/" + to, StringencyData.class);
        logger.info("Total countries: " + stringencyData.getCountries().size());
        logger.info("Total death data: " + stringencyData.getDeathData().size());
        List<CovidData> batchData = new ArrayList<>();
        for (Map.Entry<String, Map<String, JsonCovidData>> d : stringencyData.getDeathData().entrySet()) {
            for (Map.Entry<String, JsonCovidData> dt : d.getValue().entrySet()) {
                JsonCovidData json = dt.getValue();
                CovidData covidData = new CovidData(json.getDate().toString(), json.getCountryCode(), (int)json.getConfirmed(), (int)json.getDeaths(), json.getStringency(), json.getStringencyActual());
                batchData.add(covidData);
            }
        }
        batchData.forEach(this::save);
    }
}
