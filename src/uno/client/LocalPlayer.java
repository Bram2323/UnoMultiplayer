package uno.client;

import uno.common.cards.Card;

import java.util.ArrayList;

public class LocalPlayer extends Player {

    public LocalPlayer(int id, String name) {
        super(id, name);
    }

    public void setCards(Card[] cards){
        // copy cards
    }

    public Card askForCard(){
        return null;
    }


    public void displayCards(){

    }


    @Override
    public void display() {

    }
}
