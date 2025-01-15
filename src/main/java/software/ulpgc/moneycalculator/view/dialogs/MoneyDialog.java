package software.ulpgc.moneycalculator.view.dialogs;

import software.ulpgc.moneycalculator.model.Currency;
import software.ulpgc.moneycalculator.model.Money;

import java.util.List;

public interface MoneyDialog {
    MoneyDialog define(List<Currency> currencies);
    Money get();
}
