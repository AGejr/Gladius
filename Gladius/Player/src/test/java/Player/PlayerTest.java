package Player;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.*;
import CommonPlayer.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private GameData gameData;
    private Entity player;
    private MovingPart playerMovingPart;

    @Before
    public void init() {
        gameData = new GameData();
        World world = new World();

        player = new Player("", 3);

        playerMovingPart = new MovingPart(100);
        player.add(playerMovingPart);

        world.addEntity(player);
    }

    @Test
    public void playerMovement() {
        // Check if player is created with all the right parts
        assertNotNull(player.getPart(MovingPart.class));

        playerMovingPart.process(gameData, player);

        // RIGHT
        float oldPlayerLocation = player.getX();
        playerMovingPart.setRight(true);
        gameData.setDelta(0.1f);
        playerMovingPart.process(gameData, player);
        float newPlayerLocation = player.getX();
        System.out.println(oldPlayerLocation + ", " + newPlayerLocation);
        assertTrue(newPlayerLocation > oldPlayerLocation);

        // Stop movement
        playerMovingPart.setLeft(false);
        playerMovingPart.setRight(false);
        playerMovingPart.setUp(false);
        playerMovingPart.setDown(false);

        // LEFT
        oldPlayerLocation = player.getX();
        playerMovingPart.setLeft(true);
        gameData.setDelta(0.1f);
        playerMovingPart.process(gameData, player);
        newPlayerLocation = player.getX();
        System.out.println(oldPlayerLocation + ", " + newPlayerLocation);
        assertTrue(newPlayerLocation < oldPlayerLocation);

        // Stop movement
        playerMovingPart.setLeft(false);
        playerMovingPart.setRight(false);
        playerMovingPart.setUp(false);
        playerMovingPart.setDown(false);

        // UP
        oldPlayerLocation = player.getY();
        playerMovingPart.setUp(true);
        gameData.setDelta(0.1f);
        playerMovingPart.process(gameData, player);
        newPlayerLocation = player.getY();
        System.out.println(oldPlayerLocation + ", " + newPlayerLocation);
        assertTrue(newPlayerLocation > oldPlayerLocation);

        // Stop movement
        playerMovingPart.setLeft(false);
        playerMovingPart.setRight(false);
        playerMovingPart.setUp(false);
        playerMovingPart.setDown(false);

        // DOWN
        oldPlayerLocation = player.getY();
        playerMovingPart.setDown(true);
        gameData.setDelta(0.1f);
        playerMovingPart.process(gameData, player);
        newPlayerLocation = player.getY();
        System.out.println(oldPlayerLocation + ", " + newPlayerLocation);
        assertTrue(newPlayerLocation < oldPlayerLocation);
    }
}
