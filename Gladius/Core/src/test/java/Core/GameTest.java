package Core;

import Common.data.Entity;
import Common.data.GameData;
import Common.data.World;
import Common.data.entityparts.*;
import Common.services.IEntityProcessingService;
import CommonEnemy.Enemy;
import com.badlogic.gdx.graphics.Color;
import org.junit.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class GameTest {
    GameData gameData;
    World world;
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();

    @Before
    public void setUp() throws Exception {
        gameData = mock(GameData.class);
        world = mock(World.class);
    }

    @After
    public void tearDown() throws Exception {
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

    @Test
    public void create() {
        
    }

    @Test
    public void testRender() {
    }

    /**
     * Test the update of bundles
     */
    @Test
    public void update() {

    }

    @Test
    public void dispose() {
    }
}