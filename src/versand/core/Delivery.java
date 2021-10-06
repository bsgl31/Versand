package versand.core;

import javafx.scene.control.*;

import java.time.LocalDate;

public class Delivery {

    private final boolean express;
    private final DeliveryType type;

    private final LocalDate wishDeliveryDate;

    private final String alternativeDestination;

    public Delivery(boolean express, DeliveryType type, LocalDate wishDeliveryDate, String alternativeDestination) {
        this.express = express;
        this.type = type;
        this.wishDeliveryDate = wishDeliveryDate;
        this.alternativeDestination = alternativeDestination;
    }

    public Delivery(CheckBox express, DeliveryType deliveryType, DatePicker wishDeliveryDate, TextArea alternativeDestination) {
        this(express.isSelected(), deliveryType, wishDeliveryDate.getValue(), alternativeDestination.getText());
    }

    public boolean isExpress() {
        return express;
    }

    public DeliveryType getType() {
        return type;
    }

    public LocalDate getWishDeliveryDate() {
        return wishDeliveryDate;
    }

    public String getAlternativeDestination() {
        return alternativeDestination;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "express=" + express +
                ", type=" + type +
                ", wishDeliveryDate=" + wishDeliveryDate +
                ", alternativeDestination='" + alternativeDestination + '\'' +
                '}';
    }
}
