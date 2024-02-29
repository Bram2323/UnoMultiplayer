package uno.server;

import uno.common.cards.Card;

import java.util.Optional;

public class Game {


    public Game(Player[] players) {}


    public void playCard(Player player, Card card) {}

    public void drawCard(Player player) {}

    public Optional<Player> getWinner() { return Optional.empty(); }

}
