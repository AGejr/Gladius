package Common.events;

import Common.data.GameData;
import Common.data.World;
import Common.services.IEventProcessingService;

import java.util.List;

public class EventProcessor implements IEventProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (GAME_EVENT game_event: EventRegistry.getEvents()){
            switch (game_event) {
                case ARENA_ENTERED:
                    processArenaEnteredEvent(gameData, world);
                case ARENA_EXITED:
                    processArenaExitedEvent(gameData, world);
                case WAVE_STARTED:
                    processWaveStartedEvent(gameData, world);
                case WAVE_COMPLETED:
                    processWaveCompletedEvent(gameData, world);
                case PLAYER_DIED:
                    processPlayerDiedEvent(gameData, world);
            }
            EventRegistry.removeEvent(game_event);
        }
    }

    private void processArenaEnteredEvent(GameData gameData, World world) {
    }

    private void processArenaExitedEvent(GameData gameData, World world) {
    }

    private void processWaveStartedEvent(GameData gameData, World world) {
    }

    private void processWaveCompletedEvent(GameData gameData, World world) {
    }

    private void processPlayerDiedEvent(GameData gameData, World world) {
    }
}
