package uno.client;

import events.EventHandler;

public class UnoClient {

    private final EventHandler eventHandler = new EventHandler();

    public UnoClient(String playerName){

    }


    public EventHandler getEventHandler(){
        return eventHandler;
    }
}
