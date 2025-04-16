import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ArithmeticRegex {
    public static void main(String[] args) {
        File input = new File("input.txt");

        try(Scanner sc = new Scanner(input); FileWriter output = new FileWriter("output.txt")) {

            while (sc.hasNextLine()) {
                String equation = sc.nextLine().trim();
                boolean isValid = isValid(equation);
                output.write(equation + " ==> " + (isValid ? "Valid" : "Invalid"));
                output.write("\n");
            }
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }

    public static boolean isValid(String s) {
        String pattern = "^(\\d+)([+\\-*/])(\\d+)=(\\d+)$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(s);

        if (!matcher.find()) {
            return false;
        }

        try {
            int num1 = Integer.parseInt(matcher.group(1));
            String operator = matcher.group(2);
            int num2 = Integer.parseInt(matcher.group(3));
            int result = Integer.parseInt(matcher.group(4));

            if (operator.equals("-") && num1 < num2) {
                return false;
            }


            int actualResult;
            switch (operator) {
                case "+":
                    actualResult = num1 + num2;
                    break;
                case "-":
                    actualResult = num1 - num2;
                    break;
                case "*":
                    actualResult = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) throw new ArithmeticException("Division by zero");
                    actualResult = num1 / num2;
                    break;
                default:
                    return false;
            }

            return actualResult == result;
        } catch (ArithmeticException e) {
            return false;
        }
    }
}