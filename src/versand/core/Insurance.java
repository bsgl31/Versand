package versand.core;

import javafx.scene.control.CheckBox;

public class Insurance {

    private final boolean selected;
    private final InsuranceType type;

    public Insurance(boolean selected, InsuranceType type) {
        this.selected = selected;
        this.type = type;
    }

    public Insurance(CheckBox selected, InsuranceType insuranceType) {
        this(selected.isSelected(), insuranceType);
    }

    public boolean isSelected() {
        return selected;
    }

    public InsuranceType getType() {
        return type;
    }

    public double getAmount() {
        return 0;
    }

    @Override
    public String toString() {
        return "Insurance{" +
                "selected=" + selected +
                ", type=" + type +
                '}';
    }
}
