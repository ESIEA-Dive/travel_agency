package fr.lernejo.travelsite;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lernejo.travelsite.enumeration.Weather;
import fr.lernejo.travelsite.model.Prediction;
import fr.lernejo.travelsite.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(MockServerExtension.class)
@MockServerSettings(ports = {7080, 7081})
class TravelControllerTest {

    @Autowired
    MockMvc mvc;


    private void createMockServerClient() {
        List<Prediction.Temperature> temperatures = new LinkedList<>();
        temperatures.add(new Prediction.Temperature(LocalDate.now(), 10));
        temperatures.add(new Prediction.Temperature(LocalDate.now().minusDays(1), 30));
        new MockServerClient("localhost", 7080)
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/api/temperature"))
            .respond(
                response()
                    .withStatusCode(200)
                    .withHeaders(
                        new Header("Content-Type", "application/json"))
                    .withBody(getJsonString(new Prediction("France", temperatures)))
            );
    }

    private String getJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void post_inscription_ok(@Autowired MockMvc mockMvc) throws Exception {
        User user = new User("test@yopmail.com", "test", "France", Weather.WARMER, 20D);
        ObjectMapper mapper = new ObjectMapper();
        String requestJSON = mapper.writeValueAsString(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/inscription").contentType(MediaType.APPLICATION_JSON)
                .content(requestJSON))
            .andExpect(MockMvcResultMatchers.status().isOk());

    }


    @Test
    void post_inscription_with_prediction(@Autowired MockMvc mockMvc) throws Exception {
        createMockServerClient();
        User user = new User("test2@yopmail.com", "test2", "France", Weather.WARMER,
            20D);
        ObjectMapper mapper = new ObjectMapper();
        String requestJSON = mapper.writeValueAsString(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/inscription").contentType(MediaType.APPLICATION_JSON)
                .content(requestJSON))
            .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc
            .perform(MockMvcRequestBuilders.get("/api/travels?userName=test2"))
            .andExpect(MockMvcResultMatchers.status().isOk());

    }


}
