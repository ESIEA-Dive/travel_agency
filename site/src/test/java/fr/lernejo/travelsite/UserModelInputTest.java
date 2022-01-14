package fr.lernejo.travelsite;

import fr.lernejo.travelsite.enumeration.Weather;
import fr.lernejo.travelsite.exception.InvalidEmailException;
import fr.lernejo.travelsite.exception.InvalidTemperatureException;
import fr.lernejo.travelsite.exception.RequiredInputException;
import fr.lernejo.travelsite.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
class UserModelInputTest {
    @Test
    public void invalid_email_exception() {
        Assertions.assertThatExceptionOfType(InvalidEmailException.class)
            .isThrownBy(() -> new User(".abc@pre.et", "abc", "pr",
                Weather.WARMER, 39D));

    }

    @Test
    public void username_required_input_exception() {
        Assertions.assertThatExceptionOfType(RequiredInputException.class)
            .isThrownBy(() -> new User(".abc@pre.et", null, "pr",
                Weather.WARMER, 39D));

    }

    @Test
    public void user_email_required_input_exception() {
        Assertions.assertThatExceptionOfType(RequiredInputException.class)
            .isThrownBy(() -> new User(null, "test", "pr",
                Weather.WARMER, 39D));

    }

    @Test
    public void user_weather_required_input_exception() {
        Assertions.assertThatExceptionOfType(RequiredInputException.class)
            .isThrownBy(() -> new User(".abc@pre.et", "test", "pr",
                null, 39D));

    }

    @Test
    public void user_country_required_input_exception() {
        Assertions.assertThatExceptionOfType(RequiredInputException.class)
            .isThrownBy(() -> new User(".abc@pre.et", "test", null,
                Weather.WARMER, 39D));
    }

    @Test
    public void user_minimum_temperature_distance_required_input_exception() {
        Assertions.assertThatExceptionOfType(RequiredInputException.class)
            .isThrownBy(() -> new User(".abc@pre.et", "test", "pr",
                Weather.WARMER, null));

    }

    @Test
    public void invalid_temperature_exception() {
        Assertions.assertThatExceptionOfType(InvalidTemperatureException.class)
            .isThrownBy(() -> new User("test@yopmail.com", "Test", "FR",
                Weather.WARMER, -5D));
        Assertions.assertThatExceptionOfType(InvalidTemperatureException.class)
            .isThrownBy(() -> new User("test@gmail.in", "Test", "FR",
                Weather.COLDER, 45d));
    }
}
