package uno.server;

import net.server.Server;
import uno.common.cards.Card;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class UnoServer extends Server {

    public UnoServer() {
        super(0);
    }


    public void setOnHandshake(BiConsumer<Integer, String> onHandshake) {}
    public void setOnStartGame(Consumer<Integer> onStartGame) {}
    public void setOnPlayCard(BiConsumer<Integer, Card> onPlayCard) {}
    public void setOnDrawCard(Consumer<Integer> onDrawCard) {}


    @Override
    public void receivedMessage(int id, String message) {

    }

    @Override
    public void clientConnected(int id) {

    }
}
