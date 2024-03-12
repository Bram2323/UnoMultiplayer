package uno.common.cards;

public enum CardColor {
    RED("Red"),
    GREEN("Green"),
    BLUE("Blue"),
    YELLOW("Yellow");

    private final String cardColorStringValue;

    CardColor(String cardColorsStringValue) {
        this.cardColorStringValue = cardColorsStringValue;
    }

    public String getString(){
        return cardColorStringValue;
    }

}
