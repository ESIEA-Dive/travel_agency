package fr.lernejo.travelsite.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.util.List;

public record Prediction(String country, List<Temperature> temperatures) {

    public record Temperature(@JsonDeserialize(using = LocalDateDeserializer.class)
                              @JsonSerialize(using = LocalDateSerializer.class) LocalDate date, double temperature) {
    }
}
