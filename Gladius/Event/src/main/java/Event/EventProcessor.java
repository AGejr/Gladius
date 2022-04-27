package Event;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.services.IEntityFactoryService;
import Common.services.IEventProcessingService;
import Common.ui.UI;
import Enemy.Enemy;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

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
                    process_entity_killed(gameData, world);
                    break;
                case PLAYER_DIED:
                    processPlayerDiedEvent(gameData, world);
                    break;
            }
            EventRegistry.removeEvent(game_event);
        }
    }

    /**
     * @param world
     * @return true if all enemies are dead, otherwise return false
     */
    private boolean wave_is_completed(World world){
        for (Entity entity: world.getEntities(Enemy.class)){
            LifePart lifePart = entity.getPart(LifePart.class);
            if (!lifePart.isDead()){
                return false;
            }
        }
        return true;
    }

    private void process_entity_killed(GameData gameData, World world) {
        if (wave_is_completed(world)) {
            EventRegistry.addEvent(GAME_EVENT.WAVE_COMPLETED);
        }
    }

    private void processArenaEnteredEvent(GameData gameData, World world) {
        gameData.setGateEnabled(false);
        EventRegistry.addEvent(GAME_EVENT.WAVE_STARTED);
    }

    private void processArenaExitedEvent(GameData gameData, World world) {
        for(Entity enemy: world.getEntities(Enemy.class)){
            world.removeEntity(enemy);
        }
    }

    private void processWaveStartedEvent(GameData gameData, World world) {
        for (IEntityFactoryService entityFactoryService: world.getEntityFactoryList()){
            entityFactoryService.spawn(gameData, world, gameData.getWave());
        }
    }

    private void processWaveCompletedEvent(GameData gameData, World world) {
        gameData.setGateEnabled(true);
        // TODO: center text
        UI.addFreeTypeText(gameData, "Wave" + Integer.toString(gameData.getWave()) + " cleared!", 250f, 320f, 3f);
        gameData.incrementWave();
    }

    private void processPlayerDiedEvent(GameData gameData, World world) {
    }
}
