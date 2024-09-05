package com.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

/**
 * @author nimatullah
 */
public class Main {
    public static void main(String[] args) {
        String inputFilePath = "адреса.csv"; // Путь к исходному файлу
        String outputFilePath = "адреса_с_результатами.csv"; // Путь к файлу для записи

        // Читаем маршруты из CSV файла
        List<String[]> routes = CsvReader.readCsv(inputFilePath);

        for (String[] route : routes) {
            String from = route[0];
            String to = route[1];

            try {
                // Запрос на получение информации о маршруте на автомобиле
                String drivingResponse = YandexApiRequest.getRouteInfo(from, to, "driving");
                String drivingDistance = RouteDataParser.parseDistance(drivingResponse);
                int drivingTimeInMinutes = Integer.parseInt(RouteDataParser.parseTime(drivingResponse));
                String drivingTimeFormatted = convertMinutesToDdHhMm(drivingTimeInMinutes);

                // Запрос на общественный транспорт
                String transitResponse = YandexApiRequest.getRouteInfo(from, to, "transit");
                int transitTimeInMinutes = Integer.parseInt(RouteDataParser.parseTime(transitResponse));
                String transitTimeFormatted = transitTimeInMinutes + " – Прибытие " + getArrivalTime(transitResponse);

                // Запрос на пеший маршрут
                String walkingResponse = YandexApiRequest.getRouteInfo(from, to, "walking");
                String walkingDistance = RouteDataParser.parseDistance(walkingResponse);
                String walkingFormatted = calculateStepCount(walkingDistance);

                // Запись результатов в CSV
                String[] resultRecord = {from, to, drivingTimeFormatted + " – " + drivingDistance, transitTimeFormatted, walkingFormatted};
                CsvWriter.writeResult(outputFilePath, resultRecord);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String convertMinutesToDdHhMm(int totalMinutes) {
        int days = totalMinutes / (24 * 60);
        int hours = (totalMinutes % (24 * 60)) / 60;
        int minutes = totalMinutes % 60;
        return String.format("%02d.%02d.%02d", days, hours, minutes);
    }

    public static String getArrivalTime(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        String arrivalTime = jsonObject.getAsJsonObject("route").get("arrival_time").getAsString();
        return arrivalTime;
    }

    public static String calculateStepCount(String walkingDistance) {
        double distanceKm = Double.parseDouble(walkingDistance); // Преобразуем строку в число
        int stepCount = (int) (distanceKm * 1300); // Примерное количество шагов
        return walkingDistance + " – " + stepCount + " шагов";
    }
}
