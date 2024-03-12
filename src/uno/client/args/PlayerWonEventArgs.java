package uno.client.args;

import events.EventArgs;
import uno.client.Player;

public class PlayerWonEventArgs extends EventArgs {
    private Player wonPlayer;

    public PlayerWonEventArgs(Player wonPlayer) {
        this.wonPlayer = wonPlayer;
    }

    public Player getWonPlayer() {
        return wonPlayer;
    }
}
