package uno.server;

import net.server.Server;
import uno.common.cards.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UnoServer extends Server {

    BiConsumer<Integer, String> onHandshake;
    Consumer<Integer> onStartGame;
    BiConsumer<Integer, Card> onPlayCard;
    Consumer<Integer> onDrawCard;

    public UnoServer() {
        super(0);
    }


    public void setOnHandshake(BiConsumer<Integer, String> onHandshake) {
        this.onHandshake = onHandshake;
    }
    public void setOnStartGame(Consumer<Integer> onStartGame) {
        this.onStartGame = onStartGame;
    }
    public void setOnPlayCard(BiConsumer<Integer, Card> onPlayCard) {
        this.onPlayCard = onPlayCard;
    }
    public void setOnDrawCard(Consumer<Integer> onDrawCard) {
        this.onDrawCard = onDrawCard;
    }


    @Override
    public void receivedMessage(int id, String message) {
        String[] arguments = message.split("\\|");
        if (arguments.length == 0) return;

        switch (arguments[0].toLowerCase()){
            case "handshake" -> {
                if (arguments.length >= 2){
                    String name = arguments[1].replaceAll("\\||~,~|\\$,\\$", "");
                    sendMessage(id, String.valueOf(id));
                    onHandshake.accept(id, name);
                }
            }
            case "startgame" -> onStartGame.accept(id);
            case "playcard" -> {
                if (arguments.length >= 3){
                    try {
                        CardColor color = CardColor.valueOf(arguments[1].toUpperCase());
                        CardType type = CardType.valueOf(arguments[2].toUpperCase());
                        Card card = new Card(color, type);
                        onPlayCard.accept(id, card);
                    }
                    catch (IllegalArgumentException ex){

                    }
                }
            }
            case "drawcard" -> onDrawCard.accept(id);
        }
    }


    @Override
    public void clientConnected(int id) {

    }
}
