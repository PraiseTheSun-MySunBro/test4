package ee.ttu.algoritmid.tsp;

/**
 * Created by Chapaev on 04.12.2017.
 */
public class Test {
    static int[][] test = {
            {0,2,7,3}, // 2
            {5,0,3,4}, // 3
            {3,4,0,8}, // 3
            {6,8,9,0}  // 6
            // 2 + 3 + 3 + 6 = 14
    };

    static int[][] test1 = {{0}};

    public static void main(String[] args) throws Exception {
        TSP impl = new TSP();
        //System.out.println(impl.depthFirst(test));
        System.out.println(impl.depthFirst(MatrixLoader.loadFile("symmetric.in", 11)));
        //System.out.println(impl.getCheckedNodesCount());
    }
}
