import java.util.*;

public class BasicArithmeticRegex {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String equation = sc.nextLine().trim();
            boolean isValid = isValid(equation);
            System.out.println(equation + " ==> " + (isValid ? "Valid" : "Invalid"));
        }

        sc.close();
    }

    public static boolean isValid(String s) {
        String pattern = "^(\\d+)([+\\-*/])(\\d+)=(\\d+)$";

        if (!s.matches(pattern)) {
            return false;
        }

        String[] numbers = s.split("[+\\-*/=]");
        String[] operators = s.split("\\d+"); // First element is a white space " ", third element is "="

        int num1 = Integer.parseInt(numbers[0]);
        int num2 = Integer.parseInt(numbers[1]);
        String operator = operators[1];
        int result = Integer.parseInt(numbers[2]);

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
                if (num2 == 0) return false;
                actualResult = num1 / num2;
                break;
            default:
                return false;
        }

        return result == actualResult;
    }
}