package ee.ttu.algoritmid.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node implements Comparable<Node> {
    List<Integer> route;
    int cityNumber;
    int distance;
    int bound;
    int maxBound;


    public Node(int cityNumber, int maxBound) {
        this.cityNumber = cityNumber;
        this.distance = 0;

        this.bound = maxBound;
        this.maxBound = maxBound;

        this.route = new ArrayList<>(Arrays.asList(cityNumber));
    }

    public Node(int cityNumber, Node prev) {
        this.cityNumber = cityNumber;
        this.distance = prev.distance + TSP.adjacencyMatrix[prev.cityNumber][cityNumber];

        this.maxBound = prev.maxBound - TSP.bounds[prev.cityNumber];
        this.bound = this.distance + this.maxBound;

        (this.route = new ArrayList<>(prev.route)).add(cityNumber);
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.bound, o.bound);
    }
}
