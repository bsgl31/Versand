package versand.core.loader;

import versand.core.ShippingObject;
import versand.core.Utils;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvLoader implements DataLoader {

    private final char splitChar = ';';

    @Override
    public HashMap<String, ShippingObject> loadObjects(String fileName) {

        return null;
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
                data.add(o.getSender().)
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
