package Enemy;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.SoundData;
import Common.data.World;
import Common.data.entityparts.*;
import Common.services.IGamePluginService;
import Common.services.IEntityFactoryService;
import Common.tools.FileLoader;
import CommonEnemy.Enemy;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.graphics.Color;

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
        enemy.add(new LifePart(100, Color.RED));
        enemy.add(new AnimationPart());
        enemy.add(new StatsPart(5, 5, 0));

        SoundPart soundPart = new SoundPart(gamedata);
        soundPart.putAudio(SoundData.SOUND.DEATH, "Sounds/minotaur_death.mp3");
        soundPart.putAudio(SoundData.SOUND.ATTACK, "Sounds/minotaur_attack.mp3");
        enemy.add(soundPart);



        FileLoader.loadFile(file, getClass());


        //400 is max, 280 is min
        enemy.setX(new Random().nextInt((580 - 385) + 1) + 385);
        enemy.setY(new Random().nextInt((1000 - 350) + 1) + 350);

        return enemy;
    }

    @Override
    public void stop(World world) {
        enemies.clear();
        for (Entity enemy: world.getEntities(Enemy.class)){
            world.removeEntity(enemy);
        }
    }
}
