package versand.core;

import javafx.scene.control.TextField;

public class ShippingPerson implements CsvSerializable {

    private final String name;
    private final String surname;

    private final Address address;

    /** Erstellt eine neue {@link ShippingPerson}, die als Sender oder Empfänger eines {@link ShippingObject} verwendet werden kann.
     * @param name Vorname
     * @param surname Nachname
     * @param address Addresse
     */
    public ShippingPerson(String name, String surname, Address address) {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public ShippingPerson(TextField name, TextField surname, Address address) {
        this(name.getText(), surname.getText(), address);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toCsv(char splitChar) {
        return name + splitChar + surname + splitChar + address.toCsv(splitChar);
    }

    @Override
    public String toString() {
        return "ShippingPerson{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address=" + address +
                '}';
    }
}
