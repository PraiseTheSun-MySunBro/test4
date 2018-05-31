package ee.ttu.algoritmid.tsp;

import java.math.BigInteger;
import java.util.*;

public class TSP {
    private BigInteger checkedNodesCount;
    private int nodesCount;
    private int best;
    private List<Integer> bestRoute;

    /* Depth first search */
    public List<Integer> depthFirst(int[][] adjacencyMatrix) {
        this.nodesCount = adjacencyMatrix.length;
        this.checkedNodesCount = BigInteger.ZERO;
        if (nodesCount == 0) return new ArrayList<>();
        if (nodesCount == 1) {
            this.checkedNodesCount = BigInteger.ONE;
            return Collections.singletonList(0);
        }
        Node.setMinBound(adjacencyMatrix);
        this.best = Integer.MAX_VALUE;

        Stack<Node> stack = new Stack<>();
        stack.push(new Node(0));
        while (!stack.isEmpty()) {
            Node currentNode = stack.pop();
            this.checkedNodesCount = this.checkedNodesCount.add(BigInteger.ONE);
            if (currentNode.getPassedRoutesSize() == nodesCount) {
                Node last = new Node(currentNode, 0, adjacencyMatrix);
                setBestRoute(last);
            } else {
                for (Node node : Node.getRoutes(currentNode, adjacencyMatrix, this.best)) {
                    if (node.getBound() >= this.best) continue;
                    stack.push(node);
                }
            }
        }
        return this.bestRoute;
    }

    /* Best first search */
    public List<Integer> bestFirst(int[][] adjacencyMatrix) {
        this.nodesCount = adjacencyMatrix.length;
        this.checkedNodesCount = BigInteger.ZERO;
        if (nodesCount == 0) return new ArrayList<>();
        if (nodesCount == 1) {
            this.checkedNodesCount = BigInteger.ONE;
            return Collections.singletonList(0);
        }
        Node.setMinBound(adjacencyMatrix);
        this.best = Integer.MAX_VALUE;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getBound));
        pq.add(new Node(0));
        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            this.checkedNodesCount = this.checkedNodesCount.add(BigInteger.ONE);
            if (currentNode.getPassedRoutesSize() == nodesCount) {
                Node last = new Node(currentNode, 0, adjacencyMatrix);
                setBestRoute(last);
            } else {
                pq.addAll(Node.getRoutes(currentNode, adjacencyMatrix, this.best));
            }
        }
        return this.bestRoute;
    }

    /* Nodes viewed in last matrix to find the solution (should be zeroed at the beginnig of search) */
    public BigInteger getCheckedNodesCount() {
        return this.checkedNodesCount;
    }

    private void setBestRoute(Node node) {
        if (node.getDistance() < this.best) {
            this.best = node.getDistance();
            this.bestRoute = node.getPassedRoutes();
        }
    }
}