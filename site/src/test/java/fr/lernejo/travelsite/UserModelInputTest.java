package fr.lernejo.travelsite;

import fr.lernejo.travelsite.enumeration.Weather;
import fr.lernejo.travelsite.exception.InvalidEmailException;
import fr.lernejo.travelsite.exception.InvalidTemperatureException;
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
    public void invalid_temperature_exception() {
        Assertions.assertThatExceptionOfType(InvalidTemperatureException.class)
            .isThrownBy(() -> new User("test@yopmail.com", "Test", "FR",
                Weather.WARMER, -5D));

        Assertions.assertThatExceptionOfType(InvalidTemperatureException.class)
            .isThrownBy(() -> new User("test@gmail.in", "Test", "FR",
                Weather.COLDER, 45d));
    }
}
