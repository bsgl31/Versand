package versand.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import versand.core.*;
import versand.core.gson.LocalDateSerializer;

import java.io.File;
import java.time.LocalDate;

public class Test {

    public static void main(String[] args) {

        ShippingObject object = new ShippingObject(
                "4234234",
                LocalDate.now(),
                new ShippingPerson("Hans", "Wuaschd", new Address("Straße", "5c", 94227, "Zwiesel")),
                new ShippingPerson("Herbert", "Hinterdupfing", new Address("Straße 2", "10a", 94227, "Zwiesel")),
                "Das ist eine Beschreibung",
                new Delivery(true, DeliveryType.PACKAGE, LocalDate.now(), "Hinterdupfing 30a"),
                new Insurance(true, null, 534));

        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).serializeNulls().create();

        ShippingObject.saveObjects(new File("objects.json"));

        ShippingObject.clear();

        ShippingObject.loadObjects(new File("objects.json"));
        ShippingObject.printObjects();
    }

}
