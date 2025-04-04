//! The following code is written by Erfan Rezaei
//! Data Structures, Alireza Nikian, Fall 2024
//! Islamic Azad University of Najafabad

import java.util.*;

public class RecursiveMath {

    public static int sum(int a, int b) {
        if (b == 0) return a;

        return sum(a + 1, b - 1);
    }

    public static int power(int a, int b) {
        if(b == 1) return a;

        return a * power(a, b - 1);
    }

    private static int multiply(int a, int b) {
        if (b == 1) return a;

        return a + multiply(a, b - 1);
    }

    public static int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        if (a < b) {
            return 0;
        }
        return 1 + divide(a - b, b);
    }

    public static int remainder(int a, int b) {
        if (a < b) return a;

        return remainder(a - b, b);
    }

    public static int bigDigit(int a) {
        if (a <= 0) return Integer.MIN_VALUE;

        int digit = a % 10;

        return Math.max(digit, bigDigit(a / 10));
    }

    public static int digitSum(int a) {
        if (a <= 0) return 0;

        int digit = a % 10;

        return digit + digitSum(a / 10);
    }

    public static int evenDigits(int a) {
        if (a <= 0) return 0;

        int digit = a % 10;

        if (digit % 2 == 0) return 1 + evenDigits(a / 10);

        else return evenDigits(a / 10);
    }

    public static void main(String[] args) {
        // Test sum
        System.out.println("5 + 3 = " + sum(5, 3)); // 8
        System.out.println("10 + 5 = " + sum(10, 5)); // 15

        // Test power
        System.out.println("2 ^ 4 = " + power(2, 4)); // 16
        System.out.println("3 ^ 3 = " + power(3, 3)); // 27

        // Test multiply
        System.out.println("9 * 4 = " + multiply(9, 4)); // 36
        System.out.println("14 * 4 = " + multiply(14, 4)); // 56

        // Test divide
        System.out.println("10 / 3 = " + divide(10, 3)); // 3
        System.out.println("14 / 4 = " + divide(14, 4)); // 3
        System.out.println("27 / 5 = " + divide(27, 5)); // 5
        System.out.println("0 / 5 = " + divide(0, 5)); // 0

        // Test remainder
        System.out.println("10 % 3 = " + remainder(10, 3)); // 1
        System.out.println("14 % 4 = " + remainder(14, 4)); // 2
        System.out.println("27 % 5 = " + remainder(27, 5)); // 2

        // Test bigDigit
        System.out.println("Biggest digit in 562 is " + bigDigit(562)); // 6
        System.out.println("Biggest digit in 7832 is " + bigDigit(7832)); // 8

        // Test digitSum
        System.out.println("Sum of digits in 123 is " + digitSum(123)); // 6
        System.out.println("Sum of digits in 987 is " + digitSum(987)); // 24

        // Test evenDigits
        System.out.println("Count of even digits in 2468 is " + evenDigits(2468)); // 4
        System.out.println("Count of even digits in 1357 is " + evenDigits(1357)); // 0
    }

}