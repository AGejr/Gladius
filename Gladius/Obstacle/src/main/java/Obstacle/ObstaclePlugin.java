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
    Random rand;

    @Override
    public void start(GameData gameData, World world) {
        obstacles = new ArrayList<>();
        rand = new Random();

        // Random spawn
        // spawn barrel
        int randomNumber = rand.nextInt(5) + 1;
        for(int i = 0; i < randomNumber; i++) {
            obstacles.add(createBarrel(gameData, world));
        }
        // spawn explosion barrel
        randomNumber = rand.nextInt(5) + 1;
        for(int i = 0; i < randomNumber; i++) {
            obstacles.add(createExplosiveBarrel(gameData, world));
        }
        // spawn chest
        randomNumber = rand.nextInt(5) + 1;
        for(int i = 0; i < randomNumber; i++) {
            obstacles.add(createChest(gameData, world));
        }
        // spawn crate
        randomNumber = rand.nextInt(5) + 1;
        for(int i = 0; i < randomNumber; i++) {
            obstacles.add(createCrate(gameData, world));
        }
        // spawn jar
        randomNumber = rand.nextInt(5) + 1;
        for(int i = 0; i < randomNumber; i++) {
            obstacles.add(createJar(gameData, world));
        }

        for(Entity entity : obstacles) {
            world.addEntity(entity);
        }
    }

    private Entity createBarrel(GameData gamedata, World world) {
        String file = "Barrel.png";
        Entity barrel = new Obstacle(file, 10, 0, -5, 64, 64, 0, 0.3f, 0.3f, 32, 32);
        barrel.add(new LifePart(100));
        barrel.add(new StatsPart(0,0,0,0, 0));
        FileLoader.loadFile(file, getClass());

        barrel.setX(0);
        barrel.setY(0);
        barrel.add(new AnimationPart());
        //world.setTileInMap(randY, randX, 3);

        return barrel;
    }

    private Entity createExplosiveBarrel(GameData gamedata, World world) {
        String file = "ExplosiveBarrel.png";
        Entity explosiveBarrel = new Obstacle(file, 10, 0, -5, 64, 64, 0, 0.3f, 0.3f, 32, 32);
        explosiveBarrel.add(new LifePart(50));
        explosiveBarrel.add(new StatsPart(0,0,50,50, 0));
        FileLoader.loadFile(file, getClass());

        explosiveBarrel.setX(0);
        explosiveBarrel.setY(0);
        //world.setTileInMap(randY, randX, 3);

        explosiveBarrel.add(new AnimationPart());

        return explosiveBarrel;
    }

    private Entity createChest(GameData gamedata, World world) {
        String file = "Chest.png";
        Entity chest = new Obstacle(file, 10, 0 ,-2, 64, 64, 0, 0.3f, 0.2f, 32, 32);
        chest.add(new LifePart(100));
        chest.add(new StatsPart(0,0,0, 0,0));
        FileLoader.loadFile(file, getClass());

        chest.setX(0);
        chest.setY(0);
        //world.setTileInMap(randY, randX, 3);

        chest.add(new AnimationPart());

        return chest;
    }

    private Entity createCrate(GameData gamedata, World world) {
        String file = "Crate.png";
        Entity crate = new Obstacle(file, 14, 0 ,-4, 64, 64, 0, 0.3f, 0.3f, 32, 32);
        crate.add(new LifePart(100));
        crate.add(new StatsPart(0,0,0, 0,0));
        FileLoader.loadFile(file, getClass());

        crate.setX(0);
        crate.setY(0);
        //world.setTileInMap(randY, randX, 3);

        crate.add(new AnimationPart());

        return crate;
    }

    private Entity createJar(GameData gamedata, World world) {
        String file = "Jar.png";
        Entity jar = new Obstacle(file, 10, 0, -2, 64, 64, 0, 0.2f, 0.2f, 32, 32);
        jar.add(new LifePart(100));
        jar.add(new StatsPart(0,0,0, 0,0));
        FileLoader.loadFile(file, getClass());

        jar.setX(0);
        jar.setY(0);
        //world.setTileInMap(randY, randX, 3);

        jar.add(new AnimationPart());

        return jar;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for(Entity entity : obstacles) {
            world.removeEntity(entity);
        }
    }
}
