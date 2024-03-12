package uno.common.cards;

public class Card {
    // Fields
    private final CardColor cardColor;
    private final CardType cardType;

    // Constructor
    public Card(CardColor cardColor, CardType cardType) {

        this.cardColor = cardColor;
        this.cardType = cardType;

    }

    // Getters and setters
    public CardColor getCardColor() {
        return cardColor;
    }

    public CardType getCardType() {
        return cardType;
    }

    @Override
    public String toString() {
        return cardColor + " " + cardType;
    }
}