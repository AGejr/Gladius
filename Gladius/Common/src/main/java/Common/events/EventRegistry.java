package Common.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventRegistry {

    private List<GAME_EVENT> events;

    public EventRegistry() {
        this.events = new CopyOnWriteArrayList<>();
    }

    public void addEvent(GAME_EVENT game_event) {
        events.add(game_event);
    }

    public void removeEvent(GAME_EVENT game_event) {
        events.removeIf(game_event1 -> game_event1 == game_event);
    }

    public List<GAME_EVENT> getEvents() {
        return events;
    }
}
