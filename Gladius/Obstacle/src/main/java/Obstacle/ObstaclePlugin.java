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
import java.util.List;
import java.util.Random;

public class ObstaclePlugin implements IGamePluginService {

    private ArrayList<Entity> obstacles;
    private List<List<Integer>> csv;
    Random rand;

    @Override
    public void start(GameData gameData, World world) {
        while (!world.isMapLoaded()) {
            // Wait until map is loaded
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        csv = world.getCsvMap();

        rand = new Random();

        obstacles = new ArrayList<>();

        // Random spawn
        // spawn barrel
        for(int i = 0; i < rand.nextInt(5); i++) {
            obstacles.add(createBarrel(gameData, world));
        }
        // spawn explosion barrel
        for(int i = 0; i < rand.nextInt(5); i++) {
            obstacles.add(createExplosiveBarrel(gameData, world));
        }
        // spawn chest
        for(int i = 0; i < rand.nextInt(5); i++) {
            obstacles.add(createChest(gameData, world));
        }
        // spawn crate
        for(int i = 0; i < rand.nextInt(5); i++) {
            obstacles.add(createCrate(gameData, world));
        }
        // spawn jar
        for(int i = 0; i < rand.nextInt(5); i++) {
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
        // BARREL HAS A SPIKE ATTACK FOR TEST PURPOSES
        barrel.add(new StatsPart(0,10,0,0, 0));
        FileLoader.loadFile(file, getClass());

        int randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 0 to width of the map in tiles
        int randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 0 to height of the map in tiles
        while(csv.get(randY).get(randX) != 0) {
            randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 0 to width of the map in tiles
            randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 0 to height of the map in tiles
        }
        int spawnX = (randX * 32) - barrel.getTextureWidth()/2 + 32/2;
        int spawnY = gamedata.getMapHeight() - (randY * 32) - barrel.getTextureHeight()/2 - barrel.getRadiusOffsetY() - 32/2;
        barrel.setX(spawnX);
        barrel.setY(spawnY);
        barrel.add(new AnimationPart());
        world.setTileInMap(randY, randX, 3);

        return barrel;
    }

    private Entity createExplosiveBarrel(GameData gamedata, World world) {
        String file = "ExplosiveBarrel.png";
        Entity explosiveBarrel = new Obstacle(file, 10, 0, -5, 64, 64, 0, 0.3f, 0.3f, 32, 32);
        explosiveBarrel.add(new LifePart(50));
        explosiveBarrel.add(new StatsPart(0,0,50,100, 0));
        FileLoader.loadFile(file, getClass());

        int randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 0 to width of the map in tiles
        int randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 0 to height of the map in tiles
        while(csv.get(randY).get(randX) != 0) {
            randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 0 to width of the map in tiles
            randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 0 to height of the map in tiles
        }
        int spawnX = (randX * 32) - explosiveBarrel.getTextureWidth()/2 + 32/2;
        int spawnY = gamedata.getMapHeight() - (randY * 32) - explosiveBarrel.getTextureHeight()/2 - explosiveBarrel.getRadiusOffsetY() - 32/2;
        explosiveBarrel.setX(spawnX);
        explosiveBarrel.setY(spawnY);
        world.setTileInMap(randY, randX, 3);

        explosiveBarrel.add(new AnimationPart());

        return explosiveBarrel;
    }

    private Entity createChest(GameData gamedata, World world) {
        String file = "Chest.png";
        Entity chest = new Obstacle(file, 10, 0 ,-2, 64, 64, 0, 0.3f, 0.2f, 32, 32);
        chest.add(new LifePart(100));
        chest.add(new StatsPart(0,0,0, 0,0));
        FileLoader.loadFile(file, getClass());

        int randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 0 to width of the map in tiles
        int randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 0 to height of the map in tiles
        while(csv.get(randY).get(randX) != 0) {
            randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 0 to width of the map in tiles
            randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 0 to height of the map in tiles
        }
        int spawnX = (randX * 32) - chest.getTextureWidth()/2 + 32/2;
        int spawnY = gamedata.getMapHeight() - (randY * 32) - chest.getTextureHeight()/2 - chest.getRadiusOffsetY() - 32/2;
        chest.setX(spawnX);
        chest.setY(spawnY);
        world.setTileInMap(randY, randX, 3);

        chest.add(new AnimationPart());

        return chest;
    }

    private Entity createCrate(GameData gamedata, World world) {
        String file = "Crate.png";
        Entity crate = new Obstacle(file, 14, 0 ,-4, 64, 64, 0, 0.3f, 0.3f, 32, 32);
        crate.add(new LifePart(100));
        crate.add(new StatsPart(0,0,0, 0,0));
        FileLoader.loadFile(file, getClass());

        int randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 0 to width of the map in tiles
        int randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 0 to height of the map in tiles
        while(csv.get(randY).get(randX) != 0) {
            randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 0 to width of the map in tiles
            randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 0 to height of the map in tiles
        }
        int spawnX = (randX * 32) - crate.getTextureWidth()/2 + 32/2;
        int spawnY = gamedata.getMapHeight() - (randY * 32) - crate.getTextureHeight()/2 - crate.getRadiusOffsetY() - 32/2;
        crate.setX(spawnX);
        crate.setY(spawnY);
        world.setTileInMap(randY, randX, 3);

        crate.add(new AnimationPart());

        return crate;
    }

    private Entity createJar(GameData gamedata, World world) {
        String file = "Jar.png";
        Entity jar = new Obstacle(file, 10, 0, -2, 64, 64, 0, 0.2f, 0.2f, 32, 32);
        jar.add(new LifePart(100));
        jar.add(new StatsPart(0,0,0, 0,0));
        FileLoader.loadFile(file, getClass());

        int randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 0 to width of the map in tiles
        int randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 0 to height of the map in tiles
        while(csv.get(randY).get(randX) != 0) {
            randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 0 to width of the map in tiles
            randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 0 to height of the map in tiles
        }
        int spawnX = (randX * 32) - jar.getTextureWidth()/2 + 32/2;
        int spawnY = gamedata.getMapHeight() - (randY * 32) - jar.getTextureHeight()/2 - jar.getRadiusOffsetY() - 32/2;
        jar.setX(spawnX);
        jar.setY(spawnY);
        world.setTileInMap(randY, randX, 3);

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
