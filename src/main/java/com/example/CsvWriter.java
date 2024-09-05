package com.example;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author nimatullah
 */
public class CsvWriter {
    public static void writeResult(String filePath, String[] record) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath, true));
            writer.writeNext(record);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
