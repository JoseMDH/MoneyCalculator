package software.ulpgc.moneycalculator.view.dialogs;

import software.ulpgc.moneycalculator.model.Currency;
import software.ulpgc.moneycalculator.model.Money;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingMoneyDialog extends JPanel implements MoneyDialog {
    private JTextField amountField;
    private CurrencyDialog currencyDialog;

    public SwingMoneyDialog() {
        this.setLayout(new FlowLayout());
    }

    @Override
    public MoneyDialog define(List<Currency> currencies) {
        if (currencies == null || currencies.isEmpty()) {
            throw new IllegalArgumentException(
                    "La lista de monedas no puede ser nula o vacía"
            );
        }
        this.removeAll();
        add(createAmountField());
        add(createCurrencyDialog(currencies));
        this.revalidate();
        this.repaint();
        return this;
    }

    private Component createCurrencyDialog(List<Currency> currencies) {
        SwingCurrencyDialog dialog = new SwingCurrencyDialog();
        dialog.define(currencies);
        this.currencyDialog = dialog;
        return dialog;
    }

    private Component createAmountField() {
        JTextField textField = new JTextField();
        textField.setColumns(5);
        this.amountField = textField;
        return textField;
    }

    @Override
    public Money get() {
        return new Money(toLong(amountField.getText()), currencyDialog.get());
    }

    private long toLong(String text) {
        if (text == null || text.isEmpty()) {
            System.err.println("La entrada es nula o vacía.");
            JOptionPane.showMessageDialog(null,
                    "Por favor, introduce un valor numérico válido.",
                    "Entrada inválida", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir la entrada a número: " + text);
            JOptionPane.showMessageDialog(this,
                    "El valor introducido no es válido: " + text,
                    "Error de conversión", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
}
