package Common.ai;

import Common.data.Entity;

import java.util.ArrayList;
import java.util.List;

public class GreedyBestFirst {
    private Node initialState;
    private Node goalState;

    public List<Node> treeSearch(List<Integer> initialState) {
        List<Node> fringe = new ArrayList<>();
        Node initialNode = new Node(initialState);
        fringe.add(initialNode);
        while (!fringe.isEmpty()) {
            Node node = removeLowestHeuristic(fringe);
            if (goalState.getState().containsValue(node.getState().get(0))) {
                return node.path();
            }
            List<Node> children = expand(node);
            fringe = insertAll(children, fringe);
        }
        return null;
    }

    private Node removeLowestHeuristic(List<Node> fringe) {
        int lowest = 0;
        for (int i = 1; i < fringe.size(); i++) {
            if (heuristic(fringe.get(i).getState()) < heuristic(fringe.get(lowest).getState()))) {
                lowest = i;
            }
        }
        Node last = fringe.get(lowest);
        fringe.remove(lowest);
        return last;
    }

    //GoalState is constant, currentNode is the one getting closer
    private float heuristic(Node currentNode) {
        return Math.abs(currentNode.getX() - goalState.getX()) + Math.abs(currentNode.getY() - goalState.getY());
    }

}
