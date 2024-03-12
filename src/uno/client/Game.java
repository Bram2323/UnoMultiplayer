package uno.client;

import uno.common.cards.Card;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Game {
    private final Player[] players;
    private LocalPlayer localPlayer;
    private final int[] playerOrder;
    private Card lastCard;

    private final Scanner in = new Scanner(System.in);

    public Game(Player[] players, int[] playerOrder) {
        this.players = players;
        this.playerOrder = playerOrder;
        for (Player p : players) {
            if (p instanceof LocalPlayer) {
                localPlayer = (LocalPlayer) p;
            }
        }
    }

    public void setLastCard(Card card) { //card must not be null...
        if (card == null) throw new NullPointerException("card can't be null!");
        lastCard = card;
    }

    public Optional<Card> playerTurn(Player player) {
        for (int i : playerOrder) {
            players[i].display();
        }
        String lastCardString = lastCard == null ? "" : lastCard.toString();
        System.out.println("\nLast card: " + lastCardString);

        System.out.println("\nYour cards:\n" + localPlayer.allCards());

        do {
            if (player.equals(localPlayer)) {
                if (askForAction("What do you want to do?", new String[]{"draw", "play card"}) == 1) {
                    return Optional.empty();
                } else {
                    Card chosenCard = askForCardToPlay();
                    if (canPlayCard(chosenCard)) {
                        return Optional.of(chosenCard);
                    } else {
                        System.out.println("This card can't be played!");
                    }
                }
            } else {
                return null;
            }
        } while (true);
    }

    private boolean canPlayCard(Card playedCard) {

        if (playedCard.getCardType().equals(lastCard.getCardType())
                || playedCard.getCardColor().equals(lastCard.getCardColor())) {
            return true;
        } else {
            return false;
        }

    }

    private Card askForCardToPlay() {
        System.out.println("Which card do you want to play?");
        int choice = askFromMenu(localPlayer.allCards().split("\n"));
        return localPlayer.getHand().get(choice - 1);
    }

    private int askForAction(String question, String[] options) {
        System.out.println(question);
        return askFromMenu(options);
    }

    private int askFromMenu(String[] options) {
        for (int menuOption = 1; menuOption <= options.length; menuOption++) {
            System.out.println(menuOption + ". " + options[menuOption - 1]);
        }
        int choice;
        do {
            if (in.hasNextInt()) {
                choice = in.nextInt();
                in.nextLine();
                if (choice >= 1 && choice <= options.length) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between 1 and " + options.length);
                }
            } else {
                in.nextLine();
                System.out.println("Please enter a valid number");
            }
        } while (true);
    }

    public void playerWon(Player player) {

    }
}
