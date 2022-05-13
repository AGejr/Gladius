package Enemy;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.SoundData;
import Common.data.World;
import Common.data.entityparts.*;
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
            Entity enemy = createMinotaur(world);
            enemies.add(enemy);
            world.addEntity(enemy);
        }
    }

    private Entity createMinotaur(World world) {
        String texture = "Minotaur.png";
        String minotaur_death = "Sounds/minotaur_death.mp3";
        String mintoaur_attack = "Sounds/minotaur_attack.mp3";
        String[] files = {texture, minotaur_death, mintoaur_attack};

        // radius should be texture width / 16
        Entity enemy = new Enemy(texture, 20);
        enemy.add(new MovingPart(30));
        enemy.add(new LifePart(100, Color.RED));
        enemy.add(new AnimationPart());
        enemy.add(new StatsPart(5,0, 0, 5, 0));

        SoundPart soundPart = new SoundPart();
        soundPart.putAudio(SoundData.SOUND.DEATH, minotaur_death);
        soundPart.putAudio(SoundData.SOUND.ATTACK, mintoaur_attack);
        enemy.add(soundPart);

        FileLoader.loadFiles(files, getClass());

        do{
            //1350 is max x value of arena, 220 is min
            enemy.setX(new Random().nextInt((1350 - 220) + 1) + 220);
            //1000 is max y value of arena, 350 is min
            enemy.setY(new Random().nextInt((1000 - 350) + 1) + 350);
        } while(world.getCsvMap().get((int) enemy.getY()/32).get((int) enemy.getX()/32) == 1);

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
