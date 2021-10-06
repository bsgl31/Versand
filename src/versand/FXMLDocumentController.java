package versand;

import java.io.File;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.sun.deploy.uitoolkit.impl.fx.ui.FXMessageDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import versand.core.*;

import javax.swing.*;

public class FXMLDocumentController implements Initializable {

    public ToggleGroup deliveryType, insurance;

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

        placedDate.setValue(LocalDate.now());

        ShippingObject.loadObjects(new File("objects.json"));
    }



    @FXML
    private void loadShippingObject(ActionEvent event) {
        ShippingObject object = ShippingObject.get(id.getText());
        if(object == null) {
            Utils.message("Kein Objekt mit dieser ID");
            return;
        }

        Insurance insurance = object.getInsurance();
        insuranceCheckbox.setSelected(insurance.isSelected());
        for(Toggle toggle : this.insurance.getToggles()) {
            toggle.setSelected(insuranceTypes.get(toggle) == insurance.getType());
            ((RadioButton) toggle).setDisable(!insurance.isSelected());
        }
        insuranceAmount.setDisable(insurance.getType() != InsuranceType.ABOVE_500);
        insuranceAmount.setText((insurance.getAmount() == 0 ? "" : insurance.getAmount()) + "");

        Delivery delivery = object.getDelivery();
        deliveryExpress.setSelected(delivery.isExpress());
        for(Toggle toggle : this.deliveryType.getToggles()) {
            toggle.setSelected(deliveryTypes.get(toggle) == delivery.getType());
        }
        wishDeliveryDate.setValue(delivery.getWishDeliveryDate());
        alternativeDestination.setText(delivery.getAlternativeDestination());
        alternativeDestinationCheckbox.setSelected(delivery.getAlternativeDestination() != null && !delivery.getAlternativeDestination().trim().equals(""));
        alternativeDestination.setDisable(!alternativeDestinationCheckbox.isSelected());

        ShippingPerson receiver = object.getReceiver();
        receiverName.setText(receiver.getName());
        receiverSurname.setText(receiver.getSurname());
        Address receiverAddress = receiver.getAddress();
        receiverHouseNumber.setText(receiverAddress.getHouseNumber());
        receiverLocation.setText(receiverAddress.getLocation());
        receiverStreet.setText(receiverAddress.getStreetName());
        receiverPostcode.setText(receiverAddress.getPostcode() + "");

        ShippingPerson sender = object.getSender();
        senderName.setText(sender.getName());
        senderSurname.setText(sender.getSurname());
        Address senderAddress = sender.getAddress();
        senderHouseNumber.setText(senderAddress.getHouseNumber());
        senderLocation.setText(senderAddress.getLocation());
        senderStreet.setText(senderAddress.getStreetName());
        senderPostcode.setText(senderAddress.getPostcode() + "");

        placedDate.setValue(object.getPlaced());
        description.setText(object.getDescription());
    }

    private boolean isNotValid() {
        for (TextField s : new TextField[]{senderStreet, senderHouseNumber, senderPostcode, senderLocation, senderName, senderSurname,
                receiverStreet, receiverHouseNumber, receiverPostcode, receiverLocation, receiverName, receiverSurname, id}) {
            if (s.getText() == null || s.getText().trim().equals("")) {
                Utils.message("Ungültige/Unvollständige Eingabe");
                return true;
            }
        }

        if(placedDate.getValue().isBefore(LocalDate.now())) {
            Utils.message("\"Aufgegeben\" darf nicht in der Vergangenheit liegen");
            return true;
        }

        if(wishDeliveryDate.getValue() != null && wishDeliveryDate.getValue().getDayOfWeek() == DayOfWeek.SUNDAY) {
            Utils.message("\"Wunschzustellung\" darf nicht an einem Sonntag sein");
            return true;
        }
        return false;
    }

    private ShippingObject createShippingObject() {
        Address senderAddress = new Address(senderStreet, senderHouseNumber, senderPostcode, senderLocation);
        ShippingPerson sender = new ShippingPerson(senderName, senderSurname, senderAddress);

        Address receiverAddress = new Address(receiverStreet, receiverHouseNumber, receiverPostcode, receiverLocation);
        ShippingPerson receiver = new ShippingPerson(receiverName, receiverSurname, receiverAddress);

        Delivery delivery = new Delivery(deliveryExpress, deliveryTypes.get(deliveryType.getSelectedToggle()), wishDeliveryDate, alternativeDestination);
        Insurance insurance = new Insurance(insuranceCheckbox, insuranceTypes.get(this.insurance.getSelectedToggle()), insuranceAmount);

        return new ShippingObject(id, placedDate, sender, receiver, description, delivery, insurance);
    }

    @FXML
    private void createShippingObject(ActionEvent event) {
        if(isNotValid()) return;

        ShippingObject object = createShippingObject();
        if(object.isCached()) {
            new Thread(() -> {
                int choice = JOptionPane.showOptionDialog(null, "Ein Objekt mit der ID " + id.getText() + " existiert bereits.", "Meldung",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Überschreiben", "Abbrechen"}, "Überschreiben");
                if(choice == 0) {
                    object.cache();
                    Utils.message("Objekt " + id.getText() + " überschrieben");
                }
            }).start();
            return;
        }

        object.cache();
        Utils.message("Objekt " + id.getText() + " erstellt");
    }

    @FXML
    private void calculatePrice(ActionEvent event) {
        if(isNotValid()) return;

        ShippingObject object = createShippingObject();
        double price = object.getPrice() * ((100 - salesPercent.getValue()) / 100);

        this.price.setText("Preis: " + String.format("%,.2f", price));
    }

    @FXML
    private void toggleAlternativeDestination(ActionEvent event) {
        alternativeDestination.setDisable(!alternativeDestinationCheckbox.isSelected());
    }

    @FXML
    private void toggleDeliveryType(ActionEvent event) {
        if (deliveryType.getSelectedToggle() == deliveryLetter) {
            insuranceCheckbox.setSelected(false);
            insuranceCheckbox.setDisable(true);
            for (Toggle o : insurance.getToggles()) {
                ((RadioButton) o).setDisable(!insuranceCheckbox.isSelected());
            }
        } else {
            insuranceCheckbox.setDisable(false);
        }
    }

    @FXML
    private void toggleInsurance(ActionEvent event) {
        if (deliveryType.getSelectedToggle() == deliveryLetter) {
            insuranceCheckbox.setSelected(false);
        }
        for (Toggle o : insurance.getToggles()) {
            ((RadioButton) o).setDisable(!insuranceCheckbox.isSelected());
        }
    }

    @FXML
    private void toggleInsurance500(ActionEvent event) {
        insuranceAmount.setDisable(!insuranceAbove500.isSelected());
    }


}
