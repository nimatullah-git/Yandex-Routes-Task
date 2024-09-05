package com.example;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.List;

/**
 * @author nimatullah
 */
public class CsvReader {
    public static List<String[]> readCsv(String filePath) {
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            List<String[]> addresses = reader.readAll();
            reader.close();
            return addresses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
