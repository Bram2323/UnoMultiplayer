package uno.server;

import uno.common.cards.Card;

import java.util.ArrayList;

public class Player {

    private final int id;
    private final String name;

    public Player(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    private ArrayList<Card> cardsInHand = new ArrayList<>();

    public void addCard(Card card) {
        cardsInHand.add(card);
    }

    public void removeCard(Card card) {
        cardsInHand.remove(card);
    }

    public boolean hasCard(Card card) {
        return (cardsInHand.contains(card));
    }

    public ArrayList<Card> getCardsInHand() {
        return new ArrayList<>(cardsInHand);
    }
}
