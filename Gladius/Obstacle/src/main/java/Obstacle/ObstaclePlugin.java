package Obstacle;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.StatsPart;
import Common.services.IGamePluginService;
import Common.tools.FileLoader;

import java.util.ArrayList;
import java.util.Random;

public class ObstaclePlugin implements IGamePluginService {

    private ArrayList<Entity> obstacles;

    @Override
    public void start(GameData gameData, World world) {
        obstacles = new ArrayList<>();
        obstacles.add(createBarrel(gameData));
        obstacles.add(createChest(gameData));
        obstacles.add(createCrate(gameData));
        obstacles.add(createJar(gameData));
        obstacles.add(createExplosiveBarrel(gameData));
        obstacles.add(createExplosiveBarrel(gameData));
        obstacles.add(createExplosiveBarrel(gameData));
        obstacles.add(createExplosiveBarrel(gameData));
        obstacles.add(createExplosiveBarrel(gameData));
        for(Entity entity : obstacles) {
            world.addEntity(entity);
        }
    }

    private Entity createBarrel(GameData gamedata) {
        String file = "Barrel.png";
        Entity barrel = new Obstacle(file, 10, 0, -5, 64, 64, 0, 0.3f, 0.3f, 32, 32);
        barrel.add(new LifePart(100));
        // BARREL HAS A SPIKE ATTACK FOR TEST PURPOSES
        barrel.add(new StatsPart(0,10,0,0, 0));
        barrel.add(new AnimationPart());
        FileLoader.loadFile(file, getClass());

        barrel.setX(new Random().nextInt((1000 - 200) + 1) + 200);
        barrel.setY(new Random().nextInt((1000 - 400) + 1) + 400);

        return barrel;
    }

    private Entity createExplosiveBarrel(GameData gamedata) {
        String file = "ExplosiveBarrel.png";
        Entity explosiveBarrel = new Obstacle(file, 10, 0, -5, 64, 64, 0, 0.3f, 0.3f, 32, 32);
        explosiveBarrel.add(new LifePart(100));
        explosiveBarrel.add(new StatsPart(0,0,50,40, 0));
        explosiveBarrel.add(new AnimationPart());
        FileLoader.loadFile(file, getClass());

        explosiveBarrel.setX(new Random().nextInt((1000 - 200) + 1) + 200);
        explosiveBarrel.setY(new Random().nextInt((1000 - 400) + 1) + 400);

        return explosiveBarrel;
    }

    private Entity createChest(GameData gamedata) {
        String file = "Chest.png";
        Entity chest = new Obstacle(file, 10, 0 ,-2, 64, 64, 0, 0.3f, 0.2f, 32, 32);
        chest.add(new LifePart(100));
        chest.add(new StatsPart(0,0,0, 0,0));
        chest.add(new AnimationPart());
        FileLoader.loadFile(file, getClass());

        chest.setX(new Random().nextInt((1000 - 200) + 1) + 200);
        chest.setY(new Random().nextInt((1000 - 400) + 1) + 400);

        return chest;
    }

    private Entity createCrate(GameData gamedata) {
        String file = "Crate.png";
        Entity crate = new Obstacle(file, 14, 0 ,-4, 64, 64, 0, 0.3f, 0.3f, 32, 32);
        crate.add(new LifePart(100));
        crate.add(new StatsPart(0,0,0, 0,0));
        crate.add(new AnimationPart());
        FileLoader.loadFile(file, getClass());

        crate.setX(new Random().nextInt((1000 - 200) + 1) + 200);
        crate.setY(new Random().nextInt((1000 - 400) + 1) + 400);

        return crate;
    }

    private Entity createJar(GameData gamedata) {
        String file = "Jar.png";
        Entity jar = new Obstacle(file, 10, 0, -2, 64, 64, 0, 0.2f, 0.2f, 32, 32);
        jar.add(new LifePart(100));
        jar.add(new StatsPart(0,0,0, 0,0));
        jar.add(new AnimationPart());
        FileLoader.loadFile(file, getClass());

        jar.setX(new Random().nextInt((1000 - 200) + 1) + 200);
        jar.setY(new Random().nextInt((1000 - 400) + 1) + 400);

        return jar;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(Entity entity : obstacles) {
            world.removeEntity(entity);
        }
    }
}
