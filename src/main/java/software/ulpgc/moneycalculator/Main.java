package software.ulpgc.moneycalculator;

import software.ulpgc.moneycalculator.control.Command;
import software.ulpgc.moneycalculator.control.ExchangeMoneyCommand;
import software.ulpgc.moneycalculator.model.Currency;
import software.ulpgc.moneycalculator.model.loaders.FileCurrencyLoader;
import software.ulpgc.moneycalculator.model.loaders.FileExchangeRateLoader;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String filePathRates = "D:\\Terminar\\money-calculator\\exchangeRates.tsv";
        String filePathCurrencies = "D:\\Terminar\\money-calculator\\currencies.tsv";
        Locale.setDefault(Locale.US);

        Main mainApp = new Main();
        mainApp.start(filePathCurrencies, filePathRates);
    }

    public void start(String filePathCurrencies, String filePathRates) {
        try {
            List<Currency> currencies = new FileCurrencyLoader().load(filePathCurrencies);
            FileExchangeRateLoader loader = new FileExchangeRateLoader();
            Map<String, Double> exchangeRates = loader.load(filePathRates);
            LocalDate exchangeRateDate = loader.getLastModifiedDate(filePathRates);

            Map<String, Command> commands = new HashMap<>();
            MainFrame mainFrame = new MainFrame(
                    commands,
                    currencies,
                    exchangeRateDate);

            Command command = new ExchangeMoneyCommand(
                    mainFrame.getMoneyDialog().define(currencies),
                    mainFrame.getCurrencyDialog().define(currencies),
                    exchangeRates,
                    mainFrame.getMoneyDisplay());
            mainFrame.addCommand("exchange money", command);
            mainFrame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al cargar los archivos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
