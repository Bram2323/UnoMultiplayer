package uno.client.args;

import events.EventArgs;

public class StartedGameEventArgs extends EventArgs {
    private int[] playerOrder;

    public StartedGameEventArgs(int[] playerOrder) {
        this.playerOrder = playerOrder;
    }

    public int[] getPlayerOrder() {
        return playerOrder;
    }
}
