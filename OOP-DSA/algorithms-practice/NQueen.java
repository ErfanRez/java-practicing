import java.util.*;

public class NQueen {
    static int n = 4;
    static int nodeCount = 0;
    static int solutionCount = 0;
    static int[] cols = new int[n + 1];

    static boolean isPromising(int i) {
        for(int k = 1; k <= i - 1; k++){
            if(cols[i] == cols[k] || (i - k == Math.abs(cols[i] - cols[k])))
                return false;
        }
        return true;
    }

    static void queens(int i) {
        if(isPromising(i)){
            if(i == n){
                solutionCount++;
                // print cols[1 : n]
            }else{
                for(int j = 1; j <= n; j++){
                    nodeCount++;
                    cols[i + 1] = j;
                    queens(i + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        queens(0);

        // n = 4
        System.out.println("Number of possible solutions: " + solutionCount); // 2
        System.out.println("Number of nodes traversed: " + nodeCount); // 60
    }
}
