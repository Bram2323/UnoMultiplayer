package uno.client.args;

import events.EventArgs;

public class HandshakedEventArgs extends EventArgs {
    private final int id;

    public HandshakedEventArgs(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
