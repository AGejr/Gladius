package Monster;

import Common.data.GameData;
import Common.data.SoundData;
import Common.data.World;
import Common.data.Entity;
import Common.data.entityparts.*;
import Common.services.IEntityFactoryService;
import Common.tools.FileLoader;
import com.badlogic.gdx.graphics.Color;
import CommonMonster.Monster;


import java.util.ArrayList;
import java.util.Random;

public class MonsterFactory implements IEntityFactoryService {

    ArrayList<Entity> monsters = new ArrayList<Entity>();;

    @Override
    public void spawn(GameData gameData, World world, Integer waveNumber) {
        int spawnAmount = (waveNumber/4)+1;
        for(int i = 0; i < spawnAmount; i++ ) {
            Entity monster = createMonster(world, waveNumber);
            monsters.add(monster);
            world.addEntity(monster);
        }
    }

    private Entity createMonster(World world, int waveNumber) {
        String file = "Goblin_king.png";
        String goblin_death = "Sounds/goblin_death.mp3";
        String goblin_attack = "Sounds/goblin_attack.mp3";
        String[] files = {file,goblin_attack,goblin_death};

        // in the modifiers, 2 and 4 are magic numbers. Every 2nd wave, the attack modifier will be upped by 1
        int attackModifier = (waveNumber/2)*(waveNumber/4);
        int defenceModifier = (waveNumber/4)*(waveNumber/8);
        int hpScaling = (30*((waveNumber/2)+1))*waveNumber;
        int speed = new Random().nextInt((70-30)+1) + 30;

        Entity monster = new Monster(file, 5,30f/speed);
        monster.add(new MovingPart(speed));
        monster.add(new LifePart(100 + hpScaling, Color.TEAL));
        monster.add(new AnimationPart());
        monster.add(new StatsPart(20+attackModifier, 5+defenceModifier, 0));

        SoundPart soundPart = new SoundPart();

        soundPart.putAudio(SoundData.SOUND.ATTACK, goblin_attack);
        soundPart.putAudio(SoundData.SOUND.DEATH, goblin_death);

        monster.add(soundPart);
        FileLoader.loadFiles(files, getClass());

        do{
            //1350 is max x value of arena, 220 is min
            monster.setX(new Random().nextInt((1350 - 220) + 1) + 220);
            //1000 is max y value of arena, 350 is min
            monster.setY(new Random().nextInt((1000 - 350) + 1) + 350);
        } while(world.getCsvMap().get((int) monster.getY()/32).get((int) monster.getX()/32) == 1);

        return monster;
    }

    @Override
    public void stop(World world) {
        monsters.clear();
        for (Entity monster: world.getEntities(Monster.class)){
            world.removeEntity(monster);
        }

    }
}
