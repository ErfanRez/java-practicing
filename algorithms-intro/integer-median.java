//* We get an integer sequence from user. we want to convert all ai such that 1 <= i <= n, to a number M
//* called the Median. find out and output the Median and the minimum operations needed to convert all ai to M 

/* Example:

input:
5
1 2 3 4 5

output:
3 6

*/


import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the number of elements
        int n = scanner.nextInt();

        // Read the array elements
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // Sort the array
        Arrays.sort(a);

        // Calculate the median
        int M = a[(n - 1) / 2];

        // Calculate the sum of absolute deviations
        //? due to integer type range limit, we might face overflowing, so we better use "long" for very large test cases
        
        /* Example:
        input: 
        10
        942198004 216678442 271696411 676324589 395898830 455221447 527896566 460424922 961835161 853491810

        output:
        460424922 2161826078
        */

        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.abs((long)a[i] - M);
        }

        // Print the median and the sum of deviations
        System.out.println(M + " " + ans);
    }
}



//! Big-O = O(n.log(n))