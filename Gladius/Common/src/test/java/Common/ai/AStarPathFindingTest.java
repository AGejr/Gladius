package Common.ai;

import Common.data.World;
import Common.tools.FileLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AStarPathFindingTest {
    private AStarPathFinding pathFinding = new AStarPathFinding();
    private World world;

    @BeforeEach
    public void setUp() throws Exception {
        world = new World();
        FileLoader.loadFile("Map.tmx", this.getClass());
        world.setCsvMap(FileLoader.fetchData("Map.tmx"));
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    public void treeSearchTest() {
        List<Integer> initialState = new ArrayList<>(Arrays.asList(10, 29));
        List<Integer> goalState = new ArrayList<>(Arrays.asList(9, 15));

        List<Node> path = pathFinding.treeSearch(initialState, goalState, world.getCsvMap());

        //Assert that all nodes is not a wall (wall value is 1)
        for (Node node : path) {
            assertNotEquals(node.getCsvVal(), 1);
        }
    }
}