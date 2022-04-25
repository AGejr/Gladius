package Event;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.services.IEntityFactoryService;
import Common.services.IEventProcessingService;
import Enemy.Enemy;

public class EventProcessor implements IEventProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (GAME_EVENT game_event: EventRegistry.getEvents()){
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
                case ENTITY_KILLED:
                    if (wave_completed(world)) {EventRegistry.addEvent(GAME_EVENT.WAVE_COMPLETED);}
                    break;
                case PLAYER_DIED:
                    processPlayerDiedEvent(gameData, world);
                    break;
            }
            EventRegistry.removeEvent(game_event);
        }
    }

    private boolean wave_completed(World world){
        for (Entity entity: world.getEntities()){
            if (entity instanceof Enemy){
                LifePart lifePart = entity.getPart(LifePart.class);
                if (!lifePart.isDead()){
                    return false;
                }
            }
        }
        return true;
    }

    private void processArenaEnteredEvent(GameData gameData, World world) {
        gameData.setGateEnabled(false);
        for (IEntityFactoryService entityFactoryService: world.getEntityFactoryList()){
            entityFactoryService.spawn(gameData, world, 1);
        }
    }

    private void processArenaExitedEvent(GameData gameData, World world) {
    }

    private void processWaveStartedEvent(GameData gameData, World world) {
    }

    private void processWaveCompletedEvent(GameData gameData, World world) {
        gameData.setGateEnabled(true);
    }

    private void processPlayerDiedEvent(GameData gameData, World world) {
    }
}
