package com.transportapi.services.impl.geocoding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GeocodingImpl {

    private static final String MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoibHVpc3J1YmVuIiwiYSI6ImNseGpvanVtZjFnOW4ybXB3amJudWtqNzEifQ.IBltRlzj_uMXezhW7kPxQg";
    private static final String GEOCODING_API_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/%s.json?access_token=%s";

    public double[] getCoordinates(String address) {
        RestTemplate restTemplate = new RestTemplate();
        if (address == null) {
            throw new IllegalArgumentException("La dirección proporcionada es nula");
        }
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String url = String.format(GEOCODING_API_URL, encodedAddress, MAPBOX_ACCESS_TOKEN);

        // Log para verificar la URL generada
        System.out.println("URL generada: " + url);

        try {
            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);

            JsonNode features = root.get("features");
            if (features != null && features.isArray() && features.size() > 0) {
                JsonNode location = features.get(0); // Obtenemos la primera ubicación encontrada
                JsonNode center = location.get("center");
                double lon = center.get(0).asDouble(); // Longitud está en la posición 0 del array "center"
                double lat = center.get(1).asDouble(); // Latitud está en la posición 1 del array "center"
                return new double[]{lat, lon};
            } else {
                throw new IllegalArgumentException("No se encontraron resultados para la dirección: " + address);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al obtener las coordenadas para la dirección: " + address, e);
        }
    }
    }
