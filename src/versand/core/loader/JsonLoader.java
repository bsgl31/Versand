package versand.core.loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import versand.core.ShippingObject;
import versand.core.gson.LocalDateSerializer;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;

public class JsonLoader implements DataLoader {

    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .setPrettyPrinting()
            .create();

    @Override
    public HashMap<String, ShippingObject> loadObjects(String fileName) {
        HashMap<String, ShippingObject> shippingObjects = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File(fileName + ".json"));
            StringBuilder content = new StringBuilder();
            while(scanner.hasNextLine()) {
                content.append(scanner.nextLine());
            }
            Type type = new TypeToken<HashMap<String, ShippingObject>>(){}.getType();
            shippingObjects = gson.fromJson(content.toString(), type);
            scanner.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return shippingObjects;
    }

    @Override
    public void saveObjects(HashMap<String, ShippingObject> shippingObjects, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName + ".json");
            writer.append(gson.toJson(shippingObjects));
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
