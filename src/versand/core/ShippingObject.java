package versand.core;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import versand.core.loader.CsvLoader;
import versand.core.loader.CsvSerializable;
import versand.core.loader.DataLoader;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ShippingObject implements CsvSerializable {

    private static HashMap<String, HashMap<String, ShippingObject>> SHIPPING_OBJECTS = new HashMap<>();
    private static final DataLoader DATA_LOADER = new CsvLoader();

    public static ShippingObject get(LocalDate date, String id) {
        HashMap<String, ShippingObject> objects = SHIPPING_OBJECTS.get(DataLoader.FORMATTER.format(date));
        if (objects == null) {
            return null;
        }
        return objects.get(id);
    }

    public static boolean exists(LocalDate date, String id) {
        if(!SHIPPING_OBJECTS.containsKey(DataLoader.FORMATTER.format(date))) return false;
        return SHIPPING_OBJECTS.get(DataLoader.FORMATTER.format(date)).containsKey(id);
    }

    public static boolean existsId(String id) {
        for(Map.Entry<String, HashMap<String, ShippingObject>> entry : SHIPPING_OBJECTS.entrySet()) {
            if(entry.getValue().containsKey(id)) {
                return true;
            }
        }
        return false;
    }

    public static void saveObjects() {
        DATA_LOADER.saveObjects(SHIPPING_OBJECTS);
    }

    public static void loadObjects() {
        SHIPPING_OBJECTS = DATA_LOADER.loadObjects();
    }

    public static void clearObjects() {
        SHIPPING_OBJECTS.clear();
    }

    public static void printObjects() {
        for (Map.Entry<String, HashMap<String, ShippingObject>> entry : SHIPPING_OBJECTS.entrySet()) {
            for (Map.Entry<String, ShippingObject> objectEntry : entry.getValue().entrySet()) {
                System.out.println(entry.getKey() + " -- " + objectEntry.getValue());
            }
        }
    }


    private final String id;
    private final LocalDate placed;

    private final ShippingPerson sender;
    private final ShippingPerson receiver;

    private final String description;

    private final Delivery delivery;
    private final Insurance insurance;

    /**
     * Erstellt ein neues {@link ShippingObject} und speichert es im Cache, falls die Erstellung erfolgreich war.<br>
     * Existiert bereits ein Versandobjekt mit der ID, wird es überschrieben.
     *
     * @param id          ID des Versandobjekts
     * @param placed      Das Datum, an dem es aufgegeben wurde
     * @param sender      Sender
     * @param receiver    Empfänger
     * @param description Beschreibung
     * @param delivery    Versand
     * @param insurance   Versicherung
     */
    public ShippingObject(String id, LocalDate placed, ShippingPerson sender, ShippingPerson receiver, String description, Delivery delivery, Insurance insurance) {
        this.id = id;
        this.placed = placed;
        this.sender = sender;
        this.receiver = receiver;
        this.description = description;
        this.delivery = delivery;
        this.insurance = insurance;
    }

    public ShippingObject(TextField id, DatePicker datePicker, ShippingPerson sender, ShippingPerson receiver, TextArea description, Delivery delivery, Insurance insurance) {
        this(id.getText(), datePicker.getValue(), sender, receiver, description.getText(), delivery, insurance);
    }

    public void cache() {
        String stringDate = DataLoader.FORMATTER.format(placed);
        for(Map.Entry<String, HashMap<String, ShippingObject>> entry : new HashSet<>(SHIPPING_OBJECTS.entrySet())) {
            entry.getValue().remove(id);
        }
        if (!SHIPPING_OBJECTS.containsKey(stringDate)) {
            SHIPPING_OBJECTS.put(stringDate, new HashMap<>());
        }
        SHIPPING_OBJECTS.get(stringDate).put(id, this);
    }

    public boolean isCached() {
        return existsId(id);
    }

    public double getPrice() {
        DeliveryType deliveryType = delivery.getType();
        double price = deliveryType.getPrice();
        if (delivery.isExpress()) {
            if (deliveryType == DeliveryType.LETTER) {
                price += 4;
            } else {
                price += 6;
            }
        }
        if (delivery.getWishDeliveryDate() != null) {
            price += 0.5;
        }
        if (deliveryType != DeliveryType.LETTER && insurance.isSelected()) {
            InsuranceType insuranceType = insurance.getType();
            if (insuranceType == InsuranceType.UNTIL_100) {
                price += 1.20;
            } else if (insuranceType == InsuranceType.UNTIL_500) {
                price += 2.00;
            } else {
                price += insurance.getAmount() * 0.05;
            }
        }
        return price;
    }

    public String getId() {
        return id;
    }

    public LocalDate getPlaced() {
        return placed;
    }

    public ShippingPerson getSender() {
        return sender;
    }

    public ShippingPerson getReceiver() {
        return receiver;
    }

    public String getDescription() {
        return description;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    @Override
    public String toCsv(char splitChar) {
        return id + splitChar + Utils.localDateToString(placed) + splitChar + sender.toCsv(splitChar) + splitChar + receiver.toCsv(splitChar) + splitChar + description;
    }

    @Override
    public String toString() {
        return "ShippingObject{" +
                "id=" + id +
                ", placed=" + placed +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", description='" + description + '\'' +
                ", delivery=" + delivery +
                ", insurance=" + insurance +
                '}';
    }
}
