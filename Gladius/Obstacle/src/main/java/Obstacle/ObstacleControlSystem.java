package Obstacle;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.StatsPart;
import Common.services.IEntityProcessingService;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

import java.util.List;
import java.util.Random;

public class ObstacleControlSystem implements IEntityProcessingService {
    private List<List<Integer>> csv;
    private boolean obstaclesMoved = false;
    private Random rand = new Random();

    @Override
    public void process(GameData gameData, World world) {
        // First time move obstacles into grid
        if(csv == null) {
            csv = world.getCsvMap();
        } else if(!obstaclesMoved) {
            for (Entity entity : world.getEntities(Obstacle.class)) {
                int randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
                int randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
                while (csv.get(randY).get(randX) != 0) {
                    randX = rand.nextInt(48 - 2) + 2; // random x spawn point from 2 to width of the map - 2 in tiles
                    randY = rand.nextInt(40 - 10) + 3; // random y spawn point from 3 to height of the map - 7 in tiles
                }
                int spawnX = (randX * 32) - entity.getTextureWidth() / 2 + 32 / 2;
                int spawnY = gameData.getMapHeight() - (randY * 32) - entity.getTextureHeight() / 2 - entity.getRadiusOffsetY() - 32 / 2;
                entity.setX(spawnX);
                entity.setY(spawnY);
                world.setTileInMap(randY, randX, 3);
                // Update map for each spawned obstacle
                csv = world.getCsvMap();
            }
            obstaclesMoved = true;
        }


        for (Entity entity : world.getEntities(Obstacle.class)) {
            LifePart lifePart = entity.getPart(LifePart.class);
            AnimationPart animationPart = entity.getPart(AnimationPart.class);
            if(entity.getPart(AnimationPart.class) != null) {
                animationPart.process(gameData, entity);
            }

            if(entity.getPart(StatsPart.class) != null && entity.getPart(LifePart.class) != null) {
                StatsPart explosiveBarrelStatsPart = entity.getPart(StatsPart.class);
                LifePart explosiveBarrelLifePart = entity.getPart(LifePart.class);
                if(explosiveBarrelStatsPart.getExplosiveAttack() != 0) {
                    Polygon polygonBoundaries = new Polygon(new float[]{entity.getX() + ((float) entity.getTextureWidth()/2) - (float) explosiveBarrelStatsPart.getExplosionRadius()/2, entity.getY() + ((float) entity.getTextureHeight()/2) - (float) explosiveBarrelStatsPart.getExplosionRadius()/2, entity.getX() + ((float) entity.getTextureWidth()/2) - (float) explosiveBarrelStatsPart.getExplosionRadius()/2, entity.getY() + ((float) entity.getTextureHeight()/2) - (float) explosiveBarrelStatsPart.getExplosionRadius()/2 + explosiveBarrelStatsPart.getExplosionRadius(), entity.getX() + ((float) entity.getTextureWidth()/2) - (float) explosiveBarrelStatsPart.getExplosionRadius()/2 + explosiveBarrelStatsPart.getExplosionRadius(), entity.getY() + ((float) entity.getTextureHeight()/2) - (float) explosiveBarrelStatsPart.getExplosionRadius()/2 + explosiveBarrelStatsPart.getExplosionRadius(), entity.getX() + ((float) entity.getTextureWidth()/2) - (float) explosiveBarrelStatsPart.getExplosionRadius()/2 + explosiveBarrelStatsPart.getExplosionRadius(), entity.getY() + ((float) entity.getTextureHeight()/2) - (float) explosiveBarrelStatsPart.getExplosionRadius()/2});
                    if(explosiveBarrelLifePart.isDead() && animationPart.isDoneAnimating()) {
                        // Wait to add damage until the explosion animation is done
                        for (Entity hitEntity: world.getEntities()) {
                            if(entity.getID().equals(hitEntity.getID())) {
                                // If is same object, skip
                            } else {
                                if(hitEntity.getPart(LifePart.class) != null) {
                                    if(Intersector.overlapConvexPolygons(polygonBoundaries, hitEntity.getPolygonBoundaries())) {
                                        LifePart hitEntityLifePart = hitEntity.getPart(LifePart.class);
                                        hitEntityLifePart.subtractLife(explosiveBarrelStatsPart.getExplosiveAttack());
                                        AnimationPart hitEntityAnimationPart = hitEntity.getPart(AnimationPart.class);
                                        if (hitEntityAnimationPart != null) {
                                            hitEntityAnimationPart.setTakeDamage();
                                        }
                                    }
                                }
                            }
                        }
                        // remove the barrel's explosion attack so it wont attack again
                        explosiveBarrelStatsPart.setExplosiveAttack(0);
                    }
                }
                if(lifePart.isDead()) {
                    // Remove obstacle from csv map so the enemies don't avoid it
                    int y = (int) (40 - (((entity.getY() + entity.getTextureHeight() / 2 + entity.getRadiusOffsetY() + 32 / 2) / gameData.getMapHeight()) * 40));
                    int x = (int) (((entity.getX()+(entity.getTextureWidth())/2) / gameData.getMapWidth()) * 50); // divide by 2 to get center
                    world.setTileInMap(y, x, 0);
                }
            }

            lifePart.process(gameData, entity);
        }
    }
}
