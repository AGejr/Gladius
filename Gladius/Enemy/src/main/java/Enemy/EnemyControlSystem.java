package Enemy;

import Common.ai.AStarPathFinding;
import Common.ai.Node;
import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.LifePart;
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
            LifePart lifePart = enemy.getPart(LifePart.class);
            for (Entity player : world.getEntities(Player.class)) {
                // getting player position
                int playerY = (int) ((player.getY() / gameData.getMapHeight()) * 40);
                int playerX = (int) (((player.getX() + 32 / 2) / gameData.getMapWidth()) * 50);
                if (player.getY() > 300) {
                    // Initialization of a new search for the given positions
                    // 40 - y is done to flip the y-axis, as tile representation is flipped from our position representation
                    List<Node> path = aStarPathFinding.treeSearch(new ArrayList<>(Arrays.asList(enemyX, 40 - enemyY)), new ArrayList<>(Arrays.asList(playerX, 40 - playerY)), world);
                    //Removes the initial node so it does not move there
                    if (path.size() > 1) {
                        path.remove(path.size() - 1);
                    }

                    Node nextPoint = path.get(path.size()-1);
                    // the y of the target tile is flipped to fit the position representation
                    // the output from the search is given in tiles (0-40), so it's scaled to fit the position representation (0-1280)
                    float targetY = (gameData.getMapHeight() - (nextPoint.getY() * (gameData.getMapHeight() / 40f)));
                    float targetX = (nextPoint.getX() * (gameData.getMapWidth() / 50f));

                    if (targetX < enemy.getX() + (enemy.getRadius() * 16) / 2) {
                        movingPart.setLeft(true);
                        movingPart.setRight(false);
                    } else {
                        movingPart.setRight(true);
                        movingPart.setLeft(false);
                    }

                    if (targetY < enemy.getY()) {
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
            lifePart.process(gameData, enemy);
        }


    }
}
