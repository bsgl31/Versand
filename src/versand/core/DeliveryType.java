package versand.core;

public enum DeliveryType {

    LETTER("Brief"),
    PARCEL("Päckchen"),
    PACKAGE("Paket");


    private final String buttonText;

    DeliveryType(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getButtonText() {
        return buttonText;
    }

}
