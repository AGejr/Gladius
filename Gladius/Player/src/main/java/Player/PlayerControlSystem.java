package Player;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.GameKeys;
import Common.data.World;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.MovingPart;
import Common.services.IEntityProcessingService;
import CommonPlayer.Player;
import Common.data.entityparts.AnimationPart;

public class PlayerControlSystem implements IEntityProcessingService {

    //TODO implement attack feature

    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities(Player.class)){

            MovingPart movingPart = entity.getPart(MovingPart.class);
            LifePart lifePart = entity.getPart(LifePart.class);
            AnimationPart animationPart = entity.getPart(AnimationPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));

            movingPart.process(gameData, entity);
            animationPart.process(gameData,entity);
            lifePart.process(gameData,entity);
        }
    }
}
