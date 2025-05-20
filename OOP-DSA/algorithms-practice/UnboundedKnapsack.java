public class UnboundedKnapsack {
    static int knapSack(int W, int[] val, int[] wt) {
        int n = val.length;
        int[] dp = new int[W + 1];
        for (int j = 1; j <= W; j++) {
            for (int i = 0; i < n; i++) {
                if (wt[i] <= j) {
                    dp[j] = Math.max(dp[j], dp[j - wt[i]] + val[i]);
                }
            }
        }
        return dp[W];
    }

    public static void main(String[] args) {
        int[] wt = {2, 3, 5, 6, 7};
        int[] val = {1, 6, 18, 22, 28};
        int W = 11;

        System.out.println("Maximum profit = " + knapSack(W, val, wt));
    }
}
