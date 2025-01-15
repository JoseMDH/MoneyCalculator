package software.ulpgc.moneycalculator.model.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class FileExchangeRateLoader implements ExchangeRateLoader {

    @Override
    public Map<String, Double> load(String filename) throws IOException {
        Map<String, Double> exchangeRates = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;

                try {
                    parseLine(line, exchangeRates);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                }
            }
        }

        return exchangeRates;
    }

    private void parseLine(String line, Map<String, Double> exchangeRates) {
        String[] columns = line.split("\t");
        if (columns.length != 3) {
            throw new IllegalArgumentException("Invalid line format");
        }

        String fromCurrency = columns[0];
        String toCurrency = columns[1];
        double rate = Double.parseDouble(columns[2]);
        String key = fromCurrency + "_" + toCurrency;
        exchangeRates.put(key, rate);
    }

    public LocalDate getLastModifiedDate(String filename) throws IOException {
        return Files.getLastModifiedTime(Paths.get(filename))
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}