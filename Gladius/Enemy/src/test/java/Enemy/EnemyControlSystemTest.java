package Enemy;

import Common.data.GameData;
import Common.data.World;
import Common.services.IEntityProcessingService;
import CommonEnemy.Enemy;
import org.junit.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

public class EnemyControlSystemTest {
    World world;
    GameData gameData;
    Enemy enemy;
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();

    @Before
    public void setUp() throws Exception {
        world = new World();
        gameData = new GameData();
        enemy = new Enemy("",0,0);
        world.addEntity(enemy);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void load() throws Exception {
        assertEquals(0,entityProcessorList.size());

        entityProcessorList.add(new EnemyControlSystem());

        assertEquals(1,entityProcessorList.size());
    }

    @Test
    public void unload() throws Exception {
        entityProcessorList.add(new EnemyControlSystem());

        assertEquals(1, entityProcessorList.size());

        entityProcessorList.remove(0);

        assertEquals(0,entityProcessorList.size());
    }

    @Test
    public void start() throws Exception {
        EnemyFactory enemyFactory = new EnemyFactory();

        int entitiesBeforeStop = world.getEntities().size();

        enemyFactory.stop(world);

        int entitiesAfterStop = world.getEntities().size();

        assertNotEquals(entitiesAfterStop, entitiesBeforeStop);
    }

    @Test
    public void stop() throws Exception {
        EnemyFactory enemyFactory = new EnemyFactory();

        int entitiesBeforeStop = world.getEntities().size();

        enemyFactory.stop(world);

        int entitiesAfterStop = world.getEntities().size();

        assertNotEquals(entitiesAfterStop, entitiesBeforeStop);
    }


}