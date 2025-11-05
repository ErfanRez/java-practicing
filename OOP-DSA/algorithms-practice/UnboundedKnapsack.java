import java.util.Arrays;

class UnboundedKnapsack {
    static int n;
    static int[] dp;
    static int[] choice;
    static int[] count;

    static int knapsack(int W, int[] val, int[] wt) {
        int n = wt.length;
        dp = new int[W + 1];
        choice = new int[W + 1];
        Arrays.fill(choice, -1);

        for (int i = 0; i < n; i++) {
            for (int j = wt[i]; j <= W; j++) {
                int newVal = dp[j - wt[i]] + val[i];
                if (newVal > dp[j]) {
                    dp[j] = newVal;
                    choice[j] = i;
                }
            }
        }

        count = new int[n];
        int rem = W;
        while (rem > 0 && choice[rem] != -1) {
            int item = choice[rem];
            count[item]++;
            rem -= wt[item];
        }

        return dp[W];
    }

    public static void main(String[] args) {
        int[] wt  = {1, 2, 5, 6, 7};
        int[] val = {1, 6, 18, 22, 28};
        int W = 11;

        n = wt.length;

        int result = knapsack(W, val, wt);

        System.out.println("Maximum value achievable = " + result);
        System.out.println("Items chosen:");
        for (int i = 0; i < n; i++) {
            if (count[i] > 0) {
                System.out.printf("  - Item %d (weight=%d, value=%d): %d pcs%n",
                        i + 1, wt[i], val[i], count[i]);
            }
        }
    }
}
