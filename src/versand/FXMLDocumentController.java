package versand;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import versand.core.*;

import javax.swing.*;

public class FXMLDocumentController implements Initializable {

    public ToggleGroup type, insurance;

    public TextField id;
    public DatePicker placedDate;

    public TextField senderName, senderSurname;
    public TextField senderStreet, senderHouseNumber, senderPostcode, senderLocation;

    public TextArea description;

    public CheckBox deliveryExpress;
    public RadioButton deliveryLetter, deliveryParcel, deliveryPackage;
    public DatePicker wishDeliveryDate;
    public CheckBox alternativeDestinationCheckbox;
    public TextArea alternativeDestination;

    public TextField receiverName, receiverSurname;
    public TextField receiverStreet, receiverHouseNumber, receiverPostcode, receiverLocation;

    public CheckBox insuranceCheckbox;
    public RadioButton insuranceUntil100;
    public RadioButton insuranceUntil500;
    public RadioButton insuranceAbove500;
    public TextField insuranceAmount;

    public Slider salesPercent;
    public Button calculatePrice;
    public Label price;

    private final HashMap<Toggle, DeliveryType> deliveryTypes = new HashMap<>();
    private final HashMap<Toggle, InsuranceType> insuranceTypes = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        deliveryTypes.put(deliveryLetter, DeliveryType.LETTER);
        deliveryTypes.put(deliveryParcel, DeliveryType.PARCEL);
        deliveryTypes.put(deliveryPackage, DeliveryType.PACKAGE);

        insuranceTypes.put(insuranceUntil100, InsuranceType.UNTIL_100);
        insuranceTypes.put(insuranceUntil500, InsuranceType.UNTIL_500);
        insuranceTypes.put(insuranceAbove500, InsuranceType.ABOVE_500);
    }

    @FXML
    private void createShippingObject(ActionEvent event) {
        try {
            Address senderAddress = new Address(senderStreet, senderHouseNumber, senderPostcode, senderLocation);
            ShippingPerson sender = new ShippingPerson(senderName, senderSurname, senderAddress);

            Address receiverAddress = new Address(receiverStreet, receiverHouseNumber, receiverPostcode, receiverLocation);
            ShippingPerson receiver = new ShippingPerson(receiverName, receiverSurname, receiverAddress);

            Delivery delivery = new Delivery(deliveryExpress, deliveryTypes.get(type.getSelectedToggle()), wishDeliveryDate, alternativeDestination);
            Insurance insurance = new Insurance(insuranceCheckbox, insuranceTypes.get(this.insurance.getSelectedToggle()));

            ShippingObject object = new ShippingObject(id, placedDate, sender, receiver, description, delivery, insurance);

            // FIXME
            System.out.println(object.toJson());

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
}
