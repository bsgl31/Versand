package versand.core;

import javafx.scene.control.*;

import java.time.LocalDate;

public class Delivery implements CsvSerializable {

    private final boolean express;
    private final DeliveryType type;

    private final LocalDate wishDeliveryDate;

    private final String alternativeDestination;

    /**
     * Erstellt eine neue {@link Delivery}
     * @param express Express-Lieferung
     * @param type Lieferungs-Typ
     * @param wishDeliveryDate Wunsch-Lieferdatum
     * @param alternativeDestination Alternatives Ziel
     */
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
    public String toCsv(char splitChar) {
        return Boolean.toString(express) + splitChar + type.name() + splitChar + Utils.localDateToString(wishDeliveryDate) + splitChar + alternativeDestination;
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

    public static Delivery fromCsv(char splitChar, String string) {
        String[] s = string.split(splitChar + "");
        return new Delivery(Boolean.parseBoolean(s[0]), DeliveryType.valueOf(s[1]), Utils.localDateFromString(s[2]), s[3]);
    }
}
