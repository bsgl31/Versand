package versand.core.loader;

import versand.core.*;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.*;

public class CsvLoader implements DataLoader {

    private final char splitChar = ';';

    @Override
    public HashMap<String, ShippingObject> loadObjects(String fileName) {
        HashMap<String, ShippingObject> map = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File(fileName + ".csv"));
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split(";");
                String id = split[0];
                LocalDate placedDate = Utils.localDateFromString(split[1]);
                String senderName = split[2];
                String senderSurname = split[3];
                String senderStreetName = split[4];
                String senderHouseNumber = split[5];
                int senderPostcode = Integer.parseInt(split[6]);
                String senderLocation = split[7];

                String receiverName = split[8];
                String receiverSurname = split[9];
                String receiverStreetName = split[10];
                String receiverHouseNumber = split[11];
                int receiverPostcode = Integer.parseInt(split[12]);
                String receiverLocation = split[13];

                String description = split[14];

                boolean express = Boolean.parseBoolean(split[15]);
                DeliveryType deliveryType = DeliveryType.valueOf(split[16]);
                LocalDate wishDeliveryDate = Utils.localDateFromString(split[17]);
                String alternativeDestination = (split[18].equals("null") ? null : split[18]);

                boolean hasInsurance = Boolean.parseBoolean(split[19]);
                InsuranceType insuranceType = InsuranceType.valueOf(split[20]);
                double insuranceAmount = Double.parseDouble(split[21]);

                ShippingPerson sender = new ShippingPerson(senderName, senderSurname, new Address(senderStreetName, senderHouseNumber, senderPostcode, senderLocation));
                ShippingPerson receiver = new ShippingPerson(receiverName, receiverSurname, new Address(receiverStreetName, receiverHouseNumber, receiverPostcode, receiverLocation));

                Delivery delivery = new Delivery(express, deliveryType, wishDeliveryDate, alternativeDestination);
                Insurance insurance = new Insurance(hasInsurance, insuranceType, insuranceAmount);

                ShippingObject object = new ShippingObject(id, placedDate, sender, receiver, description, delivery, insurance);
                map.put(id, object);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    @Override
    public void saveObjects(HashMap<String, ShippingObject> shippingObjects, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName + ".csv");
            for(Map.Entry<String, ShippingObject> entry : shippingObjects.entrySet()) {
                List<String> data = new ArrayList<>();
                data.add(entry.getKey());

                ShippingObject o = entry.getValue();
                data.add(Utils.localDateToString(o.getPlaced()));
                data.add(o.getSender().getName());
                data.add(o.getSender().getSurname());
                data.add(o.getSender().getAddress().toCsv(splitChar));

                data.add(o.getReceiver().getName());
                data.add(o.getReceiver().getSurname());
                data.add(o.getReceiver().getAddress().toCsv(splitChar));

                data.add(o.getDescription());
                data.add(o.getDelivery().toCsv(splitChar));
                data.add(o.getInsurance().toCsv(splitChar));

                writer.append(String.join(";", data)).append("\n");
                /*
                42342345;2021-10-6;Hans;Wuaschd;Straße;5c;94227;Zwiesel;Herbert;Hinterdupfing;Straße 2;10a;94227;Zwiesel;Das ist eine Beschreibung;true;PACKAGE;2021-10-9;Hinterdupfing 30a;true;UNTIL_100;0.0
                {
                  "42342345": {
                    "id": "42342345",
                    "placed": "2021-10-6",
                    "sender": {
                      "name": "Hans",
                      "surname": "Wuaschd",
                      "address": {
                        "streetName": "Straße",
                        "houseNumber": "5c",
                        "postcode": 94227,
                        "location": "Zwiesel"
                      }
                    },
                    "receiver": {
                      "name": "Herbert",
                      "surname": "Hinterdupfing",
                      "address": {
                        "streetName": "Straße 2",
                        "houseNumber": "10a",
                        "postcode": 94227,
                        "location": "Zwiesel"
                      }
                    },
                    "description": "Das ist eine Beschreibung",
                    "delivery": {
                      "express": true,
                      "type": "PACKAGE",
                      "wishDeliveryDate": "2021-10-9",
                      "alternativeDestination": "Hinterdupfing 30a"
                    },
                    "insurance": {
                      "selected": true,
                      "type": "UNTIL_100",
                      "amount": 0.0
                    }
                  }
                }
                 */
            }
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
