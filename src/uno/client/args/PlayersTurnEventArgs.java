package uno.client.args;

import events.EventArgs;
import uno.client.Player;

public class PlayersTurnEventArgs extends EventArgs {
    private final Player turnPlayer;

    public PlayersTurnEventArgs(Player turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }
}
