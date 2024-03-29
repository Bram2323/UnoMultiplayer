package uno.server;

import uno.common.cards.Card;
import uno.common.cards.Deck;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class Game {
    private static final int QUANTITY_CARDS_AT_START = 7;

    private int currentPlayer;
    private final Player[] players;
    private Deck deck;


    public Game(Player[] players) {
        this.players = players;
        currentPlayer = new Random().nextInt(players.length);
        deck = new Deck();
        for(Player player: players){
            for(int i = 1; i <= QUANTITY_CARDS_AT_START; i++){
                player.addCard(deck.drawCard());
            }
        }
    }

    public void playCard(Player player, Card card) {
        if(deck.canPlayCard(card) && player.hasCard(card)){
            deck.playCard(card);
            player.removeCard(card);
            nextPlayer();
        }
    }

    public void drawCard(Player player) {
        player.addCard(deck.drawCard());
        nextPlayer();
    }

    private void nextPlayer(){
        currentPlayer++;
        if(currentPlayer >= players.length){
            currentPlayer = 0;
        }
    }

    public Optional<Player> getWinner() {
        return Arrays.stream(players).filter(player -> player.getCardsInHand().isEmpty()).findFirst();
    }

    public Card getTopCard(){
        return deck.getTopCard();
    }

    public Player getCurrentPlayer(){
        return players[currentPlayer];
    }
}