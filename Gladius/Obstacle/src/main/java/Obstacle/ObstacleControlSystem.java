package Obstacle;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.LifePart;
import Common.services.IEntityProcessingService;

public class ObstacleControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Obstacle.class)) {

            LifePart lifePart = entity.getPart(LifePart.class);
            AnimationPart animationPart = entity.getPart(AnimationPart.class);
            if(entity.getPart(AnimationPart.class) != null) {
                animationPart.process(gameData, entity);
            }

            lifePart.process(gameData, entity);
        }
    }
}
