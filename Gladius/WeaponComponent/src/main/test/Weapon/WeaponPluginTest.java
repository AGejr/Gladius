package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import CommonWeapon.Weapon;
import Common.services.IGamePluginService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

public class WeaponPluginTest {
    IGamePluginService plugin;
    GameData gameData;
    World world;
    Entity weapon;


    @Before
    public void setUp() throws Exception {
        plugin = new WeaponPlugin();
        gameData = new GameData();
        world = new World();
        weapon = new Weapon();
        world.addEntity(weapon);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void stop() {
        int entitiesBeforeStop = world.getEntities().size();
        plugin.stop(gameData, world);
        int entitiesAfterStop = world.getEntities().size();
        assertNotEquals(entitiesAfterStop, entitiesBeforeStop);
    }
}