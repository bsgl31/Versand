package versand.core.loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import versand.core.ShippingObject;
import versand.core.gson.LocalDateSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JsonLoader implements DataLoader {

    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .setPrettyPrinting()
            .create();

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

    public void saveObjects(HashMap<String, ShippingObject> shippingObjects, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName + ".json");
            writer.append(gson.toJson(shippingObjects));
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public HashMap<String, HashMap<String, ShippingObject>> loadObjects() {
        HashMap<String, HashMap<String, ShippingObject>> map = new HashMap<>();
        try {
            File folder = new File("data\\");
            if(!folder.exists()) {
                return map;
            }
            File[] files = folder.listFiles((dir, name) -> name.startsWith("versand-") && name.endsWith(".csv"));
            if(files == null || files.length == 0) {
                return map;
            }

            for(File file : files) {
                Scanner scanner = new Scanner(file);
                StringBuilder content = new StringBuilder();
                while(scanner.hasNextLine()) {
                    content.append(scanner.nextLine());
                }
                Type type = new TypeToken<HashMap<String, ShippingObject>>(){}.getType();
                HashMap<String, ShippingObject> objects = gson.fromJson(content.toString(), type);

                String dateFormatted = file.getName().substring(8, file.getName().length()-3);

                // FIXME check if working
                map.put(dateFormatted, objects);

                scanner.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public void saveObjects(HashMap<String, HashMap<String, ShippingObject>> shippingObjects) {
        try {
            File folder = new File("data\\");
            if(!folder.exists()) {
                if(!folder.mkdirs()) throw new RuntimeException("Cannot save objects: No permission");
            }

            File[] files = folder.listFiles((dir, name) -> name.startsWith("versand-") && name.endsWith(".json"));
            if (files != null && files.length != 0) {
                for(File f : files) {
                    f.delete();
                }
            }

            for(Map.Entry<String, HashMap<String, ShippingObject>> dateEntry : shippingObjects.entrySet()) {
                FileWriter writer = new FileWriter(new File(folder, "versand-" + dateEntry.getKey() + ".json"));
                writer.append(gson.toJson(dateEntry.getValue()));
                writer.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
