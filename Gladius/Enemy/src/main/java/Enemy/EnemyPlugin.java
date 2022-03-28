package Enemy;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.services.IGamePluginService;
import Common.tools.FileLoader;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gamedata) {
        String file = "Minotaur.png";

        Entity enemy = new Enemy(file, 2);
        enemy.add(new MovingPart(30));
        FileLoader.loadFile(file, getClass());

        enemy.setX(840);
        enemy.setY(500);
        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(this.enemy);
    }
}
