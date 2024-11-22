//* We have an integer sequences A such that ai : 1<i<n.
//* Find and print count of numbers in A that are less than each number entered by user.

/* Example:

input:
2 3
1 2
1
2
3

output:
0
1
2

*/


import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        int n = input.nextInt();
        int q = input.nextInt();
        int[] a = new int[n];
        
        for (int i = 0; i < n; i++) {
            a[i] = input.nextInt();
        }

        int M = a[0];
        for (int i = 1; i < n; i++) {
            M = Math.max(M, a[i]);
        }

        int[] cnt = new int[M + 1];
        
        for (int i = 0; i < n; i++) {
            cnt[a[i]]++;
        }

        int[] ps = new int[M+1];
        
        for (int i = 1; i < M+1; i++) {
            ps[i] = ps[i - 1] + cnt[i];
        }

        for (int i = 0; i < q; i++) {
            int x = input.nextInt();
            
            if (x > M) {
                System.out.println(n);
            } else {
                System.out.println(ps[x - 1]);
            }
        }
    }
}

//! Big-O = O(n)
