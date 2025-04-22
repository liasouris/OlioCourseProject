package com.example.courseprojectolio;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;

public class DataRetriever {
    private static final String API_KEY = "1703bdfd503c84d8e12227cd540f8262";

    private static final String GEOCODING_URL =
            "https://api.openweathermap.org/geo/1.0/direct?q=%s,FI&limit=5&appid=%s";

    private static final String WEATHER_URL =
            "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Weather getWeatherData(String municipality) {
        try {
            String geoQuery = String.format(GEOCODING_URL,
                    urlEncode(municipality),
                    API_KEY);
            JsonNode geocodeArray = objectMapper.readTree(new URL(geoQuery));
            if (geocodeArray.isEmpty()) {
                throw new RuntimeException("No geocoding result for " + municipality);
            }
            JsonNode firstMatch = geocodeArray.get(0);
            double lat = firstMatch.get("lat").asDouble();
            double lon = firstMatch.get("lon").asDouble();

            String weatherQuery = String.format(WEATHER_URL, lat, lon, API_KEY);
            JsonNode weatherJson = objectMapper.readTree(new URL(weatherQuery));

            String name = weatherJson.get("name").asText();
            JsonNode w0 = weatherJson.get("weather").get(0);
            String main = w0.get("main").asText();
            String description = w0.get("description").asText();
            String icon = w0.get("icon").asText();
            String temperature = weatherJson.get("main").get("temp").asText();
            String windSpeed = weatherJson.get("wind").get("speed").asText();

            return new Weather(name, main, description, temperature, windSpeed, icon);

        } catch (IOException e) {
            throw new RuntimeException("Network error", e);
        }
    }

    private String urlEncode(String s) {
        return s.trim().replace(" ", "%20");
    }
}