package Common.ai;

import Common.data.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AStarPathFinding {
    // TODO implement multiple goal states (e.g monster wanting multiple targets)
    private List<Integer> goalState;
    private final int[] noCollide = {0, 70, 71, 72, 78, 79, 86, 87, 88, 103, 104, 107, 123, 127, 159, 155, 108, 24, 25, 37, 98, 99, 159, 160, 161, 162, 163, 164, 165, 177, 178, 179};
    private List<List<Integer>> csv;
    private List<List<Integer>> closedNodes;

    public List<Node> treeSearch(List<Integer> initialState, List<Integer> goalState, World world) {
        this.goalState = goalState;
        this.closedNodes = new ArrayList<>();
        this.csv = world.getCsvMap();
        List<Node> fringe = new ArrayList<>();
        Node initialNode = new Node(initialState);
        fringe.add(initialNode);
        while (!fringe.isEmpty()) {
            Node node = removeLowestHeuristic(fringe);
            if (goalState.equals(node.getState())) {
                return node.path();
            }
            List<Node> children = expand(node);
            fringe.addAll(children);
        }
        return null;
    }

    private List<Node> expand(Node parent) {
        List<Node> successors = new ArrayList<>();

        List<Node> children = successors(parent);
        for (Node child : children) {
            Node successor = new Node(new ArrayList<>());
            successor.setState(child.getState());
            successor.setParentNode(parent);
            successor.setDepth(parent.getDepth() + 1);
            successors.add(successor);
        }
        return successors;
    }

    private List<Node> successors(Node parent) {
        List<Node> successors = new ArrayList<>();
        List<Node> removeSuccessors = new ArrayList<>();
        int[] around = {1, -1};
        for (int i : around) {
            successors.add(new Node(Arrays.asList(parent.getX() + i, parent.getY()), parent));
            successors.add(new Node(Arrays.asList(parent.getX(), parent.getY() + i), parent));
        }
        for (Node node : successors) {
            int csvVal = csv.get(node.getY()).get(node.getX());

            if (Arrays.stream(noCollide).noneMatch(i -> i == csvVal)) {
                removeSuccessors.add(node);
            }
            if (!removeSuccessors.contains(node)) {
                for (List<Integer> closedNode : closedNodes) {
                    if (node.getX() == closedNode.get(0) && node.getY() == closedNode.get(1)) {
                        removeSuccessors.add(node);
                    }
                }
            }
        }
        successors.removeAll(removeSuccessors);
        return successors;
    }

    private Node removeLowestHeuristic(List<Node> fringe) {
        int lowest = 0;
        for (int i = 1; i < fringe.size(); i++) {
            if (heuristic(fringe.get(i)) + fringe.get(i).getDepth() < heuristic(fringe.get(lowest)) + fringe.get(lowest).getDepth()) {
                lowest = i;
            }
        }
        Node last = fringe.get(lowest);
        closedNodes.add(last.getState());
        fringe.remove(lowest);
        return last;
    }

    //GoalState is constant, currentNode is the one getting closer
    private float heuristic(Node currentNode) {
        return (float) (Math.pow(Math.abs(currentNode.getX() - goalState.get(0)), 2) + Math.pow(Math.abs(currentNode.getY() - goalState.get(1)), 2));
    }
}