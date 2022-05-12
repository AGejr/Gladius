package Event;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.SoundData;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.StatsPart;
import Common.services.IEntityFactoryService;
import Common.services.IEventProcessingService;
import Common.ui.Text;
import Common.ui.UI;
import CommonEnemy.Enemy;
import CommonMonster.Monster;
import CommonPlayer.Player;

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
        for (Entity entity: world.getEntities(Monster.class)){
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
        for (Entity player: world.getEntities(Player.class)){
            LifePart playerLifePart = player.getPart(LifePart.class);
            if (playerLifePart.isDead()) {
                EventRegistry.addEvent(GAME_EVENT.PLAYER_DIED);
            }
            StatsPart statsPart = player.getPart(StatsPart.class);
            int life = playerLifePart.getLife();
            if (life > 90) {
                statsPart.depositBalance(75);
            } else if (life > 60) {
                statsPart.depositBalance(50);
            } else {
                statsPart.depositBalance(25);
            }
        }
    }

    private void processArenaEnteredEvent(GameData gameData, World world) {
        gameData.setGateEnabled(false);
        gameData.getSoundData().playSound(SoundData.SOUND.ARENA_ENTERED);
        EventRegistry.addEvent(GAME_EVENT.WAVE_STARTED);
    }

    private void processArenaExitedEvent(GameData gameData, World world) {
        for(Entity enemy: world.getEntities(Enemy.class)){
            world.removeEntity(enemy);
        }
        for(Entity monster: world.getEntities(Monster.class)){
            world.removeEntity(monster);
        }
        for (Entity player: world.getEntities(Player.class)){
            LifePart lifePart = player.getPart(LifePart.class);
            lifePart.resetHealth();
        }
        gameData.getSoundData().playSound(SoundData.SOUND.ARENA_EXIT);
    }

    private void processWaveStartedEvent(GameData gameData, World world) {
        for (IEntityFactoryService entityFactoryService: world.getEntityFactoryList()){
            entityFactoryService.spawn(gameData, world, gameData.getWave());
        }
        String string = "Wave " + Integer.toString(gameData.getWave()) + " started!";
        Text text = new Text(string, 3, 20,2);
        text.alignScreenCenter(gameData);
        UI.addText(text);
    }

    private void processWaveCompletedEvent(GameData gameData, World world) {
        gameData.setGateEnabled(true);

        String string = "Wave " + Integer.toString(gameData.getWave()) + " cleared!";
        Text text = new Text(string,3,20,3);
        text.alignScreenCenter(gameData);
        UI.addText(text);

        gameData.getSoundData().playSound(SoundData.SOUND.WAVE_CLEARED);

        gameData.incrementWave();
    }

    private void processPlayerDiedEvent(GameData gameData, World world) {
        Text text = new Text("GAME OVER",3,20);
        text.alignScreenCenter(gameData);
        UI.addText(text);
    }
}
