package fr.lernejo.prediction.controller;

import fr.lernejo.prediction.UnknownCountryException;
import fr.lernejo.prediction.service.PredictionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PredictionController {

    private PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @GetMapping("/temperature")
    public ResponseEntity<?> temperature(@RequestParam String country) {
        try {
            return ResponseEntity.ok(predictionService.getPredictionByCountry(country));
        } catch (UnknownCountryException unknownCountryException) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Unknown country");
        }
    }
}
