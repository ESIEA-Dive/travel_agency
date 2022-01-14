package fr.lernejo.travelsite.service;

import fr.lernejo.travelsite.api.PredictionClient;
import fr.lernejo.travelsite.exception.UnableToConnectClientException;
import fr.lernejo.travelsite.exception.UnableToReadFileException;
import fr.lernejo.travelsite.model.Prediction;
import fr.lernejo.travelsite.model.Travel;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PredictionService {

    private final PredictionClient predictionClient;


    public PredictionService(PredictionClient predictionClient) {
        this.predictionClient = predictionClient;
    }

    public List<Travel> getListOfTravels() {
        String FILE_COUNTRIES = "countries.txt";
        List<Travel> travels = new ArrayList<>();
        String content = null;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(FILE_COUNTRIES);
        try {
            if (Objects.nonNull(inputStream)) {
                content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new UnableToReadFileException("Unable To Read File.");
        }
        if (Objects.nonNull(content)) {
            content.lines().forEach(country -> travels.add(createTravelByCountry(country)));
        }
        return travels;
    }


    private Travel createTravelByCountry(String country) {
        Prediction prediction = getPredictionByCountry(country);
        double averageTemperature = prediction.temperatures().stream().mapToDouble(Prediction.Temperature::temperature)
            .summaryStatistics().getAverage();

        return new Travel(country, averageTemperature);
    }

    private Prediction getPredictionByCountry(String country) {
        Call<Prediction> callPrediction = predictionClient.getPrediction(country);
        try {
            return callPrediction.execute().body();
        } catch (IOException e) {
            throw new UnableToConnectClientException("Unable to connect to prediction engine.");
        }
    }
}
