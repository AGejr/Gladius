package Common.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Node {
    private List<Integer> state; //The list with integers contains the x and y coordinates.
    private Node parentNode;
    private int depth;
    private int pathCost;

    public Node(List<Integer> state, Node parentNode) {
        this.state = state;
        this.parentNode = parentNode;
        this.depth = parentNode.getDepth() + 1;
        this.pathCost = parentNode.getPathCost();
    }

    public Node(List<Integer> state) {
        this.state = state;
        this.parentNode = null;
        this.depth = 0;
        this.pathCost = 0;
    }

    public List<Node> path() {
        Node currentNode = this;
        List<Node> path = new ArrayList<>();
        path.add(this);
        while (currentNode.getParentNode() != null) {
            currentNode = currentNode.getParentNode();
            path.add(currentNode);
        }
        return path;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public List<Integer> getState() {
        return state;
    }

    public void setState(List<Integer> state) {
        this.state = state;
    }

    public int getX() {
        return this.getState().get(0);
    }

    public int getY(){
        return this.getState().get(1);
    }

    @Override
    public String toString() {
        return "Node{" +
                "state=" + state +
                '}';
    }

    public int getPathCost() {
        return pathCost;
    }
}
