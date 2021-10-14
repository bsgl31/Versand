package versand.core.loader;

import versand.core.ShippingObject;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public interface DataLoader {

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    HashMap<String, HashMap<String, ShippingObject>> loadObjects();

    void saveObjects(HashMap<String, HashMap<String, ShippingObject>> shippingObjects);

}
