package Collision;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.services.IPostEntityProcessingService;

import java.util.List;

public class Collision implements IPostEntityProcessingService {
    private List<List<Integer>> csv;

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
                // Check wall layer, if there is a wall (not 0)
                //      Should the tile be ignored?
                //      Is the distance between entity and tile < entity radius?
                if ((csv.get(y - 1).get(x) != 0 && csv.get(y - 1).get(x) != 2) && (gameData.getMapHeight() - ((y - 1) * 32) - 32) - entity.getY() <= radius) {
                    movingPart.setColTop(true);
                } else {
                    movingPart.setColTop(false);
                }
                if ((csv.get(y + 1).get(x) != 0 && csv.get(y + 1).get(x) != 2) && entity.getY() - (gameData.getMapHeight() - (y + 1) * 32) <= radius) {
                    movingPart.setColBot(true);
                } else {
                    movingPart.setColBot(false);
                }
                // todo : radius*16/2 should be changed to texture width / 2
                if ((csv.get(y).get(x - 1) != 0  && csv.get(y).get(x - 1) != 2) && (entity.getX() + (radius * 16) / 2) - (((x - 1) * 32) + 32) < radius) {
                    movingPart.setColLeft(true);
                } else {
                    movingPart.setColLeft(false);
                }
                // todo : radius*16/2 should be changed to texture width / 2
                if ((csv.get(y).get(x + 1) != 0 && csv.get(y).get(x + 1) != 2) && ((x + 1) * 32) - (entity.getX() + (radius * 16) / 2) < radius) {
                    movingPart.setColRight(true);
                } else {
                    movingPart.setColRight(false);
                }

                if (csv.get(y).get(x) == 2) {
                    movingPart.setSlow(0.5f);
                } else {
                    movingPart.setSlow(1f);
                }

                if (entity.getY() > 220 && entity.getY() < 240 && entity.getX() > 770 && entity.getX() < 810) {
                    entity.setY(420);
                } else if (entity.getY() > 350 && entity.getY() < 360 && entity.getX() > 770 && entity.getX() < 810) {
                    entity.setY(150);
                }
            }
        }
    }
}
