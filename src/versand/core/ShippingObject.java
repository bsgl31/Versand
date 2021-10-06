package versand.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import versand.core.gson.LocalDateSerializer;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShippingObject {

    private static HashMap<String, ShippingObject> SHIPPING_OBJECTS = new HashMap<>();

    public static ShippingObject get(String id) {
        return SHIPPING_OBJECTS.get(id);
    }

    public static boolean exists(String id) {
        return SHIPPING_OBJECTS.containsKey(id);
    }

    public static void clear() {
        SHIPPING_OBJECTS.clear();
    }

    public static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .setPrettyPrinting()
            .create();

    public static ShippingObject getFromJson(String json) {
        return GSON.fromJson(json, ShippingObject.class);
    }

    public static void printObjects() {
        for(Map.Entry<String, ShippingObject> entry : SHIPPING_OBJECTS.entrySet()) {
            System.out.println(entry.getKey() + " -- " + entry.getValue());
        }
    }

    public static void saveObjects(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.append(GSON.toJson(SHIPPING_OBJECTS));
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void loadObjects(File file) {
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder content = new StringBuilder();
            while(scanner.hasNextLine()) {
                content.append(scanner.nextLine());
            }
            Type type = new TypeToken<HashMap<String, ShippingObject>>(){}.getType();
            SHIPPING_OBJECTS = GSON.fromJson(content.toString(), type);
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
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
     * @param id ID des Versandobjekts
     * @param placed Das Datum, an dem es aufgegeben wurde
     * @param sender Sender
     * @param receiver Empfänger
     * @param description Beschreibung
     * @param delivery Versand
     * @param insurance Versicherung
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
        SHIPPING_OBJECTS.put(id, this);
    }

    public boolean isCached() {
        return SHIPPING_OBJECTS.containsKey(id);
    }

    public double getPrice() {
        DeliveryType deliveryType = delivery.getType();
        double price = deliveryType.getPrice();
        if(delivery.isExpress()) {
            if(deliveryType == DeliveryType.LETTER) {
                price += 4;
            } else {
                price += 6;
            }
        }
        if(delivery.getWishDeliveryDate() != null) {
            price += 0.5;
        }
        if(deliveryType != DeliveryType.LETTER) {
            InsuranceType insuranceType = insurance.getType();
            if(insuranceType == InsuranceType.UNTIL_100) {
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

    public String toJson() {
        return GSON.toJson(this);
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
