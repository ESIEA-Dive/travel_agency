package fr.lernejo.prediction.service;

import fr.lernejo.prediction.TemperatureService;
import fr.lernejo.prediction.model.Prediction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionService {

    private final TemperatureService temperatureService;

    public PredictionService(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    public Prediction getPredictionByCountry(String country) {
        List<Prediction.Temperature> temperatures = new ArrayList<>();
        temperatures.add(new Prediction.Temperature(LocalDate.now().minusDays(1), temperatureService.getTemperature(country)));
        temperatures.add(new Prediction.Temperature(LocalDate.now().minusDays(2), temperatureService.getTemperature(country)));
        return new Prediction(country, temperatures);
    }

}
