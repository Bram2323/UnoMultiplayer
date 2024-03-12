package uno.client;

import events.EventHandler;
import net.client.Client;
import uno.client.args.HandshakedEventArgs;
import uno.client.args.NewPlayerEventArgs;
import uno.client.args.StartedGameEventArgs;
import uno.client.args.UpdateStatusEventArgs;
import uno.common.cards.Card;
import uno.common.cards.CardColor;
import uno.common.cards.CardType;

import java.util.Optional;
import java.util.Scanner;

public class UnoClient extends Client {
    public static final String PARAM_SEPARATOR = "|";
    public static final String ITEM_SEPARATOR = "~,~";
    public static final String OBJECT_SEPARATOR = "$,$";


    public static final String HANDSHAKE = "Handshake";
    public static final String HANDSHAKED = "Handshaked";
    public static final String PLAYER_JOINED = "PlayerJoined";
    public static final String STARTED_GAME = "StartedGame";
    public static final String UPDATE_STATUS = "UpdateStatus";
    public static final String PLAYER_TURN = "PlayerTurn";
    public static final String PLAYER_WON = "PlayerWon";


    private final EventHandler eventHandler = new EventHandler();

    private final String playerName;


    public UnoClient(String playerName){
        super(askUserForIp(), askUserForPort());
        playerName = playerName.replace(PARAM_SEPARATOR, "");
        playerName = playerName.replace(ITEM_SEPARATOR, "");
        playerName = playerName.replace(OBJECT_SEPARATOR, "");
        this.playerName = playerName;
    }


    public EventHandler getEventHandler(){
        return eventHandler;
    }

    @Override
    public void connectedToServer(){
        sendMessage(HANDSHAKE + PARAM_SEPARATOR + playerName);
    }

    @Override
    public void receivedMessage(String message){
        String[] params = (message.split("\\" + PARAM_SEPARATOR));

        if (params.length == 0) throw new IllegalArgumentException("Received empty message from server!");

        switch (params[0]){
            case HANDSHAKED -> {
                if (params.length < 2) throw new IllegalArgumentException("Handshake from server didn't have enough parameters! '" + message + "'");

                try {
                    int id = Integer.parseInt(params[1]);
                    eventHandler.InvokeEvent(HANDSHAKED, new HandshakedEventArgs(id));
                } catch (Exception ex){
                    throw new IllegalArgumentException("Received invalid handshake id from server! '" + message + "'");
                }
            }
            case PLAYER_JOINED -> {
                if (params.length < 3) throw new IllegalArgumentException("Player joined from server didn't have enough parameters! '" + message + "'");

                try {
                    String name = params[1];
                    int id = Integer.parseInt(params[2]);
                    eventHandler.InvokeEvent(HANDSHAKED, new NewPlayerEventArgs(name, id));
                } catch (Exception ex){
                    throw new IllegalArgumentException("Received invalid handshake from server! '" + message + "'");
                }
            }
            case STARTED_GAME -> {
                if (params.length < 3) throw new IllegalArgumentException("Started game from server didn't have enough parameters! '" + message + "'");

                String[] playerIDs = params[1].split(ITEM_SEPARATOR);
                int[] playerOrder = new int[playerIDs.length];
                try {
                    for (int i = 0; i < playerIDs.length; i++) {
                        String playerId = playerIDs[i];
                        playerOrder[i] = Integer.parseInt(playerId);
                    }
                    eventHandler.InvokeEvent(STARTED_GAME, new StartedGameEventArgs(playerOrder));
                }
                catch (NumberFormatException ex){
                    throw new IllegalArgumentException("Received invalid started game from server! '" + message + "'");
                }
            }
            case UPDATE_STATUS -> {
                if (params.length < 4) throw new IllegalArgumentException("Update status from server didn't have enough parameters! '" + message + "'");
                Optional<Card> topCard = getCardFromString(params[1]);
                if (topCard.isEmpty()) throw new IllegalArgumentException("Update status had an invalid card! '" + message + "'");

                String[] cardStrings = params[2].split(ITEM_SEPARATOR);
                Card[] userCards = new Card[cardStrings.length];

                for (int i = 0; i < cardStrings.length; i++){
                    Optional<Card> card = getCardFromString(cardStrings[i]);
                    if (card.isEmpty()) throw new IllegalArgumentException("Update status had an invalid card! '" + message + "'");
                    userCards[i] = card.get();
                }

                String[] cardAmountStrings = params[3].split(ITEM_SEPARATOR);
                UpdateStatusEventArgs.PlayerCardCount[] playerCardCounts = new UpdateStatusEventArgs.PlayerCardCount[cardAmountStrings.length];
                for (int i = 0; i < cardAmountStrings.length; i++){
                    String[] object = cardAmountStrings[i].split(OBJECT_SEPARATOR);
                    try {
                        int id = Integer.parseInt(object[0]);
                        int amount = Integer.parseInt(object[1]);
                        playerCardCounts[i] = new UpdateStatusEventArgs.PlayerCardCount(id, amount);
                    } catch (Exception ex){
                        throw new IllegalArgumentException("Update status had an invalid player amount! '" + message + "'");
                    }
                }

                eventHandler.InvokeEvent(UPDATE_STATUS, new UpdateStatusEventArgs(topCard.get(), userCards, playerCardCounts));
            }
            case PLAYER_TURN -> {
                if (params.length < 2) throw new IllegalArgumentException("Player turn from server didn't have enough parameters! '" + message + "'");

                try {
                    int id = Integer.parseInt(params[1]);
                    eventHandler.InvokeEvent(PLAYER_TURN, new HandshakedEventArgs(id));
                } catch (Exception ex){
                    throw new IllegalArgumentException("Received invalid player turn id from server! '" + message + "'");
                }
            }
            case PLAYER_WON -> {
                if (params.length < 2) throw new IllegalArgumentException("Player won from server didn't have enough parameters! '" + message + "'");

                try {
                    int id = Integer.parseInt(params[1]);
                    eventHandler.InvokeEvent(PLAYER_WON, new HandshakedEventArgs(id));
                } catch (Exception ex){
                    throw new IllegalArgumentException("Received invalid player won id from server! '" + message + "'");
                }
            }
        }
    }


    private Optional<Card> getCardFromString(String cardString){
        String[] cardData = cardString.split(OBJECT_SEPARATOR);
        if (cardData.length != 2) return Optional.empty();

        CardColor cardColor = null;
        for (CardColor value : CardColor.values()){
            if (cardData[0].equals(value.getString())) cardColor = value;
        }

        CardType cardType = null;
        for (CardType value : CardType.values()){
            if (cardData[0].equals(value.getString())) cardType = value;
        }

        if (cardColor == null || cardType == null) return Optional.empty();

        return Optional.of(new Card(cardColor, cardType));
    }

    private static String askUserForIp() {
        Scanner input = new Scanner(System.in);

        System.out.print("What is the ip of the server? ");
        return input.nextLine();
    }

    private static int askUserForPort() {
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("What is the port of the server? ");
            if (input.hasNextInt()){
                int port = input.nextInt();
                input.nextLine();
                if (port <= 65535) return port;
            }
            input.nextLine();
            System.out.println("Pleas input a valid port!");
        } while (true);
    }
}
