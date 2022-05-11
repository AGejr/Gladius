package Monster;

import Common.data.GameData;
import Common.data.World;
import Common.data.Entity;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.MovingPart;
import Common.data.entityparts.StatsPart;
import Common.services.IGamePluginService;
import Common.tools.FileLoader;
import com.badlogic.gdx.graphics.Color;
import CommonMonster.Monster;


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
