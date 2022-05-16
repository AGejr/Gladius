package Common.ai;

import Common.data.World;
import Common.tools.FileLoader;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AStarPathFindingTest {
    private AStarPathFinding pathFinding = new AStarPathFinding();
    private World world;

    @Before
    public void setUp() throws Exception {
        world = new World();
        FileLoader.loadFile("Map.tmx", this.getClass());
        world.setCsvMap(FileLoader.fetchData("Map.tmx"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void treeSearch() {
        List<Integer> initialState = new ArrayList<>(Arrays.asList(10, 29));
        List<Integer> goalState = new ArrayList<>(Arrays.asList(9, 15));

        List<Node> path = pathFinding.treeSearch(initialState, goalState, world.getCsvMap());

        //Assert that all nodes is not a wall (wall value is 1)
        for (Node node : path) {
            assertNotEquals(node.getCsvVal(), 1);
        }
    }
}