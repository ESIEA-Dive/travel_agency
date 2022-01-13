package fr.lernejo.travelsite.api;


import fr.lernejo.travelsite.model.Prediction;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface PredictionClient {

    @GET("/api/temperature")
    @Headers("Accept:application/json")
    Call<Prediction> getPrediction(@Query("country") String country);
}
