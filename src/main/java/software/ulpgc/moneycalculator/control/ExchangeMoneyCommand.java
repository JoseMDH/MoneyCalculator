package software.ulpgc.moneycalculator.control;

import software.ulpgc.moneycalculator.model.Currency;
import software.ulpgc.moneycalculator.model.Money;
import software.ulpgc.moneycalculator.view.dialogs.CurrencyDialog;
import software.ulpgc.moneycalculator.view.dialogs.MoneyDialog;
import software.ulpgc.moneycalculator.view.displays.MoneyDisplay;

import java.text.DecimalFormat;
import java.util.Map;

public class ExchangeMoneyCommand implements Command {
    private final MoneyDialog moneyDialog;
    private final CurrencyDialog currencyDialog;
    private final Map<String, Double> exchangeRates;
    private final MoneyDisplay moneyDisplay;

    public ExchangeMoneyCommand(MoneyDialog moneyDialog, CurrencyDialog currencyDialog,
                                Map<String, Double> exchangeRates, MoneyDisplay moneyDisplay) {
        this.moneyDialog = moneyDialog;
        this.currencyDialog = currencyDialog;
        this.exchangeRates = exchangeRates;
        this.moneyDisplay = moneyDisplay;
    }

    @Override
    public void execute() {
        Money money = moneyDialog.get();
        Currency currency = currencyDialog.get();

        double rate = getExchangeRate(money.currency(), currency);
        double resultAmount = getResultAmount(money, rate);
        Money result = new Money(resultAmount, currency);
        moneyDisplay.show(result);
    }

    private double getResultAmount(Money money, double rate) {
        double resultAmount = money.amount() * rate;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(resultAmount));
    }

    private double getExchangeRate(Currency from, Currency to) {

        if (from.equals(to)) {
            return 1.0;
        }
        String key = from.code() + "_" + to.code();
        Double rate = exchangeRates.get(key);

        if (rate == null) {
            String reverseKey = to.code() + "_" + from.code();
            rate = exchangeRates.get(reverseKey);
            if (rate != null) {
                return 1 / rate;
            } else {
                String keyUsd = "USD_" + from.code();
                rate = exchangeRates.get(keyUsd);
                if (rate != null) {
                    rate = 1 / rate;
                    String usdKey = "USD_" + to.code();
                    Double rateAux = exchangeRates.get(usdKey);
                    if (rateAux != null) {
                        return rateAux * rate;
                    }
                }
            }
        }

        if (rate == null) {
            throw new IllegalArgumentException(
                    "No se encontró una tasa de cambio para "
                            + key +
                            " ni para la inversa."
            );
        }
        return rate;
    }
}