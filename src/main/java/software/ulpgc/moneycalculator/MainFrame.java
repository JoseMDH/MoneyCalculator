package software.ulpgc.moneycalculator;

import software.ulpgc.moneycalculator.control.Command;
import software.ulpgc.moneycalculator.model.Currency;
import software.ulpgc.moneycalculator.view.dialogs.CurrencyDialog;
import software.ulpgc.moneycalculator.view.dialogs.MoneyDialog;
import software.ulpgc.moneycalculator.view.dialogs.SwingCurrencyDialog;
import software.ulpgc.moneycalculator.view.dialogs.SwingMoneyDialog;
import software.ulpgc.moneycalculator.view.displays.MoneyDisplay;
import software.ulpgc.moneycalculator.view.displays.SwingMoneyDisplay;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    private final Map<String, Command> commands;
    private MoneyDisplay moneyDisplay;
    private MoneyDialog moneyDialog;
    private CurrencyDialog currencyDialog;
    private JLabel dateLabel;

    public MainFrame(Map<String, Command> commands, List<Currency> currencies,
                     LocalDate exchangeRateDate) {
        this.commands = commands;
        this.setTitle("Money Calculator");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.add(createMoneyDialog(currencies));
        mainPanel.add(createCurrencyDialog(currencies));
        mainPanel.add(createMoneyDisplay());
        this.add(mainPanel, BorderLayout.CENTER);

        dateLabel = new JLabel();
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateLabel.setBorder(BorderFactory.createEmptyBorder(
                5, 0, 5, 0));

        this.add(dateLabel, BorderLayout.SOUTH);
        this.add(toolbar(), BorderLayout.NORTH);

        updateDateLabel(exchangeRateDate);
    }

    public void updateDateLabel(LocalDate date) {
        dateLabel.setText("Última actualización: " + date);
    }

    private Component toolbar() {
        JButton button = new JButton("Calcular");
        button.addActionListener(e -> {
            try {
                commands.get("exchange money").execute();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(button);
        return panel;
    }

    private Component createMoneyDisplay() {
        SwingMoneyDisplay display = new SwingMoneyDisplay();
        this.moneyDisplay = display;
        return display;
    }

    private Component createCurrencyDialog(List<Currency> currencies) {
        SwingCurrencyDialog dialog = new SwingCurrencyDialog();
        this.currencyDialog = dialog;
        dialog.define(currencies);
        return dialog;
    }

    private Component createMoneyDialog(List<Currency> currencies) {
        SwingMoneyDialog dialog = new SwingMoneyDialog();
        this.moneyDialog = dialog;
        dialog.define(currencies);
        return dialog;
    }

    public MoneyDisplay getMoneyDisplay() {
        return moneyDisplay;
    }

    public CurrencyDialog getCurrencyDialog() {
        return currencyDialog;
    }

    public MoneyDialog getMoneyDialog() {
        return moneyDialog;
    }

    public void addCommand(String name, Command command) {
        commands.put(name, command);
    }
}