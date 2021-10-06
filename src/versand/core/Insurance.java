package versand.core;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Insurance {

    private final boolean selected;
    private final InsuranceType type;
    private final double amount;

    public Insurance(boolean selected, InsuranceType type, double amount) {
        this.selected = selected;
        this.type = type;
        this.amount = amount;
    }

    public Insurance(CheckBox selected, InsuranceType insuranceType, TextField amount) {
        this(selected.isSelected(), insuranceType, parseDouble(amount.getText()));
    }

    public boolean isSelected() {
        return selected;
    }

    public InsuranceType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "selected=" + selected +
                ", type=" + type +
                '}';
    }


    private static double parseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

}
