package Common.events;

import Common.data.GameData;
import Common.data.World;
import Common.services.IEventProcessingService;

import java.util.List;

public class EventProcessor implements IEventProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (GAME_EVENT game_event: gameData.getEventRegistry().getEvents()){
            switch (game_event) {
                case ARENA_ENTERED:
                    processArenaEnteredEvent(gameData, world);
                    break;
                case ARENA_EXITED:
                    processArenaExitedEvent(gameData, world);
                    break;
                case WAVE_STARTED:
                    processWaveStartedEvent(gameData, world);
                    break;
                case WAVE_COMPLETED:
                    processWaveCompletedEvent(gameData, world);
                    break;
                case PLAYER_DIED:
                    processPlayerDiedEvent(gameData, world);
                    break;
            }
            gameData.getEventRegistry().removeEvent(game_event);
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
