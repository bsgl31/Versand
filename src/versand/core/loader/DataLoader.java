package versand.core.loader;

import versand.core.ShippingObject;

import java.io.File;
import java.util.HashMap;

public interface DataLoader {

    HashMap<String, ShippingObject> loadObjects(String fileName);

    void saveObjects(HashMap<String, ShippingObject> shippingObjects, String fileName);

}
