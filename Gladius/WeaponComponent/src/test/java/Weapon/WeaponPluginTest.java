package Weapon;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import CommonWeapon.Weapon;
import Common.services.IGamePluginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeaponPluginTest {
    IGamePluginService plugin;
    GameData gameData;
    World world;
    Entity weapon;


    @BeforeEach
    public void setUp() throws Exception {
        plugin = new WeaponPlugin();
        gameData = new GameData();
        world = new World();
        weapon = new Weapon();
        world.addEntity(weapon);
    }

    @Test
    public void stopTest() {
        int entitiesBeforeStop = world.getEntities().size();
        plugin.stop(gameData, world);
        int entitiesAfterStop = world.getEntities().size();
        assertNotEquals(entitiesAfterStop, entitiesBeforeStop);
    }
}