package software.ulpgc.moneycalculator.model.loaders;

import software.ulpgc.moneycalculator.model.Currency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FileCurrencyLoader implements CurrencyLoader {

    @Override
    public List<Currency> load(String filepath) throws IOException {
        List<Currency> currencies = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    Currency currency = parseCurrency(line);
                    currencies.add(currency);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                }
            }
        }
        return currencies;
    }

    private Currency parseCurrency(String line) {
        String[] columns = line.split("\t");
        if (columns.length != 4) {
            throw new IllegalArgumentException("Invalid line format");
        }

        String code = columns[0];
        String name = columns[1];
        String symbol = columns[2];
        LocalDate date = parseDate(columns[3]);

        return new Currency(code, name, symbol, date);
    }

    private LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            return LocalDate.now();
        }
    }
}
