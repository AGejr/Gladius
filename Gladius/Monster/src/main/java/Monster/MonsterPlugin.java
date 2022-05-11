package Monster;

import Common.data.GameData;
import Common.data.SoundData;
import Common.data.World;
import Common.data.Entity;
import Common.data.entityparts.*;
import Common.services.IGamePluginService;
import Common.tools.FileLoader;
import com.badlogic.gdx.graphics.Color;
import CommonMonster.Monster;


import javax.sound.midi.Soundbank;
import java.util.Random;

public class MonsterPlugin implements IGamePluginService {
    private Entity monster;

    @Override
    public void start(GameData gameData, World world) {
        monster = createMonster(gameData);
        world.addEntity(monster);
    }

    private Entity createMonster(GameData gameData) {
        String file = "Goblin_king.png";

        Entity monster = new Monster(file, 5);
        monster.add(new MovingPart(30));
        monster.add(new LifePart(100, Color.TEAL));
        monster.add(new AnimationPart());
        monster.add(new StatsPart(20, 5, 0));

        SoundPart soundPart = new SoundPart(gameData);

        soundPart.putAudio(SoundData.SOUND.ATTACK, "Sounds/goblin_attack.mp3");
        soundPart.putAudio(SoundData.SOUND.DEATH, "Sounds/goblin_death.mp3");
        monster.add(soundPart);
        FileLoader.loadFile(file, getClass());

        //400 is max, 280 is min
        monster.setX(new Random().nextInt((580 - 385) + 1) + 385);
        monster.setY(new Random().nextInt((1000 - 350) + 1) + 350);
        return monster;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(this.monster);
    }
}
