import java.util.Scanner;

public class Q1 {
    public static void main(String args[]) {
      
      Scanner input = new Scanner(System.in);
      int k, n;

      k = input.nextInt();
      n = input.nextInt();
      int[] a = new int[n];
      
      for (int i = 0 ; i < n ; i++) {
          a[i] = input.nextInt();
      }
      
      int sum = 0;
      
      for (int i = 0 ; i < n ; i++) {
             if (i == 0) {
                 if (a[i] <= k) {
              sum += 2;
          }else {
              int temp = a[i];
                  if ((temp % k) == 0) {
                      sum += (int)(temp / k) + 1;
                  } else {
                      sum += (int) (temp / k) + 2;
                  }
          }
                  
                  
              } else{
                  int temp = Math.abs(a[i] - a[i -1]);
                  if (a[i] <= k) {
                      
              sum += (temp / k) + 2;
          } else {
                  if ((temp % k) == 0) {
                      sum += (int)(temp / k) + 1;
                  } else {
                      sum += (int)(temp / k) + 2;
                  }
          }
                 
              }
      }
      
                if ((a[n-1] % k) == 0 ){
                      sum += a[n-1] / k;
                  } else {
                      sum += a[n-1]/k + 1;
                  }
      
      System.out.println(sum);
    }
}