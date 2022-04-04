package Enemy;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.MovingPart;
import Common.services.IEntityProcessingService;
import CommonPlayer.Player;

import java.util.PriorityQueue;

public class EnemyControlSystem implements IEntityProcessingService {
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

                System.out.println("ENEMY X: " + enemyX + " Y: " + enemyY);
                System.out.println("PLAYER X: " + playerX + " Y: " + playerY);

                PriorityQueue<Integer[]> frontier = new PriorityQueue();
                Integer[] start = {enemyX, enemyY};
                frontier.add(start);




                if (player.getY() > 300) {
                    // todo : radius*16/2 should be changed to texture width / 2
                    if (player.getX() + (player.getRadius()*16)/2 == enemy.getX() + (enemy.getRadius()*16)/2) {
                        movingPart.setLeft(false);
                        movingPart.setRight(false);
                    } else if (player.getX() + (player.getRadius()*16)/2 < enemy.getX() + (enemy.getRadius()*16)/2) {
                        movingPart.setLeft(true);
                        movingPart.setRight(false);
                    } else {
                        movingPart.setRight(true);
                        movingPart.setLeft(false);
                    }
                    if (player.getY() == enemy.getY()) {
                        movingPart.setUp(false);
                        movingPart.setDown(false);
                    } else if (player.getY() < enemy.getY()) {
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
