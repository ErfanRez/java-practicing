public class MatrixChain {
    public static final String RESET = "\u001B[0m";
    public static final String[] COLORS = {
            "\u001B[31m", // Red
            "\u001B[32m", // Green
            "\u001B[33m", // Yellow
            "\u001B[34m", // Blue
            "\u001B[35m", // Magenta
            "\u001B[36m", // Cyan
            "\u001B[91m", // Bright Red
            "\u001B[92m", // Bright Green
            "\u001B[93m", // Bright Yellow
            "\u001B[94m", // Bright Blue
            "\u001B[95m", // Bright Magenta
            "\u001B[96m"  // Bright Cyan
    };

    private static int[][] M;
    private static int[][] BestK;

    public static void minMult(int n, int[] d) {
        M = new int[n][n];
        BestK = new int[n][n];
        for (int i = 1; i < n; i++) {
            M[i][i] = 0;
        }
        for (int s = 1; s < n - 1; s++) {
            for (int i = 1; i < n - s; i++) {
                int j = i + s;
                int min = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int q = M[i][k] + M[k + 1][j] + d[i - 1] * d[k] * d[j];
                    if (q < min) {
                        min = q;
                        BestK[i][j] = k;
                    }
                }
                M[i][j] = min;
            }
        }

//        System.out.println("Minimum number of multiplications: " + M[1][n - 1]);
        System.out.print("Optimal parenthesization: ");
        printParenthesis(1, n - 1, 0);
        System.out.println(RESET);
    }

    private static void printParenthesis(int i, int j, int colorIndex) {
        String color = COLORS[colorIndex % COLORS.length];
        if (i == j) {
            System.out.print(" " + "A" + i + " ");
            return;
        }

        System.out.print(color + "(" + RESET);
        int k = BestK[i][j];
        printParenthesis(i, k, colorIndex + 1);
        printParenthesis(k + 1, j, colorIndex + 1);
        System.out.print(color + ")" + RESET);
    }

    public static void main(String[] args) {
        int[] d = {13, 5, 89, 3, 34};
        int n = d.length;
        minMult(n, d);
    }
}
