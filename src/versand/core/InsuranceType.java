package versand.core;

public enum InsuranceType {

    UNTIL_100("bis 100€"),
    UNTIL_500("bis 500€"),
    ABOVE_500("über 500€");


    private final String buttonText;

    InsuranceType(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getButtonText() {
        return buttonText;
    }

}
