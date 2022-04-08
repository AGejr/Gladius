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
    private boolean start = false;
    private AStarPathFinding aStarPathFinding = new AStarPathFinding();
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)){
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            int enemyY = (int) (40 - ((enemy.getY() / 1280) * 40));
            int enemyX = (int) (((enemy.getX()+32/2) / 1600) * 50);
            AnimationPart animationPart = enemy.getPart(AnimationPart.class);
            for (Entity player : world.getEntities(Player.class)) {
                int playerY = (int) (40 - ((player.getY() / 1280) * 40));
                int playerX = (int) (((player.getX()+32/2) / 1600) * 50);
                if (player.getY() > 400) {
                    if (!start) {
                        start=true;
                        List<Node> path = aStarPathFinding.treeSearch(new ArrayList<>(Arrays.asList(enemyX, enemyY)), new ArrayList<>(Arrays.asList(playerX, playerY)), world);
                        float newX = path.get(path.size() - 2).getX();
                        float newY = path.get(path.size() - 2).getY();
                        System.out.println("X: " + newX + "Y: " + newY);
                    }
                    /*
                    if (player.getX() + (player.getRadius()*16)/2 == enemy.getX() + (enemy.getRadius()*16)/2) {
                        movingPart.setLeft(false);
                        movingPart.setRight(false);
                    } else if (newX < enemy.getX() + (enemy.getRadius()*16)/2) {
                        movingPart.setLeft(true);
                        movingPart.setRight(false);
                    } else {
                        movingPart.setRight(true);
                        movingPart.setLeft(false);
                    }
                    if (newY == enemy.getY()) {
                        movingPart.setUp(false);
                        movingPart.setDown(false);
                    } else if (newY < enemy.getY()) {
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

                     */
                }

            }


            movingPart.process(gameData, enemy);
            animationPart.process(gameData, enemy);

        }
    }
}
