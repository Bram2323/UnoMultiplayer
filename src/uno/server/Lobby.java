package uno.server;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

public class Lobby {
    private final UnoServer server = new UnoServer();
    private final LinkedList<Player> players = new LinkedList<>();

    private Game game;
    private boolean gameInProgress = false;

    public Lobby() {
        server.start();
    }

    private void initEvents() {
        server.setOnHandshake((playerId, playerName) -> {
            addPlayer((new Player()));
            //TODO player constructor undefined, add name/id later
        });

        server.setOnStartGame(playerId -> {
            startGame();
        });

        server.setOnPlayCard((playerId, card) -> {
            game.playCard(getPlayerById(playerId).get(), card);
            //TODO fix incorrect player
        });

        server.setOnDrawCard(playerId -> {
            game.drawCard(getPlayerById(playerId).get());
            //TODO fix incorrect player
        });
    }

    private void addPlayer(Player player){
        players.add(player);

        //private handshaked
        //boardcast new player
    }

    private void startGame(){
        Player[] gamePlayers = new Player[players.size()];

        Iterator<Player> iterator = players.iterator();
        int index = 0;

        while(index < gamePlayers.length && iterator.hasNext()){
            gamePlayers[index] = iterator.next();
            index++;
        }

        game = new Game(gamePlayers);
        gameInProgress = true;
    }

    private Optional<Player> getPlayerById(int id){
        //TODO fix when player::getId is fixed
        return !players.isEmpty() ? Optional.of(players.getFirst()) : Optional.empty();
    }
}
