package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author nimatullah
 */
public class YandexApiRequest {
    private static final String API_KEY = "your_api_key"; // Не забудьте вставить ваш API-ключ

    public static String getRouteInfo(String from, String to, String mode) throws Exception {
        // Строим URL для запроса
        String url = String.format("https://api-maps.yandex.ru/services/route?from=%s&to=%s&mode=%s&apikey=%s",
                from, to, mode, API_KEY);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Выполняем запрос и возвращаем ответ
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
