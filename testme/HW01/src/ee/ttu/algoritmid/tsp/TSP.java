package ee.ttu.algoritmid.tsp;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;

public class TSP {
    BigInteger checkedNodesCount;
    static int[][] adjacencyMatrix;
    static int[] bounds;

    int citiesCount;
    int boundSum;
    int bestDistance;
    List<Integer> bestRoute;

    /* Depth first search */
    public List<Integer> depthFirst(int[][] adjacencyMatrix) {
        TSP.adjacencyMatrix = adjacencyMatrix;
        checkedNodesCount = BigInteger.ZERO;

        AbstractMap.SimpleEntry<Integer, int[]> calculatedBound = calculateMatrixBound(adjacencyMatrix);
        bounds = calculatedBound.getValue();
        bestRoute = new ArrayList<>();

        citiesCount = adjacencyMatrix.length;
        boundSum = calculatedBound.getKey();
        bestDistance = Integer.MAX_VALUE;

        if (citiesCount <= 1) {
            if (citiesCount == 0) return new ArrayList<>();
            return new ArrayList<>(Arrays.asList(0));
        }

        Stack<Node> stack = new Stack<>();
        Node root = new Node(0, boundSum);
        stack.push(root);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            increaseCheckedNodesCount();

            if (isReachedLastCity(current.route.size())) {
                calculateBestRoute(current);
                continue;
            }

            for (Node nextCity : getRoutes(current)) {
                if (bestDistance > nextCity.bound)
                    stack.push(nextCity);
            }
        }
        return bestRoute;
    }

    /* Best first search */
    public List<Integer> bestFirst(int[][] adjacencyMatrix) {
        TSP.adjacencyMatrix = adjacencyMatrix;
        checkedNodesCount = BigInteger.ZERO;

        AbstractMap.SimpleEntry<Integer, int[]> calculatedBound = calculateMatrixBound(adjacencyMatrix);
        bounds = calculatedBound.getValue();
        bestRoute = new ArrayList<>();

        citiesCount = adjacencyMatrix.length;
        boundSum = calculatedBound.getKey();
        bestDistance = Integer.MAX_VALUE;

        if (citiesCount <= 1) {
            if (citiesCount == 0) return new ArrayList<>();
            return new ArrayList<>(Arrays.asList(0));
        }

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        Node root = new Node(0, boundSum);
        priorityQueue.add(root);

        while (!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();
            increaseCheckedNodesCount();

            if (isReachedLastCity(current.route.size())) {
                calculateBestRoute(current);
                continue;
            }

            for (Node nextCity : getRoutes(current)) {
                if (bestDistance > nextCity.bound)
                    priorityQueue.add(nextCity);
            }
        }
        return bestRoute;
    }

    /* Nodes viewed in last matrix to find the solution (should be zeroed at the beginning of search) */
    public BigInteger getCheckedNodesCount() {
        return checkedNodesCount;
    }

    List<Node> getRoutes(Node current) {
        List<Node> routes = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (current.route.contains(i)) continue;
            routes.add(new Node(i, current));
        }
        return routes;
    }

    boolean isReachedLastCity(int nodeCitiesCount) {
        return citiesCount == nodeCitiesCount;
    }

    void calculateBestRoute(Node current) {
        Node finish = new Node(0, current);
        if (bestDistance > finish.distance) {
            bestRoute = finish.route;
            bestDistance = finish.distance;
        }
    }

    void increaseCheckedNodesCount() {
        checkedNodesCount = checkedNodesCount.add(BigInteger.ONE);
    }

    static AbstractMap.SimpleEntry<Integer, int[]> calculateMatrixBound(int[][] adjacencyMatrix) {
        int[] matrixBound = new int[adjacencyMatrix.length];
        int boundSum = 0;

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            int minBound = IntStream.of(adjacencyMatrix[i])
                    .filter(val -> val > 0)
                    .min()
                    .getAsInt();
            matrixBound[i] = minBound;
            boundSum += minBound;
        }
        return new AbstractMap.SimpleEntry<Integer, int[]>(boundSum, matrixBound);
    }
}