package Common.ai;

import Common.data.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AStarPathFinding {
    private List<Integer> goalState;
    private List<List<Integer>> csv;
    private List<List<Integer>> closedNodes;
    private final int gridMapWidth = 39;


    /**
     * Performs a treeSearch to find the shortest path from initialState to goalState
     * @param initialState Chasers x(index 0) and y(index 1) coordinates
     * @param goalState Targets x(index 0) and y(index 1) coordinates
     * @param csv Tilemap
     * @return Returns the shortest path
     */
    public List<Node> treeSearch(List<Integer> initialState, List<Integer> goalState, List<List<Integer>> csv) {
        this.goalState = goalState;

        this.closedNodes = new ArrayList<>(); // closedNodes are the nodes already expanded.
        this.csv = csv;
        List<Node> fringe = new ArrayList<>();
        Node initialNode = new Node(initialState);
        initialNode.setCsvVal(csv.get(gridMapWidth - initialNode.getY()).get(initialNode.getX()));

        fringe.add(initialNode);
        while (!fringe.isEmpty()) {
            // remove the best node
            Node node = removeLowestHeuristic(fringe);

            if (goalState.equals(node.getState())) {
                return node.path();
            }
            // if its not goal state, expand the node
            List<Node> children = expand(node);
            fringe.addAll(children);
        }
        return null;
    }

    /**
     * @param parent parent node
     * @return Returns the children of the parent node
     */
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

    /**
     * @param parent The node to find successors
     * @return Returns the successors of the currentNode
     */
    private List<Node> successors(Node parent) {
        List<Node> successors = new ArrayList<>();

        //array for the nodes in successors that should be removed before return
        List<Node> removeSuccessors = new ArrayList<>();

        // Add all nodes around the parent node
        successors.add(new Node(Arrays.asList(parent.getX() + 1, parent.getY()), parent));
        successors.add(new Node(Arrays.asList(parent.getX(), parent.getY() + 1), parent));
        successors.add(new Node(Arrays.asList(parent.getX() - 1, parent.getY()), parent));
        successors.add(new Node(Arrays.asList(parent.getX(), parent.getY() - 1), parent));


        for (Node node : successors) {
            //set the CSV val of the node. Minus is used to flip the map
            node.setCsvVal(csv.get(gridMapWidth - node.getY()).get(node.getX()));
            // if node is a collideable object
            if (node.getCsvVal() == 1) {
                removeSuccessors.add(node);
                continue;
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

    /**
     * removes the node that has the lowest heurisc value
     * @return Returns the node with lowest heuristic
     */
    private Node removeLowestHeuristic(List<Node> fringe) {
        int lowest = 0;
        for (int i = 1; i < fringe.size(); i++) {
            if (heuristic(fringe.get(i)) + fringe.get(i).getDepth() < heuristic(fringe.get(lowest)) + fringe.get(lowest).getDepth()) {
                lowest = i;
            }
        }
        Node last = fringe.get(lowest);
        //add node to closed nodes, as it will be used and therefore "closed"
        closedNodes.add(last.getState());
        fringe.remove(lowest);
        return last;
    }


    /**
     * Heuristic is the diagonal length between the node and the target.
     * if current node is 2 (water), the heuristic will be scaled, because water slows down movement. (The constant 1.7 is a magic number)
     * @return Pythagoras a^2 + b^2 = c^2 is used to calculate the direct route to the goal from currentNode
     */
    private float heuristic(Node currentNode) {
        if (csv.get(gridMapWidth - currentNode.getY()).get(currentNode.getX()) == 2) {
           return (float) (Math.pow(Math.abs(currentNode.getX() - goalState.get(0)), 2) + Math.pow(Math.abs(currentNode.getY() - goalState.get(1)), 2))*1.7f;
        } else if (csv.get(gridMapWidth - currentNode.getY()).get(currentNode.getX()) == 3) {
            return (float) ((Math.pow(Math.abs(currentNode.getX() - goalState.get(0)), 2) + Math.pow(Math.abs(currentNode.getY() - goalState.get(1)), 2))+10)*1.7f;
        } else {
            return (float) (Math.pow(Math.abs(currentNode.getX() - goalState.get(0)), 2) + Math.pow(Math.abs(currentNode.getY() - goalState.get(1)), 2));
        }
    }
}
