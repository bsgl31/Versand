package versand.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import versand.core.gson.LocalDateSerializer;

import java.time.LocalDate;
import java.util.HashMap;

public class ShippingObject {

    private static final HashMap<String, ShippingObject> SHIPPING_OBJECTS = new HashMap<>();

    public static ShippingObject get(String id) {
        return SHIPPING_OBJECTS.get(id);
    }

    public static boolean exists(String id) {
        return SHIPPING_OBJECTS.containsKey(id);
    }

    public static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .setPrettyPrinting()
            .create();

    public static ShippingObject getFromJson(String json) {
        return GSON.fromJson(json, ShippingObject.class);
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
     * @throws IllegalArgumentException Falls bei der Erstellung ungültige Daten verwendet wurden.
     */
    public ShippingObject(String id, LocalDate placed, ShippingPerson sender, ShippingPerson receiver, String description, Delivery delivery, Insurance insurance) throws IllegalArgumentException {
        try {
            Long.parseUnsignedLong(id);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Ungültige ID");
        }
        this.id = id;
        if(placed.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("\"Aufgegeben\" darf nicht in der Vergangenheit liegen");
        }
        this.placed = placed;
        this.sender = sender;
        this.receiver = receiver;
        this.description = description;
        this.delivery = delivery;
        this.insurance = insurance;
        SHIPPING_OBJECTS.put(id, this);
    }

    public ShippingObject(TextField id, DatePicker datePicker, ShippingPerson sender, ShippingPerson receiver, TextArea description, Delivery delivery, Insurance insurance) {
        this(id.getText(), datePicker.getValue(), sender, receiver, description.getText(), delivery, insurance);
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
