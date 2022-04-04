package Common.events;

import java.util.List;

public class EventRegistry {
    private static List<GAME_EVENT> events;

    public static void addEvent(GAME_EVENT game_event) {
        events.add(game_event);
    }

    public static void removeEvent(GAME_EVENT game_event) {
        events.removeIf(game_event1 -> game_event1 == game_event);
    }

    public static List<GAME_EVENT> getEvents() {
        return EventRegistry.events;
    }
}
