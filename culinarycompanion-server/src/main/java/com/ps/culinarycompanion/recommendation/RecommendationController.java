package com.ps.culinarycompanion.recommendation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
public class RecommendationController {

    private final ObjectMapper objectMapper;

    public RecommendationController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieves recipe recommendations based on a list of ingredients.
     *
     * @param  ingredients  the list of ingredients to use for recommendations
     * @return             a ResponseEntity containing the recommendations in the response body
     */
    @PostMapping("/api/recipes/recommendations")
    public ResponseEntity<String> getRecipeRecommendations(@Valid @RequestBody List<String> ingredients) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            String url = "FLASK_API_URL";
            String data = objectMapper.writeValueAsString(ingredients);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return new ResponseEntity<>(response.body(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
