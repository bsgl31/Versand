package versand.core;

import javafx.scene.control.TextField;
import versand.core.loader.CsvSerializable;

public class Address implements CsvSerializable {

    private final String streetName;
    private final String houseNumber;

    private final String postcode;
    private final String location;

    /**
     * Erstellt eine neue {@link Address}, die bei einer {@link ShippingPerson} gebraucht wird.
     * @param streetName Straße
     * @param houseNumber Hausnummer
     * @param postcode PLZ
     * @param location Ort
     */
    public Address(String streetName, String houseNumber, String postcode, String location) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.location = location;
    }

    public Address(TextField streetName, TextField houseNumber, TextField postcode, TextField location) {
        this(streetName.getText(), houseNumber.getText(), postcode.getText(), location.getText());
    }

    public String getStreetName() {
        return streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toCsv(char splitChar) {
        return streetName + splitChar + houseNumber + splitChar + postcode + splitChar + location;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", postcode=" + postcode +
                ", location='" + location + '\'' +
                '}';
    }

}
