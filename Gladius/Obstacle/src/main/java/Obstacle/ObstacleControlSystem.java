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

public class ObstacleControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Obstacle.class)) {
            LifePart lifePart = entity.getPart(LifePart.class);
            AnimationPart animationPart = entity.getPart(AnimationPart.class);
            Obstacle entityObstacle = (Obstacle) entity;
            if(entity.getPart(AnimationPart.class) != null && world.isMapLoaded()) { // && entityObstacle.isMapLoaded()
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
            }

            lifePart.process(gameData, entity);
        }
    }
}
