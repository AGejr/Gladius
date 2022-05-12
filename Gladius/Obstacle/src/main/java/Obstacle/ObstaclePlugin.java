package Obstacle;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.StatsPart;
import Common.services.IEntityFactoryService;
import Common.tools.FileLoader;
import CommonObstacles.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObstaclePlugin implements IEntityFactoryService {

    private ArrayList<Entity> obstacles = new ArrayList<>();
    private List<List<Integer>> csv;
    private Random rand = new Random();

    @Override
    public void spawn(GameData gameData, World world, Integer amount) {
        obstacles = new ArrayList<>();
        rand = new Random();

        // First time move obstacles into grid
        if(csv == null) {
            csv = world.getCsvMap();
        }

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
        Entity barrel = new Obstacle(file, 10, 64, 64, 0, 0.3f, 0.3f, 32, 32, 1, 0, -5);
        barrel.add(new LifePart(100));
        barrel.add(new StatsPart(0,0,0,0,0,0));
        FileLoader.loadFile(file, getClass());

        int randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
        int randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
        while (csv.get(randY).get(randX) != 0) {
            randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
            randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
        }
        int spawnX = (randX * 32) - barrel.getTextureWidth() / 2 + 32 / 2;
        int spawnY = gamedata.getMapHeight() - (randY * 32) - barrel.getTextureHeight() / 2 - barrel.getRadiusOffsetY() - 32 / 2;
        barrel.setX(spawnX);
        barrel.setY(spawnY);
        world.setTileInMap(randY, randX, 3);
        csv = world.getCsvMap();

        barrel.add(new AnimationPart());

        return barrel;
    }

    private Entity createExplosiveBarrel(GameData gamedata, World world) {
        String file = "ExplosiveBarrel.png";
        Entity explosiveBarrel = new Obstacle(file, 10, 64,64,0,0.3f,0.3f,32, 32,1,0,-5);
        explosiveBarrel.add(new LifePart(50));
        explosiveBarrel.add(new StatsPart(0,0,50,50,0,0));
        FileLoader.loadFile(file, getClass());

        int randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
        int randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
        while (csv.get(randY).get(randX) != 0) {
            randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
            randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
        }
        int spawnX = (randX * 32) - explosiveBarrel.getTextureWidth() / 2 + 32 / 2;
        int spawnY = gamedata.getMapHeight() - (randY * 32) - explosiveBarrel.getTextureHeight() / 2 - explosiveBarrel.getRadiusOffsetY() - 32 / 2;
        explosiveBarrel.setX(spawnX);
        explosiveBarrel.setY(spawnY);
        world.setTileInMap(randY, randX, 3);
        csv = world.getCsvMap();

        explosiveBarrel.add(new AnimationPart());

        return explosiveBarrel;
    }

    private Entity createChest(GameData gamedata, World world) {
        String file = "Chest.png";
        Entity chest = new Obstacle(file, 10, 64,64,0,0.3f,0.3f,32, 32,1,0,-2);
        chest.add(new LifePart(100));
        chest.add(new StatsPart(0,0,0,0,0,0));
        FileLoader.loadFile(file, getClass());

        int randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
        int randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
        while (csv.get(randY).get(randX) != 0) {
            randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
            randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
        }
        int spawnX = (randX * 32) - chest.getTextureWidth() / 2 + 32 / 2;
        int spawnY = gamedata.getMapHeight() - (randY * 32) - chest.getTextureHeight() / 2 - chest.getRadiusOffsetY() - 32 / 2;
        chest.setX(spawnX);
        chest.setY(spawnY);
        world.setTileInMap(randY, randX, 3);
        csv = world.getCsvMap();

        chest.add(new AnimationPart());

        return chest;
    }

    private Entity createCrate(GameData gamedata, World world) {
        String file = "Crate.png";
        Entity crate = new Obstacle(file, 10, 64,64,0,0.3f,0.3f,32, 32,1,0,-4);
        crate.add(new LifePart(100));
        crate.add(new StatsPart(0,0,0,0,0,0));
        FileLoader.loadFile(file, getClass());

        int randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
        int randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
        while (csv.get(randY).get(randX) != 0) {
            randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
            randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
        }
        int spawnX = (randX * 32) - crate.getTextureWidth() / 2 + 32 / 2;
        int spawnY = gamedata.getMapHeight() - (randY * 32) - crate.getTextureHeight() / 2 - crate.getRadiusOffsetY() - 32 / 2;
        crate.setX(spawnX);
        crate.setY(spawnY);
        world.setTileInMap(randY, randX, 3);
        csv = world.getCsvMap();

        crate.add(new AnimationPart());

        return crate;
    }

    private Entity createJar(GameData gamedata, World world) {
        String file = "Jar.png";
        Entity jar = new Obstacle(file, 10, 64,64,0,0.3f,0.3f,32, 32,1,0,-2);
        jar.add(new LifePart(100));
        jar.add(new StatsPart(0,0,0,0,0,0));
        FileLoader.loadFile(file, getClass());

        int randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
        int randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
        while (csv.get(randY).get(randX) != 0) {
            randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
            randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
        }
        int spawnX = (randX * 32) - jar.getTextureWidth() / 2 + 32 / 2;
        int spawnY = gamedata.getMapHeight() - (randY * 32) - jar.getTextureHeight() / 2 - jar.getRadiusOffsetY() - 32 / 2;
        jar.setX(spawnX);
        jar.setY(spawnY);
        world.setTileInMap(randY, randX, 3);
        csv = world.getCsvMap();

        jar.add(new AnimationPart());

        return jar;
    }

    @Override
    public void stop(World world) {
        for(Entity entity : obstacles) {
            world.removeEntity(entity);
        }
    }
}
