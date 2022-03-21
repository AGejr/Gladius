package Player;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {
    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        System.out.println("START");
        player = new Player();
        player.setRadius(8);
        world.addEntity(player);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }
}
