package events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventHandler {
    private final HashMap<String, ArrayList<EventListener>> events = new HashMap<>();

    public void AddListener(String event, EventListener eventListener){
        if (!events.containsKey(event)) events.put(event, new ArrayList<>());

        events.get(event).add(eventListener);
    }

    public void InvokeEvent(String event, EventArgs args){
        if (!events.containsKey(event)) return;

        for (EventListener eventListener : events.get(event)){
            eventListener.invoke(args);
        }
    }
}