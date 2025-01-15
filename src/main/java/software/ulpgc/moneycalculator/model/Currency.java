package software.ulpgc.moneycalculator.model;

import java.time.LocalDate;

public record Currency(String code, String name, String symbol, LocalDate date) {
    @Override
    public String toString() {
        return code + "-" + name;
    }
}