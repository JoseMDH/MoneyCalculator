package software.ulpgc.moneycalculator.view.dialogs;

import software.ulpgc.moneycalculator.model.Currency;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingCurrencyDialog extends JPanel implements CurrencyDialog {

    private JComboBox<Currency> currencySelector;

    public SwingCurrencyDialog() {
        this.setLayout(new FlowLayout());
    }

    @Override
    public CurrencyDialog define(List<Currency> currencies) {
        if (currencies == null || currencies.isEmpty()) {
            throw new IllegalArgumentException(
                    "La lista de monedas no puede ser nula o vacía"
            );
        }
        this.removeAll();
        add(createCurrencySelector(currencies));
        this.revalidate();
        this.repaint();
        return this;
    }

    private Component createCurrencySelector(List<Currency> currencies) {
        JComboBox<Currency> selector = new JComboBox<>();
        for (Currency currency : currencies) {
            selector.addItem(currency);
        }
        this.currencySelector = selector;
        return selector;
    }

    @Override
    public Currency get() {
        return currencySelector.getItemAt(currencySelector.getSelectedIndex());
    }
}
