package events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventHandler {
    private static final HashMap<String, ArrayList<EventListener>> events = new HashMap<>();

    public static void AddListener(String event, EventListener eventListener){
        if (!events.containsKey(event)) events.put(event, new ArrayList<>());

        events.get(event).add(eventListener);
    }

    public static void InvokeEvent(String event, EventArgs args){
        if (!events.containsKey(event)) return;

        for (EventListener eventListener : events.get(event)){
            eventListener.invoke(args);
        }
    }
}