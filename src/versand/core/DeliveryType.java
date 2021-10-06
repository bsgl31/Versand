package versand.core;

public enum DeliveryType {

    LETTER("Brief", 0.60),
    PARCEL("Päckchen", 3.20),
    PACKAGE("Paket", 5.50);


    private final String buttonText;
    private final double price;

    DeliveryType(String buttonText, double price) {
        this.buttonText = buttonText;
        this.price = price;
    }

    public String getButtonText() {
        return buttonText;
    }

    public double getPrice() {
        return price;
    }

}
