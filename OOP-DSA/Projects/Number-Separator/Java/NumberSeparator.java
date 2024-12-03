//! The following code is written by Erfan Rezaei
//! Data Structures, Alireza Nikian, Fall 2024
//! Islamic Azad University of Najafabad

import java.util.*;

public class NumberSeparator {

    //***************************************************************************************************//
    
    public static String iterativeSeparator(String s) {
        if(s.startsWith("0") || s.isEmpty() || !s.matches("\\d+")) return "Invalid number!";
        if(s.length() <= 3) return  s;

        String result = "";
        int counter = 0;
        int j = s.length();

        for (int i = s.length() - 1; i >= 0; i--) {
            counter++;
            if (counter == 3 && i != 0) {
                result = "," + s.substring(i, j) + result;
                j = i;
                counter = 0;
            } else if (i == 0) {
                result = s.substring(0, j) + result;
            }
        }

        return result;
    }

    //***************************************************************************************************//

    public static String recursiveSeparator(String s){
        if(s.startsWith("0") || s.isEmpty() || !s.matches("\\d+")) return "Invalid number!";

        if(s.length() <= 3) return  s;

        return recursiveSeparator(s.substring(0, s.length() - 3)) + "," + s.substring(s.length() - 3);

    }

    //***************************************************************************************************//

    public static void main(String[] args) {
        System.out.println("********************************");
        System.out.println("Iterative Separator Test Cases:");

        System.out.println("313456789 => " + iterativeSeparator("313456789"));
        System.out.println("21345678 => " + iterativeSeparator("21345678"));
        System.out.println("1000000 => " + iterativeSeparator("1000000"));
        System.out.println("8237 => " + iterativeSeparator("8237"));
        System.out.println("123 => " + iterativeSeparator("123"));
        System.out.println("1 => " + iterativeSeparator("1"));
        System.out.println("12 => " + iterativeSeparator("12"));
        System.out.println("000123456 => " + iterativeSeparator("000123456"));
        System.out.println("empty string! => " + iterativeSeparator(""));
        System.out.println("Erfan => " + iterativeSeparator("Erfan"));

        System.out.println("********************************");
        System.out.println("Recursive Separator Test Cases:");

        System.out.println("313456789 => " + recursiveSeparator("313456789"));
        System.out.println("21345678 => " + recursiveSeparator("21345678"));
        System.out.println("1000000 => " + recursiveSeparator("1000000"));
        System.out.println("8237 => " + recursiveSeparator("8237"));
        System.out.println("123 => " + recursiveSeparator("123"));
        System.out.println("1 => " + recursiveSeparator("1"));
        System.out.println("12 => " + recursiveSeparator("12"));
        System.out.println("000123456 => " + recursiveSeparator("000123456"));
        System.out.println("empty string! => " + recursiveSeparator(""));
        System.out.println("Erfan => " + recursiveSeparator("Erfan"));
    }
}