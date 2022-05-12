package Collision;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.services.IPostEntityProcessingService;
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
            if (entity.getPart(MovingPart.class) != null) {
                float radius = entity.getRadius();
                int y = (int) (40 - ((entity.getY() / gameData.getMapHeight()) * 40));
                // todo : radius*16/2 should be changed to texture width / 2
                int x = (int) (((entity.getX() + (radius * 16) / 2) / gameData.getMapWidth()) * 50); // divide by 2 to get center

                MovingPart movingPart = entity.getPart(MovingPart.class);
                // Checks around the entity if it is a wall (1) && checks if the entity's collision box top is colliding with the next tile's box
                if ((csv.get(y - 1).get(x) == WALL) && (gameData.getMapHeight() - ((y - 1) * 32) - 32) - entity.getY() <= radius) {
                    movingPart.setColTop(true);
                } else {
                    movingPart.setColTop(false);
                }
                if ((csv.get(y + 1).get(x) == WALL) && entity.getY() - (gameData.getMapHeight() - (y + 1) * 32) <= radius) {
                    movingPart.setColBot(true);
                } else {
                    movingPart.setColBot(false);
                }
                // todo : radius*16/2 should be changed to texture width / 2
                if ((csv.get(y).get(x - 1) == WALL) && (entity.getX() + (radius * 16) / 2) - (((x - 1) * 32) + 32) < radius) {
                    movingPart.setColLeft(true);
                } else {
                    movingPart.setColLeft(false);
                }
                // todo : radius*16/2 should be changed to texture width / 2
                if ((csv.get(y).get(x + 1) == WALL) && ((x + 1) * 32) - (entity.getX() + (radius * 16) / 2) < radius) {
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
                    if (entity.getY() > 220 && entity.getY() < 240 && entity.getX() > 770 && entity.getX() < 810) {
                        EventRegistry.addEvent(GAME_EVENT.ARENA_ENTERED);
                        entity.setY(346);
                    } else if (entity.getY() > 350 && entity.getY() < 360 && entity.getX() > 770 && entity.getX() < 810) {
                        EventRegistry.addEvent(GAME_EVENT.ARENA_EXITED);
                        entity.setY(200);
                    }
                }
            }
        }
    }
}
