package versand.core;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import versand.core.loader.CsvSerializable;

public class Insurance implements CsvSerializable {

    private final boolean selected;
    private final InsuranceType type;
    private final double amount;

    /**
     * Erstellt eine neue {@link Insurance}
     * @param selected Versichert
     * @param type Versicherungs-Typ
     * @param amount Betrag, wird nur bei {@link InsuranceType#ABOVE_500} benötigt
     */
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

    @Override
    public String toCsv(char splitChar) {
        return Boolean.toString(selected) + splitChar + type.name() + splitChar + amount;
    }


    private static double parseDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

}
