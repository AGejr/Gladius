package Player;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.services.IGamePluginService;
import Common.tools.FileLoader;


public class PlayerPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        Entity player = createPlayer(gameData);
        world.addEntity(player);
    }

    private Entity createPlayer(GameData gamedata) {
        String file = "GladiatorSpriteSheet.png";
        Entity player = new Player(file, 2);
        player.add(new MovingPart(50));
        FileLoader.loadFile(file, getClass());

        player.setX(800);
        player.setY(140);
        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            world.removeEntity(entity);
        }
    }
}
