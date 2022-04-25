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
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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

            int enemyX = (int) ((((enemy.getRegionWidth()/2)+enemy.getX()) / gameData.getMapWidth()) * 50);
            AnimationPart animationPart = enemy.getPart(AnimationPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            for (Entity player : world.getEntities(Player.class)) {
                // getting player position
                int playerY = (int) ((player.getY() / gameData.getMapHeight()) * 40);
                int playerX = (int) (((player.getX() + 32 / 2) / gameData.getMapWidth()) * 50);
                if (player.getY() > 300) {
                    // Initialization of a new search for the given positions
                    // 40 - y is done to flip the y-axis, as tile representation is flipped from our position representation
                    List<Node> path = aStarPathFinding.treeSearch(new ArrayList<>(Arrays.asList(enemyX, enemyY)), new ArrayList<>(Arrays.asList(playerX, playerY)), world);
                    //Removes the initial node so it does not move there
                    /*if (path.size() > 1) {
                        path.remove(path.size() - 1);
                    } */

                    Node nextPoint;
                    if(path.size() > 2) {
                        nextPoint = path.get(path.size() - 3);
                    }
                    else{
                        nextPoint = path.get(0);
                    }
                    // the y of the target tile is flipped to fit the position representation
                    // the output from the search is given in tiles (0-40), so it's scaled to fit the position representation (0-1280)
                    float targetY = (nextPoint.getY() *32)+16;
                    float targetX = (nextPoint.getX() * 32)+16;
                    float currentX = (int) enemy.getX() + (enemy.getRadius() * 16) / 2;
                    float currentY = (int) enemy.getY();

                    //used to show path
                    ShapeRenderer sr = new ShapeRenderer();

                    sr.setProjectionMatrix(gameData.getCam().combined);
                    sr.begin(ShapeRenderer.ShapeType.Line);
                    sr.setColor(Color.GREEN);

                    for(Node node : path){
                        int nodeX = node.getX()*32;
                        int nodeY = 32+(node.getY())*32;

                        sr.line(nodeX,nodeY,nodeX+32,nodeY);
                        sr.line(nodeX,nodeY,nodeX,nodeY-32);

                        sr.line(nodeX+32,nodeY-32,nodeX+32,nodeY);
                        sr.line(nodeX+32,nodeY-32,nodeX,nodeY-32);
                    }
                    sr.end();

                    if (targetX < currentX) {
                        movingPart.setLeft(true);
                        movingPart.setRight(false);
                    } else {
                        movingPart.setRight(true);
                        movingPart.setLeft(false);
                    }

                    if (targetY < currentY) {
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
