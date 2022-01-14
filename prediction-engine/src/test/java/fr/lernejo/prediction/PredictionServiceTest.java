package fr.lernejo.prediction;

import fr.lernejo.prediction.model.Prediction;
import fr.lernejo.prediction.service.PredictionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class PredictionServiceTest {

    private final TemperatureService temperatureService = new TemperatureService();
    private final PredictionService service = new PredictionService(temperatureService);


    @ParameterizedTest
    @CsvSource({
        "france",
        "FRANCE",
        "France"
    })
    void getting_prediction_by_country(String country) {
        Prediction prediction = service.getPredictionByCountry(country);
        Prediction.Temperature temperature1 = prediction.temperatures().get(0);
        Prediction.Temperature temperature2 = prediction.temperatures().get(1);

        assertThat(temperature1.temperature()).isBetween(8D, 32D);
        assertThat(temperature2.temperature()).isBetween(8D, 32D);

        assertThat(temperature1.date()).isNotNull();
        assertThat(temperature2.date()).isNotNull();

    }

    @Test
    public void unknown_country_prediction_exception() {
        Assertions.assertThatExceptionOfType(UnknownCountryException.class)
            .isThrownBy(() -> service.getPredictionByCountry("Unknown"));
    }
}
