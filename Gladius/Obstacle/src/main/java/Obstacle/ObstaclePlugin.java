package Obstacle;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.LifePart;
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
        for(Entity entity : obstacles) {
            world.addEntity(entity);
        }
    }

    private Entity createPillar(GameData gamedata) {
        String file = "Objects.png";
        // radius should be texture width / 16
        Entity pillar = new Obstacle(file, 4);
        pillar.add(new AnimationPart());
        FileLoader.loadFile(file, getClass());

        pillar.setX(new Random().nextInt((1000 - 200) + 1) + 200);
        pillar.setY(new Random().nextInt((1000 - 400) + 1) + 400);

        return pillar;
    }

    private Entity createCactus(GameData gamedata) {
        String file = "Objects.png";
        // radius should be texture width / 16
        Entity cactus = new Obstacle(file, 4);
        cactus.add(new LifePart(3));
        // todo : implement attack feature
        FileLoader.loadFile(file, getClass());

        cactus.setX(new Random().nextInt((1000 - 200) + 1) + 200);
        cactus.setY(new Random().nextInt((1000 - 400) + 1) + 400);

        return cactus;
    }

    private Entity createBarrel(GameData gamedata) {
        String file = "Barrel.png";
        // radius should be texture width / 16
        Entity barrel = new Obstacle(file, 20);
        barrel.add(new LifePart(3));
        // todo : implement animation when attacked
        FileLoader.loadFile(file, getClass());

        barrel.setX(new Random().nextInt((1000 - 200) + 1) + 200);
        barrel.setY(new Random().nextInt((1000 - 400) + 1) + 400);

        return barrel;
    }

    private Entity createChest(GameData gamedata) {
        String file = "Chest.png";
        // radius should be texture width / 16
        Entity chest = new Obstacle(file, 16);
        chest.add(new LifePart(3));
        FileLoader.loadFile(file, getClass());

        chest.setX(new Random().nextInt((1000 - 200) + 1) + 200);
        chest.setY(new Random().nextInt((1000 - 400) + 1) + 400);
        System.out.println(chest.getX() + ", " + chest.getY());

        return chest;
    }

    private Entity createCrate(GameData gamedata) {
        String file = "Crate.png";
        // radius should be texture width / 16
        Entity crate = new Obstacle(file, 20);
        crate.add(new LifePart(3));
        FileLoader.loadFile(file, getClass());

        crate.setX(new Random().nextInt((1000 - 200) + 1) + 200);
        crate.setY(new Random().nextInt((1000 - 400) + 1) + 400);

        return crate;
    }

    private Entity createJar(GameData gamedata) {
        String file = "Jar.png";
        // radius should be texture width / 16
        Entity jar = new Obstacle(file, 14);
        jar.add(new LifePart(3));
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
