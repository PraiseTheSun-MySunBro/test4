package ee.ttu.algoritmid.tsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chapaev on 04.12.2017.
 */
public class Node {
    private static int[] minBounds;
    private static int minBoundSum;

    private Node parent;
    private int value;
    private int minBound;
    private int bound;
    private List<Integer> passedRoutes;
    private int distance;

    public Node(int val) {
        this.distance = 0;
        this.parent = null;
        this.value = val;
        this.minBound = minBoundSum;
        this.bound = minBoundSum;
        this.passedRoutes = new ArrayList<>();
        this.passedRoutes.add(val);
    }

    public Node(Node parent, int val, int[][] matrix) {
        this.parent = parent;
        this.value = val;
        setRoutes();
        setDistance(matrix);
        setBound();
    }

    private void setRoutes() {
        this.passedRoutes = new ArrayList<>();
        this.passedRoutes.addAll(this.parent.passedRoutes);
        this.passedRoutes.add(this.value);
    }

    private void setDistance(int[][] matrix) {
        this.distance = this.parent.distance + matrix[this.parent.value][this.value];
    }

    private void setBound() {
        this.minBound = this.parent.minBound - minBounds[this.parent.value];
        this.bound = this.distance + this.minBound;
    }

    public static void setMinBound(int[][] matrix) {
        minBounds = new int[matrix.length];
        minBoundSum = 0;
        int idx = 0;
        for (int[] row : matrix) {
            int min = Integer.MAX_VALUE;
            for (int val : row) {
                if (val != 0 && val < min)
                    min = val;
            }
            minBounds[idx] = min;
            minBoundSum += min;
            idx++;
        }
    }

    public static List<Node> getRoutes(Node node, int[][] matrix, int best) {
        List<Node> routes = new ArrayList<>();
        for (int val = 0; val < matrix.length; ++val) {
            if (!node.passedRoutes.contains(val)) {
                Node newNode = new Node(node, val, matrix);
                if (newNode.getBound() < best) routes.add(newNode);
            }
        }
        return routes;
    }

    public List<Integer> getPassedRoutes() {
        return this.passedRoutes;
    }

    public int getDistance() {
        return distance;
    }

    public int getPassedRoutesSize() {
        return passedRoutes.size();
    }

    public int getBound() {
        return this.bound;
    }
}
