package fr.lernejo.travelsite.model;


import fr.lernejo.travelsite.constant.TravelAgencyConstant;
import fr.lernejo.travelsite.enumeration.Weather;
import fr.lernejo.travelsite.exception.InvalidEmailException;
import fr.lernejo.travelsite.exception.InvalidTemperatureException;
import fr.lernejo.travelsite.exception.RequiredInputException;

import java.util.Objects;


public record User(String userEmail, String userName, String userCountry,
                   Weather weatherExpectation, Double minimumTemperatureDistance) {

    public User(String userEmail, String userName, String userCountry, Weather weatherExpectation, Double minimumTemperatureDistance) {

        validateInputs(userEmail, userName, userCountry, weatherExpectation, minimumTemperatureDistance);

        this.userEmail = userEmail;
        this.minimumTemperatureDistance = minimumTemperatureDistance;

        this.userName = userName;
        this.userCountry = userCountry;
        this.weatherExpectation = weatherExpectation;
    }



    private void validateInputs(String userEmail, String userName, String userCountry, Weather weatherExpectation, Double minimumTemperatureDistance) {
        if (Objects.isNull(userEmail) || userEmail.isEmpty()) {
            throw new RequiredInputException("userEmail is required");
        }
        if (Objects.isNull(userName) || userName.isEmpty()) {
            throw new RequiredInputException("userName is required");
        }
        if (Objects.isNull(userCountry) || userCountry.isEmpty()) {
            throw new RequiredInputException("userCountry is required");
        }
        if (Objects.isNull(weatherExpectation)) {
            throw new RequiredInputException("weatherExpectation is required");
        }
        if (Objects.isNull(minimumTemperatureDistance)) {
            throw new RequiredInputException("minimumTemperatureDistance is required");
        }
        if (!userEmail.matches(TravelAgencyConstant.EMAIL_REGEX)) {
            throw new InvalidEmailException("Invalid email address, please enter correct email address");
        }
        if (minimumTemperatureDistance <= 0 || minimumTemperatureDistance > 40) {
            throw new InvalidTemperatureException("Invalid temperature, please enter correct value");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userEmail.equals(user.userEmail) && userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, userName);
    }
}
