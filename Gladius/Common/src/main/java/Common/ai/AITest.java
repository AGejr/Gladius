package Common.ai;

import Common.data.World;
import Common.tools.FileLoader;

import java.util.ArrayList;
import java.util.List;

public class AITest {
    public static void main(String[] args) {
        AStarPathFinding pathFinding = new AStarPathFinding();
        List<Integer> initialState = new ArrayList<>();
        initialState.add(17);
        initialState.add(40-28);


        List<Integer> goalState = new ArrayList<>();
        goalState.add(23);
        goalState.add(40-27);//14
        World world = new World();
        String file = "C:/Users/janik/Downloads/Map.tmx/";
        world.setCsvMap(FileLoader.fetchData(file));
        List<List<Integer>> map = new ArrayList<>();


        for (int i = 0; i < 40; i++) {
            List<Integer> list = world.getCsvMap().get(i);
            map.add(list);
        }
        System.out.println(initialState);
        System.out.println(goalState);
        List<Node> result = pathFinding.treeSearch(initialState, goalState, world);
        for (int i = 0; i < result.size(); i++) {
            map.get(40 - result.get(i).getY()).set(result.get(i).getX(), 99);
        }
        System.out.println(result);
        int count = 0;
        for (List<Integer> list1 : map) {
            count++;
            //System.out.println(count + " " + list1);
        }
    }
}
