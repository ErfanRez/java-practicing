import java.util.*;

public class Q1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        
        String result = findNthString(n);
        System.out.println(result.charAt(result.length() - 1));
    }

    private static String findNthString(int n) {
      
        Queue<String> queue = new LinkedList<>();
        queue.add("a");
        queue.add("b");

        int count = 0;
        String current = "";

        while (count < n) {
            current = queue.poll();
            count++;
            queue.add(current + "a");
            queue.add(current + "b");
        }

        return current;
    }
}
