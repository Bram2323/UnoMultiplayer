package uno.client.args;

import events.EventArgs;

public class NewPlayerEventArgs extends EventArgs {
    private final String name;
    private final int id;

    public NewPlayerEventArgs(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
