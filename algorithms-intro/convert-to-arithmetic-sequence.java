//* we have an integer sequence, we want to turn it to an arithmetic sequence => a1 + a1+d + a1+2d +...
//* (ai = a1 + (i-1)d)  with an specific d=k. if in each operation we could increment or decrement only
//* one number such that ai : 1 <= i <= n, what's the count of operations done on the original sequence?

//* ∑ (1 <= 𝑖 <=n ) => ∣ ( 𝑥 + ( 𝑖 − 1 ) 𝑘 ) − 𝑎 𝑖 ∣

/* Example:

input:
4 3
1 2 3 4

output:
8

*/


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read n and k
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        // Read the array 'a'
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // Initialize val to a large value
        int val = 1000 * 1000 * 1000;

        // Iterate through possible values of x
        for (int x = -100000; x <= 100000; x++) {
            int t = 0;

            // Calculate the sum of absolute differences
            for (int i = 0; i < n; i++) {
                t += Math.abs((x + i * k) - a[i]); //! cause i initialized from 0, we don't need to decrement by 1 as mentioned above
            }

            // Update val if a smaller value is found
            val = Math.min(val, t);
        }

        // Print the result
        System.out.println(val);
    }
}

//! Big-O = O(n(M−m)+n^2*k)