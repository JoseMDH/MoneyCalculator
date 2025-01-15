package software.ulpgc.moneycalculator.view.displays;

import software.ulpgc.moneycalculator.model.Money;

import javax.swing.*;

public class SwingMoneyDisplay extends JLabel implements MoneyDisplay {

    public SwingMoneyDisplay() {
        this.setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public void show(Money money) {
        if (money == null) {
            throw new IllegalArgumentException("El objeto Money no puede ser nulo");
        }
        this.setText(money.toString());
        this.revalidate();
        this.repaint();
    }
}