//* We want to print out the maximum sum of sub range of n numbers
//* such that 1<l<r<n => al+a(l+1)+a(l+2)+...a(r) = maximum

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int[] a = new int[n];
        
        for(int i = 0; i < n ; i++){
            a[i] = sc.nextInt();
        }

        long max = Integer.MIN_VALUE;
        long currentSum = 0;
        
        for(int i = 0; i < n; i++) {
            currentSum += a[i];
            max = Math.max(currentSum, max);
            
            //! Because we don't want to include negative sums as it's basically like we are subtracting
            //! and as we want the maximum positive sum possible in a sub range ⤵️
            if(currentSum < 0) {
                currentSum = 0;
            }
        }
        
        System.out.println(max);
    }
}
