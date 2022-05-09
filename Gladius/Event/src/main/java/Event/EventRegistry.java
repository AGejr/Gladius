package Event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventRegistry {

    private static List<GAME_EVENT> events = new CopyOnWriteArrayList<>();

    public static void addEvent(GAME_EVENT game_event) {
        events.add(game_event);
    }

    public static void removeEvent(GAME_EVENT game_event) {
        events.removeIf(game_event1 -> game_event1 == game_event);
    }

    public static void removeAllEvents(){
        for (GAME_EVENT event: getEvents()){
            events.remove(event);
        }
    }

    public static List<GAME_EVENT> getEvents() {
        return events;
    }
}
