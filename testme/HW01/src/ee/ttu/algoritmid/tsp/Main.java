package ee.ttu.algoritmid.tsp;

public class Main {
    public static void main(String[] args) throws Exception {
        TSP tsp = new TSP();
        System.out.println(tsp.depthFirst(MatrixLoader.loadFile("asymmetric.in", 15)));
    }
}
