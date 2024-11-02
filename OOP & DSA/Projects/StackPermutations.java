//! The following code is written by Erfan Rezaei
//! Data Structures, Alireza Nikian, Fall 1403
//! Islamic Azad University of Najafabad

import java.util.*;

public class StackPermutations {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number or 0 to quit: ");
        int n = scanner.nextInt();
        while (n != 0){
            System.out.println("Possible permutations for 1 to " + n + " are:");
            List<Integer> numbers = new ArrayList<>();

            for (int i = 1; i <= n; i++) {
                numbers.add(i);
            }

            List<Integer> temp = new ArrayList<>(numbers);


            List<List<Integer>> result = new ArrayList<>();
            permute(numbers, temp, 0, result);


            for (int i = 1; i <= result.size(); i++) {
                List<Integer> permutation = result.get(i - 1);
                System.out.println(i + ": " + permutation);

            }

            System.out.print("Enter a number or 0 to quit: ");
            n = scanner.nextInt();
        }

    }

    private static void permute(List<Integer> original, List<Integer> permutation, int start, List<List<Integer>> result) {
        if (start >= permutation.size() && isValidStackPermutation(original, permutation)) {
            result.add(new ArrayList<>(permutation));
            return;
        }

        for (int i = start; i < permutation.size(); i++) {
            swap(permutation, start, i);
            permute(original,permutation, start + 1, result);
            swap(permutation, start, i);
        }
    }

    private static void swap(List<Integer> permutation, int i, int j) {
        int temp = permutation.get(i);
        permutation.set(i, permutation.get(j));
        permutation.set(j, temp);
    }

    private static boolean isValidStackPermutation(List<Integer> input, List<Integer> output) {
        Stack<Integer> stack = new Stack<>();
        int j = 0;

        for (int num : input) {
            stack.push(num);
            while (!stack.isEmpty() && stack.peek() == output.get(j)) {
                stack.pop();
                j++;
            }
        }

        return stack.isEmpty();
    }
}
