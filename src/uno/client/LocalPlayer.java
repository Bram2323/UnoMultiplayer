package uno.client;

import uno.common.cards.Card;

import java.util.ArrayList;
import java.util.Arrays;

public class LocalPlayer extends Player {
    private ArrayList<Card> hand;

    public ArrayList<Card> getHand() {
        return hand;
    }

    public LocalPlayer(int id, String name) {
        super(id, name);
        hand = new ArrayList<>();
    }

    public void setCards(Card[] cards) {
        hand = new ArrayList<>(Arrays.stream(cards).toList());
    }

    public Card askForCard(Card card) {
        return hand.remove(hand.indexOf(card));
    }


    public String allCards() {
        StringBuilder handCards = new StringBuilder();
        int cardCounter = 1;
        for (Card card : hand) {
            handCards.append(cardCounter).append(" - ").append(card).append("\n");
            cardCounter++;
        }
        return handCards.toString();
    }


    @Override
    public void display() {
        System.out.println("You: " + hand.size() + " cards");
    }
}
