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


import java.util.Random;

public class MonsterFactory implements IEntityFactoryService {
    private Entity monster;

    @Override
    public void spawn(GameData gameData, World world, Integer amount) {
        monster = createMonster(world, amount);
        world.addEntity(monster);
    }

    private Entity createMonster(World world, int hpScaling) {
        String file = "Goblin_king.png";
        String goblin_death = "Sounds/goblin_death.mp3";
        String goblin_attack = "Sounds/goblin_attack.mp3";
        String[] files = {file,goblin_attack,goblin_death};

        Entity monster = new Monster(file, 5);
        monster.add(new MovingPart(30));
        monster.add(new LifePart(100 + (hpScaling * 50), Color.TEAL));
        monster.add(new AnimationPart());
        monster.add(new StatsPart(20, 5, 0));

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
        world.removeEntity(monster);
    }
}
