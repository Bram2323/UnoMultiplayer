package uno.server;

import uno.common.cards.Card;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

public class Lobby {
    private final UnoServer server = new UnoServer();
    private final LinkedList<Player> players = new LinkedList<>();

    private final static String PARAM_SEPERATOR = "|";
    private final static String ARRAY_SEPERATOR = "~,~";
    private final static String OBJECT_SEPERATOR = "$,$";

    private static final String STARTED_GAME_COMMAND = "StartedGame";
    private static final String UPDATE_STATUS_COMMAND = "UpdateStatus";
    private static final String PLAYERS_TURN_COMMAND = "PlayersTurn";

    private Game game;
    private boolean gameInProgress = false;

    public Lobby() {
        server.start();

        initEvents();
    }

    private void initEvents() {
        server.setOnHandshake((playerId, playerName) -> {
            players.add(new Player(playerId, playerName));
        });

        server.setOnStartGame(playerId -> {
            startGame();
        });

        server.setOnPlayCard((playerId, card) -> {
            Player player = getPlayerById(playerId).orElseThrow(
                () -> new IllegalArgumentException("player unknown")
            );

            game.playCard(player, card);

            broadcastGameState();
        });

        server.setOnDrawCard(playerId -> {
            Player player = getPlayerById(playerId).orElseThrow(
                () -> new IllegalArgumentException("player unknown")
            );

            game.drawCard(player);

            broadcastGameState();
        });
    }

    private void startGame(){
        Player[] gamePlayers = new Player[players.size()];

        Iterator<Player> iterator =  players.iterator();
        int index = 0;

        while(index < gamePlayers.length && iterator.hasNext()){
            gamePlayers[index] = iterator.next();
            index++;
        }

        game = new Game(gamePlayers);
        gameInProgress = true;

        broadcastStartedGame();
        broadcastGameState();
    }

    private void broadcastStartedGame(){
        StringBuilder playerIdString = new StringBuilder();

        for(Player player : players){
            if(!playerIdString.isEmpty()){
                playerIdString.append(ARRAY_SEPERATOR);
            }

            playerIdString.append(player.getId());
        }

        server.broadcastMessage(STARTED_GAME_COMMAND + "|" + playerIdString);
    }

    private void broadcastGameState(){
        broadcastUpdateStatus();
        broadcastPlayersTurn();
    }

    private void broadcastUpdateStatus(){
        Card lastCard = null;//TODO get last card

        for(Player player : players){
            String message = UPDATE_STATUS_COMMAND +
                PARAM_SEPERATOR + lastCard +
                PARAM_SEPERATOR + getPlayerCardString(player) +
                PARAM_SEPERATOR + getPlayerCardCountString(player);

            server.sendMessage(player.getId(), message);
        }
    }

    private String getPlayerCardString(Player player){
        StringBuilder playerCardString = new StringBuilder();

        for(Card card : player.getCardsInHand()){
            if(!playerCardString.isEmpty()){
                playerCardString.append(ARRAY_SEPERATOR);
            }

            playerCardString.append(card);
        }

        return playerCardString.toString();
    }

    private String getPlayerCardCountString(Player player){
        StringBuilder cardCountString = new StringBuilder();

        for(Player otherPlayer : players){
            if(otherPlayer == player){
                continue;
            }

            if(!cardCountString.isEmpty()){
                cardCountString.append(ARRAY_SEPERATOR);
            }

            cardCountString.append(otherPlayer.getId());
            cardCountString.append(OBJECT_SEPERATOR);
            cardCountString.append(otherPlayer.getCardsInHand().size());
        }

        return cardCountString.toString();
    }

    private void broadcastPlayersTurn(){
        Player currentPlayer = null;//TODO get current player

        server.broadcastMessage(PLAYERS_TURN_COMMAND + "|" + currentPlayer.getId());
    }

    private Optional<Player> getPlayerById(int id){
        for(Player player : players){
            if(player.getId() == id){
                return Optional.of(player);
            }
        }

        return Optional.empty();
    }
}
