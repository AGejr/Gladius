package Common.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Node {
    private Map<Integer, List<Integer>> state; //The list with integers contains the x and y coordinates.
    private Node parentNode;
    private int depth;

    public Node(Map<Integer, List<Integer>> state, Node parentNode, int depth) {
        this.state = state;
        this.parentNode = parentNode;
        this.depth = depth;
    }

    public Node(Map<Integer, List<Integer>> state) {
        this.state = state;
        this.parentNode = null;
        this.depth = 0;
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

    public Map<Integer, List<Integer>> getState() {
        return state;
    }

    public void setState(Map<Integer, List<Integer>> state) {
        this.state = state;
    }
}
