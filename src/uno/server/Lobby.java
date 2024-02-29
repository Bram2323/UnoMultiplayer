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
        });

        server.setOnDrawCard(playerId -> {
            Player player = getPlayerById(playerId).orElseThrow(
                () -> new IllegalArgumentException("player unknown")
            );

            game.drawCard(player);
        });
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
        for(Player player : players){
            if(player.getId() == id){
                return Optional.of(player);
            }
        }

        return Optional.empty();
    }
}
