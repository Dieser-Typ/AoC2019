package day15;

public class Node {
    private Node[] nodes;
    private int prevNodeIndex;
    private int steps;
    private int visitedNodes;

    Node() {
        nodes = new Node[4];
        prevNodeIndex = -1;
        steps = 0;
        visitedNodes = 0;
    }

    Node(Node prevNode, int index) {
        nodes = new Node[4];
        nodes[index] = prevNode;
        prevNodeIndex = index;
        steps = prevNode.steps + 1;
        visitedNodes = 1;
    }

    public int nextMovement() {
        if (visitedNodes == 4)
            return prevNodeIndex;
        return visitedNodes > prevNodeIndex? visitedNodes : visitedNodes - 1;
    }

    public Node move(long status) {
        if (visitedNodes == 4)
            return nodes[prevNodeIndex];
        int dir = nextMovement();
        visitedNodes++;
        if (status == 0)
            return this;
        return new Node(this, dir / 2 * 2 + 1 - dir  % 2);
    }

    public int getSteps() {
        return steps;
    }
}
