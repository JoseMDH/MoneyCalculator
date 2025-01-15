package software.ulpgc.moneycalculator.model.loaders;

import software.ulpgc.moneycalculator.model.Currency;

import java.io.IOException;
import java.util.List;

public interface CurrencyLoader {
    List<Currency> load(String fileName) throws IOException;
}
