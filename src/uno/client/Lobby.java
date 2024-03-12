package uno.client;

import uno.client.args.*;

import java.util.LinkedList;
import java.util.Scanner;

public class Lobby {
    private static final String HANDSHAKED_EVENT = "Handshake";
    private static final String NEW_PLAYER_EVENT = "NewPlayer";
    private static final String STARTED_GAME_EVENT = "StartedGame";
    private static final String UPDATE_STATUS_EVENT = "UpdateStatus";
    private static final String PLAYER_TURN_EVENT = "PlayersTurn";
    private static final String PLAYER_WON_EVENT = "PlayerWon";

    private UnoClient client;
    private Game game = null;

    private LinkedList<Player> players;

    private String playername;

    public Lobby(){
        playername = askPlayerName();

        client = new UnoClient(playername);
        //client.start();

        players = new LinkedList<>();
    }

    private String askPlayerName(){
        System.out.print("What is your name? ");

        Scanner scanner = new Scanner(System.in);

        while(true){
            String nameInput = scanner.nextLine();

            if(nameInput.isBlank()){
                System.out.println("Name must have at least 1 characater");
                continue;
            }

            scanner.close();
            return nameInput;
        }
    }

    private void initEvents(){
        client.getEventHandler().AddListener(HANDSHAKED_EVENT, args -> {
            if(args instanceof HandshakedEventArgs hsArgs){
                onHandshaked(hsArgs);
            }
        });

        client.getEventHandler().AddListener(NEW_PLAYER_EVENT, args -> {
            if (args instanceof NewPlayerEventArgs npArgs){
                onNewPlayer(npArgs);
            }
        });

        client.getEventHandler().AddListener(STARTED_GAME_EVENT, args -> {
            if(args instanceof StartedGameEventArgs sgArgs){
                onStartedGame(sgArgs);
            }
        });

        client.getEventHandler().AddListener(UPDATE_STATUS_EVENT, args -> {
            if(args instanceof UpdateStatusEventArgs usArgs){
                onUpdateStatus(usArgs);
            }
        });

        client.getEventHandler().AddListener(PLAYER_TURN_EVENT, args -> {
            if(args instanceof PlayersTurnEventArgs ptArgs){
                onPlayersTurn(ptArgs);
            }
        });

        client.getEventHandler().AddListener(PLAYER_WON_EVENT, args -> {
            if(args instanceof PlayerWonEventArgs pwArgs){
                onPlayerWon(pwArgs);
            }
        });
    }

    private void onHandshaked(HandshakedEventArgs args){
        players.add(new LocalPlayer(args.getId(), playername));
    }

    private void onNewPlayer(NewPlayerEventArgs args){
        players.add(new ExternalPlayer(args.getId(), args.getName()));

        System.out.println("PLAYERS:");
        for (Player player : players) {
            System.out.println(player.getName());
        }
    }

    private void onStartedGame(StartedGameEventArgs args){
        System.out.println("Game started!");


        game = new Game(getPlayerArray(), args.getPlayerOrder());
    }

    private void onUpdateStatus(UpdateStatusEventArgs args){
        if(game == null){
            return;
        }

        game.setLastCard(args.getLastCard());

        int index = 0;
        for(Player player : players){
            if(player instanceof ExternalPlayer externalPlayer){
                for(UpdateStatusEventArgs.PlayerCardCount cardCount : args.getOtherPlayerCardCounts()){
                    if(cardCount.id() == externalPlayer.getId()){
                        externalPlayer.setCardCount(cardCount.count());
                    }
                }
            }
            else if(player instanceof LocalPlayer localPlayer){
                localPlayer.setCards(args.getPlayerCards());
            }
        }
    }

    private void onPlayersTurn(PlayersTurnEventArgs args){
        game.playerTurn(args.getTurnPlayer());
    }

    private void onPlayerWon(PlayerWonEventArgs args){
        game.playerWon(args.getWonPlayer());
    }

    private Player[] getPlayerArray(){
        Player[] players = new Player[this.players.size()];

        int index = 0;
        for(Player player : this.players){
            players[index] = player;
            index++;
        }

        return players;
    }
}
