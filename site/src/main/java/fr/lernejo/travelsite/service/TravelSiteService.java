package fr.lernejo.travelsite.service;

import fr.lernejo.travelsite.enumeration.Weather;
import fr.lernejo.travelsite.exception.TravelNotFoundException;
import fr.lernejo.travelsite.exception.UserNotFoundException;
import fr.lernejo.travelsite.model.Travel;
import fr.lernejo.travelsite.model.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TravelSiteService {
    private final PredictionService predictionService;
    private final Set<User> users = new HashSet<>();

    public TravelSiteService(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    public void addUser(User user) {
        users.stream().filter(user1 -> user1.userName().equalsIgnoreCase(user.userName())).findFirst()
            .ifPresent(users::remove);

        users.add(user);


    }

    public List<Travel> findTravelsByUsername(String username) {
        return getUserTravels(users.stream().filter(user -> user.userName().equalsIgnoreCase(username)).findFirst().orElseThrow(() ->
            new UserNotFoundException("User not found")));
    }

    private List<Travel> getUserTravels(User user) {
        List<Travel> listOfTravels = predictionService.getListOfTravels();
        double countryAvgTemperature = userCountryTemp(user.userCountry(), listOfTravels);
        double finalTempToCalculate = countryAvgTemperature + user.minimumTemperatureDistance();
        return listOfTravels.stream().filter(travel ->
            isEligibleTravelForWeatherExpectation(user.weatherExpectation(), finalTempToCalculate, travel)).collect(Collectors.toList());

    }

    private boolean isEligibleTravelForWeatherExpectation(Weather weather, double finalTempToCalculate, Travel travel) {
        if (weather.equals(Weather.COLDER)) {
            return travel.temperature() < finalTempToCalculate;
        }
        if (weather.equals(Weather.WARMER)) {
            return travel.temperature() > finalTempToCalculate;
        }
        return false;

    }

    private double userCountryTemp(String country, List<Travel> travels) {
        return travels.stream().filter(travel -> travel.country().equalsIgnoreCase(country))
            .findFirst().orElseThrow(() -> new TravelNotFoundException("Travel not found")).temperature();

    }

}
