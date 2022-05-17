package Monster;

import Common.data.GameData;
import Common.data.World;
import Common.services.IEntityFactoryService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class MonsterFactoryTest {
    IEntityFactoryService factoryService;
    GameData gameData;
    World world;

    @Before
    public void setUp() throws Exception {
        factoryService = new MonsterFactory();
        gameData = new GameData();
        world = new World();
    }

    @Test
    public void spawn() {
        int entitiesBeforeStart = world.getEntities().size();
        factoryService.spawn(gameData, world, 1);
        int entitiesAfterStart = world.getEntities().size();
        assertNotEquals(entitiesAfterStart, entitiesBeforeStart);
    }

    @Test
    public void stop() {
        factoryService.spawn(gameData, world, 1);
        int entitiesBeforeStop = world.getEntities().size();
        factoryService.stop(world);
        int entitiesAfterStop = world.getEntities().size();
        assertNotEquals(entitiesAfterStop, entitiesBeforeStop);
    }
}