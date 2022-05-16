package Monster;

import Common.ai.AStarPathFinding;
import Common.ai.Node;
import Common.data.Entity;
import Common.data.GameData;
import Common.data.SoundData;
import Common.data.World;
import Common.data.entityparts.AnimationPart;
import Common.data.entityparts.LifePart;
import Common.data.entityparts.MovingPart;
import Common.data.entityparts.SoundPart;
import Common.services.IEntityProcessingService;
import CommonPlayer.Player;
import CommonWeapon.IWeaponService;
import CommonMonster.Monster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonsterControlSystem implements IEntityProcessingService {
    private IWeaponService weaponService;
    private AStarPathFinding aStarPathFinding = new AStarPathFinding();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity monster : world.getEntities(Monster.class)) {
            MovingPart movingPart = monster.getPart(MovingPart.class);

            // get the enemy position on the tile map
            int enemyY = (int) ((monster.getY() / gameData.getMapHeight()) * 40);
            int enemyX = (int) ((((monster.getRegionWidth() / 2) + monster.getX()) / gameData.getMapWidth()) * 50);

            AnimationPart animationPart = monster.getPart(AnimationPart.class);
            LifePart lifePart = monster.getPart(LifePart.class);
            SoundPart soundPart = monster.getPart(SoundPart.class);

            for (Entity player : world.getEntities(Player.class)) {
                // getting player position on the tile map
                int playerY = (int) ((player.getY() / gameData.getMapHeight()) * 40);
                int playerX = (int) (((player.getX() + player.getTextureWidth() / 2) / gameData.getMapWidth()) * 50);
                LifePart playerLifepart = player.getPart(LifePart.class);
                if (!lifePart.isDead()) {
                    // if player is in hub (<300) && player is inside the wall (39 is gridMapHeight)
                    if (player.getY() > 300 && world.getCsvMap().get(39 - playerY).get(playerX) != 1) {

                        Polygon attackRange = ((Monster) monster).getAttackRange();
                        attackRange.setPosition(monster.getX(), monster.getY());
                        attackRange.getBoundingRectangle();

                        // Checking if player is inside of enemy's attack range
                        if (Intersector.overlapConvexPolygons(attackRange, player.getPolygonBoundaries())) {
                            LifePart playerLifePart = player.getPart(LifePart.class);
                            if (animationPart.getCurrentAnimation().isAnimationFinished(animationPart.getAnimationTime()) && !playerLifePart.isDead()) {
                                if (monster.getX() > player.getX()) {
                                    soundPart.playAudio(SoundData.SOUND.ATTACK);
                                    animationPart.setCurrentState(AnimationPart.ANIMATION_STATES.ATTACK_LEFT);
                                } else {
                                    soundPart.playAudio(SoundData.SOUND.ATTACK);
                                    animationPart.setCurrentState(AnimationPart.ANIMATION_STATES.ATTACK_RIGHT);
                                }
                                if (weaponService != null && animationPart.isDoneAnimating()) {
                                    weaponService.attack(monster, gameData, world);
                                }

                            }
                        } else {
                            if (animationPart.getCurrentAnimation().isAnimationFinished(animationPart.getAnimationTime())) {
                                if (animationPart.isLeft()) {
                                    animationPart.setCurrentState(AnimationPart.ANIMATION_STATES.IDLE_LEFT);
                                } else {
                                    animationPart.setCurrentState(AnimationPart.ANIMATION_STATES.IDLE_RIGHT);
                                }
                            }
                        }


                        // listing the positions of enemy and the target (player) in Lists
                        List<Integer> enemyPos = new ArrayList<>(Arrays.asList(enemyX, enemyY));
                        List<Integer> targetPos = new ArrayList<>(Arrays.asList(playerX, playerY));

                        // Initialization of a new search for the given positions
                        List<Node> path = aStarPathFinding.treeSearch(enemyPos, targetPos, world.getCsvMap());

                        if(path != null){

                        //Removes the goal node so it does not stand on the goal, but next to it
                        if (path.size() > 1) {
                            path.remove(0);
                        }

                        // choose the point to go to
                        Node nextPoint;
                        // if there are over 2 points in the path
                        if (path.size() > 2) {
                            //get the 3rd node from start (including start node).
                            //Makes it possible to walk diagonal
                            nextPoint = path.get(path.size() - 3);
                        } else {
                            // get goal node
                            nextPoint = path.get(0);
                        }
                        // the y of the target tile is flipped to fit the position representation
                        // the output from the search is given in tiles (0-40), so it's scaled to fit the position representation (0-1280)
                        float targetY = (nextPoint.getY() * 32) + 16;
                        float targetX = (nextPoint.getX() * 32) + 16;
                        float currentX = (int) monster.getX() + (monster.getRadius() * 16) / 2;
                        float currentY = (int) monster.getY();

                        //if not at/near end goal
                        // the X position of the middle of the players texture
                        float playerFullX = player.getX() + player.getTextureWidth() / 2f;
                        double lengthToTarget = Math.sqrt(Math.pow(Math.abs(currentX - playerFullX), 2) + Math.pow(Math.abs(currentY - player.getY()), 2));
                        //if not within 96 pixels (3 tiles) of the goal target
                        if (!(lengthToTarget <= 96)) {
                            decideMovement(currentX, currentY, targetX, targetY, movingPart);
                        } else {
                            // Value to set attackrangeint to determine movement
                            int attackRangeInt = monster.getTextureWidth() / 3;
                            //if length to target is still outside attack range
                            if (lengthToTarget > attackRangeInt) {
                                decideMovement(currentX, currentY, playerFullX, player.getY(), movingPart);
                            }
                            // if inside attack range, either max range, 90 % of attack range
                            else if (lengthToTarget < attackRangeInt && lengthToTarget >= attackRangeInt * 0.9f) {
                                stopMovement(movingPart);

                            } else {
                                /*
                                       modifiers are used to figure out which direction the enemy is to its target
                                       used to modify the attackRangeInt variable
                                       decide whether to modify position positively or negatively
                                       Tells the enemy if it should walk left or right, up or down
                                    */
                                int yModifier = player.getY() < currentY ? 1 : -1;
                                int xModifier = playerFullX < currentX ? 1 : -1;

                                // if the enemy within 2 pixels on the same horizontal plane as the player (2 pixels up and 2 pixels down)
                                if (Math.abs(player.getY() - currentY) <= 2) {
                                    // decide movement with the Y parameter of both entities as the same (0)
                                    decideMovement(currentX, 0, playerFullX + (attackRangeInt * xModifier), 0, movingPart);
                                }
                                // else if the enemy within 2 pixels on the same vertical plane as the player (2 pixels left and 2 pixels right)
                                else if (Math.abs(playerFullX - currentX) <= 2) {
                                    // decide movement with the X parameter of both entities as the same (0)
                                    decideMovement(0, currentY, 0, player.getY() + (attackRangeInt * yModifier), movingPart);
                                }
                                // else do movement as with the current values.
                                else {
                                    decideMovement(currentX, currentY, playerFullX + (attackRangeInt * xModifier), player.getY() + (attackRangeInt * yModifier), movingPart);
                                }
                            }
                        }
                    } else {
                            //if player is not inside arena
                            stopMovement(movingPart);
                        }
                        decideMovement(enemyX, enemyY, playerX,playerY, movingPart);
                    }
                } else if (playerLifepart.isDead()) {
                    stopMovement(movingPart);
                    if (!lifePart.isDead()) {
                        if (animationPart.isLeft()) {
                            animationPart.setCurrentState(AnimationPart.ANIMATION_STATES.IDLE_LEFT);
                        } else {
                            animationPart.setCurrentState(AnimationPart.ANIMATION_STATES.IDLE_RIGHT);
                        }
                    }
                }

            }

            movingPart.process(gameData, monster);
            animationPart.process(gameData, monster);
            lifePart.process(gameData, monster);
            soundPart.process(gameData, monster);
        }

    }

    private void drawDebugLines(GameData gameData, List<Node> path, Polygon attackRange) {
        //used to show path
        ShapeRenderer sr = new ShapeRenderer();

        //set projection to not follow camera
        sr.setProjectionMatrix(gameData.getCam().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.GREEN);

        // for every node, draw its outline

        for (Node node : path) {
            int nodeX = node.getX() * 32;
            int nodeY = 32 + (node.getY()) * 32;

            sr.line(nodeX, nodeY, nodeX + 32, nodeY);
            sr.line(nodeX, nodeY, nodeX, nodeY - 32);

            sr.line(nodeX + 32, nodeY - 32, nodeX + 32, nodeY);
            sr.line(nodeX + 32, nodeY - 32, nodeX, nodeY - 32);
        }
        sr.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.RED);
        sr.polygon(attackRange.getTransformedVertices());
        sr.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void stopMovement(MovingPart movingPart) {
        movingPart.setDown(false);
        movingPart.setUp(false);
        movingPart.setLeft(false);
        movingPart.setRight(false);
    }

    public void setWeaponService(IWeaponService weaponService) {
        this.weaponService = weaponService;
    }

    public void removeWeaponService(IWeaponService weaponService) {
        this.weaponService = null;
    }

    private void decideMovement(float X, float Y, float targetX, float targetY, MovingPart movingPart) {
        // if the targetX and EnemyX is not the same
        X = (int) X;
        Y = (int) Y;
        targetX = (int) targetX;
        targetY = (int) targetY;

        if (!(targetX == X)) {

            if (targetX < X) {
                movingPart.setLeft(true);
                movingPart.setRight(false);
            } else {
                movingPart.setRight(true);
                movingPart.setLeft(false);
            }
        } else {
            movingPart.setLeft(false);
            movingPart.setRight(false);
        }
        if (targetY < Y) {
            movingPart.setUp(false);
            movingPart.setDown(true);
        } else if (targetY > Y) {
            movingPart.setDown(false);
            movingPart.setUp(true);
        } else {
            movingPart.setUp(false);
            movingPart.setDown(false);
        }
    }
}
