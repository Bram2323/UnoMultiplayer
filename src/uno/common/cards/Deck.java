package uno.common.cards;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    // Fields
    private ArrayList<Card> unplayedCards = new ArrayList<>();
    private ArrayList<Card> playedCards = new ArrayList<>();

    private Card topCard;


    // Constructor
    public Deck () {

        for(CardColor colour : CardColor.values()) {
            for (CardType number : CardType.values()) {
                Card card = new Card(colour, number);
                unplayedCards.add(card);
            }
        }

        Collections.shuffle(unplayedCards);
        topCard = drawCard();

    }

    // Methods
    public Card drawCard() {

        Card cardDrawn = unplayedCards.getFirst();
        unplayedCards.removeFirst();

        if (unplayedCards.isEmpty()) {
            shuffleDeck();
        }

        return cardDrawn;

    }

    public void playCard(Card playedCard) {

        playedCards.add(topCard);
        setTopCard(playedCard);

    }

    public boolean canPlayCard(Card playedCard) {

        if (playedCard.getCardType().equals(topCard.getCardType())
                || playedCard.getCardColor().equals(topCard.getCardColor())) {
            return true;
        } else {
            return false;
        }

    }

    public void shuffleDeck() {

        Collections.shuffle(playedCards);
        unplayedCards = playedCards;
        playedCards.clear();

    }

    //Getters and setters
    public Card getTopCard() {
        return topCard;
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }

}