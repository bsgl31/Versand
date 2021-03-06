package versand.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import versand.core.Address;
import versand.core.Delivery;
import versand.core.DeliveryType;
import versand.core.Insurance;
import versand.core.ShippingObject;
import versand.core.ShippingPerson;
import versand.core.gson.LocalDateSerializer;

import java.time.LocalDate;

public class Test {

    public static void main(String[] args) {

        ShippingObject object = new ShippingObject(
                "4234234",
                LocalDate.now(),
                new ShippingPerson("Hans", "Wuaschd", new Address("Straße", "5c", "94227", "Zwiesel")),
                new ShippingPerson("Herbert", "Hinterdupfing", new Address("Straße 2", "10a", "94227", "Zwiesel")),
                "Das ist eine Beschreibung",
                new Delivery(true, DeliveryType.PACKAGE, LocalDate.now(), "Hinterdupfing 30a"),
                new Insurance(true, null, 534));

        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).serializeNulls().create();

        ShippingObject.loadObjects();

        ShippingObject.printObjects();

        System.out.println(ShippingObject.get(LocalDate.now(), "1002"));

        ShippingObject.saveObjects();

        // ShippingObject.saveObjects("objects");

        // ShippingObject.clearObjects();

        // ShippingObject.loadObjects("objects");
        // ShippingObject.printObjects();
    }

}
