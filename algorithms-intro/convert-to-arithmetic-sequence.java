//* we have an integer sequence a1, a2, a3,..., an, we want to turn it to an arithmetic sequence
//* => a1, a1+k, a1+2k,...,(an = a1 + (n-1)k).
//* if in each operation we could increment or decrement only
//* one number such that ai : 1 <= i <= n, what's the minimum count of operations done on the original sequence?

/* Example:

input:
4 3
1 2 3 4

output:
8

*/


import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int k = input.nextInt();
        
        int[] a = new int[n];
        int[] b = new int[n];
        
        for(int i = 0 ; i < n ; i++){
            a[i] = input.nextInt();
            b[i] = a[i] - i*k; //! Because i didn't initialized from 1 but 0 => bn = an - (n-1)k
        }
        
        Arrays.sort(b);
        
        int M = b[(n-1)/2];
        
        long ans = 0;
        for(int i = 0; i < n; i++){
            ans += Math.abs(b[i] - M);
        }
        
        System.out.println(ans);
    }


//! Big-O = O(n(log(n))




//? For a very large test case like one in the large-testcase folder we should use this approach:

// import java.util.Scanner;
// import java.util.Arrays;
// import java.math.BigInteger;

// public class Main {
//     public static void main(String[] args) {
        
//         Scanner input = new Scanner(System.in);
//         int n = input.nextInt();
//         int k = input.nextInt();
        
//         int[] a = new int[n];
//         BigInteger[] b = new BigInteger[n]; // Change to BigInteger
        
//         for(int i = 0 ; i < n ; i++){
//             a[i] = input.nextInt();
//             BigInteger temp = BigInteger.valueOf(a[i]).subtract(BigInteger.valueOf(i).multiply(BigInteger.valueOf(k)));
//             b[i] = temp; // Using BigInteger for calculation
//         }
        
//         Arrays.sort(b);
        
//         BigInteger M = b[(n-1)/2];
        
//         BigInteger ans = BigInteger.ZERO;
//         for(int i = 0; i < n; i++){
//             ans = ans.add(b[i].subtract(M).abs()); // Using BigInteger methods
//         }
        
//         System.out.println(ans);
//     }
// }
