package versand.core.loader;

import versand.core.Address;
import versand.core.Delivery;
import versand.core.DeliveryType;
import versand.core.Insurance;
import versand.core.InsuranceType;
import versand.core.ShippingObject;
import versand.core.ShippingPerson;
import versand.core.Utils;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CsvLoader implements DataLoader {

    private final char splitChar = ';';

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
                while(scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] split = line.split(Character.toString(splitChar));
                    String id = split[0];
                    LocalDate placedDate = Utils.localDateFromString(split[1]);
                    String senderName = split[2];
                    String senderSurname = split[3];
                    String senderStreetName = split[4];
                    String senderHouseNumber = split[5];
                    String senderPostcode = split[6];
                    String senderLocation = split[7];

                    String receiverName = split[8];
                    String receiverSurname = split[9];
                    String receiverStreetName = split[10];
                    String receiverHouseNumber = split[11];
                    String receiverPostcode = split[12];
                    String receiverLocation = split[13];

                    String description = split[14].replaceAll("<br>", "\n");

                    boolean express = Boolean.parseBoolean(split[15]);
                    DeliveryType deliveryType = DeliveryType.valueOf(split[16]);
                    LocalDate wishDeliveryDate = Utils.localDateFromString(split[17]);
                    String alternativeDestination = (split[18].equals("null") ? null : split[18].replaceAll("<br>", "\n"));

                    boolean hasInsurance = Boolean.parseBoolean(split[19]);
                    InsuranceType insuranceType = InsuranceType.valueOf(split[20]);
                    double insuranceAmount = Double.parseDouble(split[21]);

                    ShippingPerson sender = new ShippingPerson(senderName, senderSurname, new Address(senderStreetName, senderHouseNumber, senderPostcode, senderLocation));
                    ShippingPerson receiver = new ShippingPerson(receiverName, receiverSurname, new Address(receiverStreetName, receiverHouseNumber, receiverPostcode, receiverLocation));

                    Delivery delivery = new Delivery(express, deliveryType, wishDeliveryDate, alternativeDestination);
                    Insurance insurance = new Insurance(hasInsurance, insuranceType, insuranceAmount);

                    ShippingObject object = new ShippingObject(id, placedDate, sender, receiver, description, delivery, insurance);

                    String dateFormatted = FORMATTER.format(placedDate);
                    if(!map.containsKey(dateFormatted)) map.put(dateFormatted, new HashMap<>());
                    map.get(dateFormatted).put(id, object);
                }
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

            File[] files = folder.listFiles((dir, name) -> name.startsWith("versand-") && name.endsWith(".csv"));
            if (files != null && files.length != 0) {
                for(File f : files) {
                    f.delete();
                }
            }

            for(Map.Entry<String, HashMap<String, ShippingObject>> dateEntry : shippingObjects.entrySet()) {
                FileWriter writer = new FileWriter(new File(folder, "versand-" + dateEntry.getKey() + ".csv"));
                for(Map.Entry<String, ShippingObject> objectEntry : dateEntry.getValue().entrySet()) {
                    List<String> data = new ArrayList<>();
                    data.add(objectEntry.getKey());

                    ShippingObject o = objectEntry.getValue();
                    data.add(Utils.localDateToString(o.getPlaced()));
                    data.add(o.getSender().getName());
                    data.add(o.getSender().getSurname());
                    data.add(o.getSender().getAddress().toCsv(splitChar));

                    data.add(o.getReceiver().getName());
                    data.add(o.getReceiver().getSurname());
                    data.add(o.getReceiver().getAddress().toCsv(splitChar));

                    data.add(o.getDescription().replaceAll("\n", "<br>"));
                    data.add(o.getDelivery().toCsv(splitChar));
                    data.add(o.getInsurance().toCsv(splitChar));

                    writer.append(String.join(Character.toString(splitChar), data)).append("\n");
                }
                writer.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
