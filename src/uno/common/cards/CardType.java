package uno.common.cards;

public enum CardType {

    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9");

    final String cardTypeStringValue;

    CardType(String cardTypeStringValue) {
        this.cardTypeStringValue = cardTypeStringValue;
    }


}