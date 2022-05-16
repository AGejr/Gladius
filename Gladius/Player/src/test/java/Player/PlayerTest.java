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
    public void setup() {
        gameData = new GameData();
        World world = new World();

        player = new Player("", 3);

        playerMovingPart = new MovingPart(100);
        player.add(playerMovingPart);

        gameData.setDelta(0.1f);

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
        playerMovingPart.process(gameData, player);
        float newPlayerLocation = player.getX();
        assertTrue(newPlayerLocation > oldPlayerLocation);

        // Stop movement
        playerMovingPart.setRight(false);

        // LEFT
        oldPlayerLocation = player.getX();
        playerMovingPart.setLeft(true);
        playerMovingPart.process(gameData, player);
        newPlayerLocation = player.getX();
        assertTrue(newPlayerLocation < oldPlayerLocation);

        // Stop movement
        playerMovingPart.setLeft(false);

        // UP
        oldPlayerLocation = player.getY();
        playerMovingPart.setUp(true);
        playerMovingPart.process(gameData, player);
        newPlayerLocation = player.getY();
        assertTrue(newPlayerLocation > oldPlayerLocation);

        // Stop movement
        playerMovingPart.setUp(false);

        // DOWN
        oldPlayerLocation = player.getY();
        playerMovingPart.setDown(true);
        playerMovingPart.process(gameData, player);
        newPlayerLocation = player.getY();
        assertTrue(newPlayerLocation < oldPlayerLocation);
    }
}
