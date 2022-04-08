package Enemy;

import Common.ai.AStarPathFinding;
import Common.ai.Node;
import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.MovingPart;
import Common.services.IEntityProcessingService;
import CommonPlayer.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnemyControlSystem implements IEntityProcessingService {

    private AStarPathFinding aStarPathFinding = new AStarPathFinding();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            int enemyY = (int) ((enemy.getY() / gameData.getMapHeight()) * 40);
            int enemyX = (int) (((enemy.getX() + 32 / 2) / gameData.getMapWidth()) * 50);
            AnimationPart animationPart = enemy.getPart(AnimationPart.class);
            for (Entity player : world.getEntities(Player.class)) {
                int playerY = (int) ((player.getY() / gameData.getMapHeight()) * 40);
                int playerX = (int) (((player.getX() + 32 / 2) / gameData.getMapWidth()) * 50);
                if (player.getY() > 300) {
                    List<Node> path = aStarPathFinding.treeSearch(new ArrayList<>(Arrays.asList(enemyX, 40 - enemyY)), new ArrayList<>(Arrays.asList(playerX, 40 - playerY)), world);
                    float newY = gameData.getMapHeight() - path.get(0).getY() * (gameData.getMapHeight() / 40f);
                    float newX = path.get(0).getX() * (gameData.getMapWidth() / 50f) + (player.getRadius() * 16) / 2;

                    if (newX < enemy.getX() + (enemy.getRadius() * 16) / 2) {
                        movingPart.setLeft(true);
                        movingPart.setRight(false);
                    } else {
                        movingPart.setRight(true);
                        movingPart.setLeft(false);
                    }

                    if (newY < enemy.getY()) {
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
            animationPart.process(gameData, enemy);

        }


    }
}
