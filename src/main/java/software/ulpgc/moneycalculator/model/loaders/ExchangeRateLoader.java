package software.ulpgc.moneycalculator.model.loaders;

import java.io.IOException;
import java.util.Map;

public interface ExchangeRateLoader {
    Map<String, Double> load(String filename) throws IOException;
}