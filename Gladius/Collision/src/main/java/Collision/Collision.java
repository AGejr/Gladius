package Collision;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.MovingPart;
import Common.data.entityparts.StatsPart;
import Common.services.IPostEntityProcessingService;
import java.util.*;
import CommonPlayer.Player;
import Event.EventRegistry;
import Event.GAME_EVENT;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collision implements IPostEntityProcessingService {
    private List<List<Integer>> csv;
    private final int WALL = 1;
    private final int WATER = 2;

    @Override
    public void process(GameData gameData, World world) {
        if (csv == null) {
            csv = world.getCsvMap();
        }
        for (Entity entity : world.getEntities()) {
            // Collision with tiles
            if(entity.getPart(MovingPart.class) != null) {
                float entityLeft = (entity.getX() + entity.getRadiusOffsetX() + ((float) entity.getTextureWidth()/2) - entity.getRadius()/2);
                float entityRight = (entity.getX() + entity.getRadiusOffsetX() + ((float) entity.getTextureWidth()/2) + entity.getRadius()/2);
                float entityTop = (entity.getY() + entity.getRadiusOffsetY() + entity.getRadius());
                float entityBottom = entity.getY() + entity.getRadiusOffsetY();
                float radius = entity.getRadius();
                int y = (int) (40 - ((entity.getY() / gameData.getMapHeight()) * 40));
                int x = (int) (((entity.getX()+(entity.getTextureWidth())/2) / gameData.getMapWidth()) * 50); // divide by 2 to get center

                MovingPart movingPart = entity.getPart(MovingPart.class);
                // Checks wall layer, if there is a wall (not 0)
                //      Checks should the tile be ignored
                //      Checks if the distance between entity and tile < entity radius
                // 2 is the smallest distance between entity and wall before it checks the wall behind (which it shouldn't)
                if((csv.get(y - 1).get(x) == WALL) && (gameData.getMapHeight() - ((y - 1) * 32) - 32) - entityTop <= 2) {
                    movingPart.setColTop(true);
                } else {
                    movingPart.setColTop(false);
                }
                if((csv.get(y + 1).get(x) == WALL) && entityBottom - (gameData.getMapHeight() - (y + 1) * 32) <= 2) {
                    movingPart.setColBot(true);
                } else {
                    movingPart.setColBot(false);
                }
                if((csv.get(y).get(x - 1) == WALL) && entityLeft - (((x - 1) * 32) + 32) <= 2) {
                    movingPart.setColLeft(true);
                } else {
                    movingPart.setColLeft(false);
                }
                if((csv.get(y).get(x + 1) == WALL) && ((x + 1) * 32) - entityRight <= 2) {
                    movingPart.setColRight(true);
                } else {
                    movingPart.setColRight(false);
                }

                if (csv.get(y).get(x) == WATER) {
                    movingPart.setSlow(0.7f);
                    movingPart.setIsSlow(true);
                } else {
                    movingPart.setSlow(1f);
                    movingPart.setIsSlow(false);
                }

                if (entity instanceof Player && gameData.isGateEnabled()) {
                    if (entity.getY() > 210 && entity.getY() < 240 && entity.getX() > 770 && entity.getX() < 810) {
                        EventRegistry.addEvent(GAME_EVENT.ARENA_ENTERED);
                        entity.setY(346);
                    } else if (entity.getY() > 350 && entity.getY() < 360 && entity.getX() > 770 && entity.getX() < 810) {
                        EventRegistry.addEvent(GAME_EVENT.ARENA_EXITED);
                        entity.setY(200);
                    }
                }

                // Collision with entities
                for(Entity collidingEntity : world.getEntities()) {
                    // ^ TO AVOID COLLISION BETWEEN TWO MOVING ENTITIES
                    // SHOULD MAYBE BE REMOVED!
                    LifePart collidingEntityLifePart = collidingEntity.getPart(LifePart.class);
                    if(collidingEntity.getPart(MovingPart.class) == null && collidingEntityLifePart != null && collidingEntityLifePart.getLife() > 0) {
                        float collidingEntityLeft = (collidingEntity.getX() + collidingEntity.getRadiusOffsetX() + ((float) collidingEntity.getTextureWidth()/2) - collidingEntity.getRadius()/2);
                        float collidingEntityRight = (collidingEntity.getX() + collidingEntity.getRadiusOffsetX() + ((float) collidingEntity.getTextureWidth()/2) + collidingEntity.getRadius()/2);
                        float collidingEntityTop = (collidingEntity.getY() + collidingEntity.getRadiusOffsetY() + ((float) collidingEntity.getTextureHeight()/2) + collidingEntity.getRadius()/2);
                        float collidingEntityBottom = (collidingEntity.getY() + collidingEntity.getRadiusOffsetY() + ((float) collidingEntity.getTextureHeight()/2) - collidingEntity.getRadius()/2);
                        // check left collision
                        if(!entity.getClass().equals(collidingEntity.getClass()) &&
                                entityLeft - collidingEntityRight <= 2 &&
                                entityLeft >= collidingEntityLeft &&
                                entityBottom <= collidingEntityTop &&
                                entityTop >= collidingEntityBottom) {
                            movingPart.setColLeft(true);
                        }
                        // check right collision
                        if(!entity.getClass().equals(collidingEntity.getClass()) &&
                                collidingEntityLeft - entityRight <= 2 &&
                                entityRight <= collidingEntityRight &&
                                entityBottom <= collidingEntityTop &&
                                entityTop >= collidingEntityBottom){
                            movingPart.setColRight(true);
                        }
                        // check top collision
                        if(!entity.getClass().equals(collidingEntity.getClass()) &&
                                entityRight >= collidingEntityLeft &&
                                entityLeft <= collidingEntityRight &&
                                entityBottom - collidingEntityTop <= 2 &&
                                entityTop >= collidingEntityBottom){
                            movingPart.setColBot(true);
                        }
                        // check bottom collision
                        if(!entity.getClass().equals(collidingEntity.getClass()) &&
                                entityRight >= collidingEntityLeft &&
                                entityLeft <= collidingEntityRight &&
                                collidingEntityBottom - entityTop <= 2 &&
                                entityBottom <= collidingEntityTop){
                            movingPart.setColTop(true);
                        }
                    }
                }
            }
        }
    }
}
