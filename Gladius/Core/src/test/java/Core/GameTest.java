package Core;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IGamePluginService;
import CommonPlayer.Player;
import CommonWeapon.Weapon;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class GameTest {
    GameData gameData;
    World world;
    List<IGamePluginService> plugins = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        gameData = new GameData();
        world = new World();
        Entity player = new Player("",3);
        world.addEntity(player);
        Entity weapon = new Weapon();
        world.addEntity(weapon);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test the start of every bundle
     */
    @Test
    public void start() {
        
    }

    @Test
    public void render() {
    }


    /**
     * Test the stop of every bundle
     */
    @Test
    public void stop() {
    }
}