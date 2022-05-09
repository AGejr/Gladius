package Monster;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.services.IGamePluginService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class MonsterPluginTest {
    IGamePluginService plugin;
    GameData gameData;
    World world;

    @Before
    public void setUp() throws Exception {
        plugin = new MonsterPlugin();
        gameData = new GameData();
        world = new World();
    }

    @Test
    public void start() {
        int entitiesBeforeStart = world.getEntities().size();
        plugin.start(gameData, world);
        int entitiesAfterStart = world.getEntities().size();
        assertNotEquals(entitiesAfterStart, entitiesBeforeStart);
    }

    @Test
    public void stop() {
        plugin.start(gameData, world);
        int entitiesBeforeStop = world.getEntities().size();
        plugin.stop(gameData, world);
        int entitiesAfterStop = world.getEntities().size();
        assertNotEquals(entitiesAfterStop, entitiesBeforeStop);
    }
}