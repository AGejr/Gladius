package Enemy;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.MovingPart;
import Common.services.IEntityProcessingService;
import CommonPlayer.Player;

public class EnemyControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)){
            MovingPart movingPart = enemy.getPart(MovingPart.class);

            for (Entity player : world.getEntities(Player.class)) {
                if (player.getY() > 300) {
                    if (player.getX() == enemy.getX()+40) {
                        movingPart.setLeft(false);
                        movingPart.setRight(false);
                    } else if (player.getX() < enemy.getX()+40) {
                        movingPart.setLeft(true);
                        movingPart.setRight(false);
                    } else {
                        movingPart.setRight(true);
                        movingPart.setLeft(false);
                    }
                    if (player.getY() == enemy.getY()+40) {
                        movingPart.setUp(false);
                        movingPart.setDown(false);
                    } else if (player.getY() < enemy.getY()+40) {
                        movingPart.setUp(false);
                        movingPart.setDown(true);
                    } else {
                        movingPart.setDown(false);
                        movingPart.setUp(true);
                    }
                } else {
                    movingPart.setDown(false);
                    movingPart.setUp(false);
                    movingPart.setLeft(false);
                    movingPart.setRight(false);
                }
            }

            movingPart.process(gameData, enemy);

        }
    }
}
