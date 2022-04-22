package Enemy;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.MovingPart;
import Common.data.entityparts.StatsPart;
import Common.services.IGamePluginService;
import Common.services.IEntityFactoryService;
import Common.tools.FileLoader;

import java.util.ArrayList;
import java.util.Random;

public class EnemyFactory implements IEntityFactoryService {

    ArrayList<Entity> enemies = new ArrayList<Entity>();;

    @Override
    public void spawn(GameData gameData, World world, Integer amount) {
        for (int i = 0; i < amount; i++) {
            Entity enemy = createMinotauer(gameData);
            enemies.add(enemy);
            world.addEntity(enemy);
        }
    }

    private Entity createMinotauer(GameData gamedata) {
        String file = "Minotaur.png";

        // radius should be texture width / 16
        Entity enemy = new Enemy(file, 6);
        enemy.add(new MovingPart(30));
        enemy.add(new LifePart(100));
        enemy.add(new AnimationPart());
        enemy.add(new LifePart(100));
        enemy.add(new StatsPart(5, 5));
        FileLoader.loadFile(file, getClass());

        enemy.setX(new Random().nextInt((1000 - 200) + 1) + 200);
        enemy.setY(new Random().nextInt((1000 - 400) + 1) + 400);

        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity enemy: enemies){
            world.removeEntity(enemy);
        }
    }
}
