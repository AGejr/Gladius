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

    @Override
    public void spawn(GameData gameData, World world, Integer waveNumber) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int spawnModifier = waveNumber/3;
                for (int i = 0; i < waveNumber + spawnModifier; i++) {
                    Entity enemy = createMinotaur(world, waveNumber);
                    world.addEntity(enemy);
                    enemy.initTextureFormAssetManager(gameData);
                }
            }
        }).start();
    }

    private Entity createMinotaur(World world, int waveNumber) {
        String texture = "Minotaur.png";
        String minotaur_death = "Sounds/minotaur_death.mp3";
        String mintoaur_attack = "Sounds/minotaur_attack.mp3";
        String[] files = {texture, minotaur_death, mintoaur_attack};

        // 2 and 5 are magic numbers
        int attackModifier = (waveNumber/3)*(waveNumber/6);
        int defenceModifier = (waveNumber/6)*(waveNumber/12);
        int healthModifier = (5*((waveNumber/5)+1))*waveNumber;
        int speed = new Random().nextInt((95-30)+1) + 30;
        // radius should be texture width / 16
        Entity enemy = new Enemy(texture, 20,30f/speed);
        enemy.add(new MovingPart(speed));
        enemy.add(new LifePart(100 + healthModifier, Color.RED));
        enemy.add(new AnimationPart());
        enemy.add(new StatsPart(5+attackModifier,0, 0, 5+defenceModifier, 0));

        SoundPart soundPart = new SoundPart();
        soundPart.putAudio(SoundData.SOUND.DEATH, minotaur_death);
        soundPart.putAudio(SoundData.SOUND.ATTACK, mintoaur_attack);
        enemy.add(soundPart);

        FileLoader.loadFiles(files, getClass());

        do{
            //1350 is max x value of arena, 220 is min
            enemy.setX(new Random().nextInt((1100 - 220) + 1) + 220);
            //1000 is max y value of arena, 350 is min
            enemy.setY(new Random().nextInt((1000 - 350) + 1) + 350);
        } while(world.getCsvMap().get((int) enemy.getY()/32).get((int) enemy.getX()/32) == 1);

        return enemy;
    }

    @Override
    public void stop(World world) {
        for (Entity enemy: world.getEntities(Enemy.class)){
            world.removeEntity(enemy);
        }
    }
}
