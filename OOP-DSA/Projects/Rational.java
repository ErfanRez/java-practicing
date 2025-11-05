//! Code by Erfan Rezaei
//! Advanced Java Programming, Alireza Nikian, Spring 2024

import java.util.Scanner;

public class Rational implements Comparable<Rational> {
    private int num;
    private int den;

    public Rational() {
        this.num = 0;
        this.den = 1;
    }

    public Rational(int num) {
        this.num = num;
        this.den = 1;
    }

    public Rational(int num, int den) {
        this.num = num;
        if (den == 0) {
            den = 1; // Avoid division by zero
        }
        this.den = den;
        simplify();
    }

    public Rational(Rational original) {
        this.num = original.num;
        this.den = original.den;
    }

    private void simplify() {
        int gcd = gcd(num, den);
        num /= gcd;
        den /= gcd;
        if (den < 0) { // Denominator should always be positive
            num = -num;
            den = -den;
        }
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a < 0 ? -a : a;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        simplify();
    }

    public int getDen() {
        return den;
    }

    public void setDen(int den) {
        if (den != 0) {
            this.den = den;
            simplify();
        }
    }

    public void setRational(int num, int den) {
        if (den != 0) {
            this.num = num;
            this.den = den;
            simplify();
        }
    }

    public Rational add(Rational other) {
        int newNum = this.num * other.den + other.num * this.den;
        int newDen = this.den * other.den;
        return new Rational(newNum, newDen);
    }

    public Rational subtract(Rational other) {
        int newNum = this.num * other.den - other.num * this.den;
        int newDen = this.den * other.den;
        return new Rational(newNum, newDen);
    }

    public Rational multiply(Rational other) {
        return new Rational(this.num * other.num, this.den * other.den);
    }

    public Rational divide(Rational other) {
        return new Rational(this.num * other.den, this.den * other.num);
    }

    public Rational increment() {
        return add(new Rational(1));
    }

    public Rational decrement() {
        return subtract(new Rational(1));
    }

    public Rational incrementBy(int value) {
        return add(new Rational(value));
    }

    public Rational decrementBy(int value) {
        return subtract(new Rational(value));
    }

    public Rational multiplyBy(int value) {
        return new Rational(this.num * value, this.den);
    }

    public Rational divideBy(int value) {
        return new Rational(this.num, this.den * value);
    }

    public Rational absolute() {
        return new Rational(num < 0 ? -num : num, den < 0 ? -den : den);
    }

    public int signum() {
        if (num == 0)
            return 0;
        return num > 0 ? 1 : -1;
    }

    public Rational negate() {
        return new Rational(-num, den);
    }

    public Rational reciprocal() {
        return new Rational(den, num);
    }

    public Rational invert() {
        int temp = this.num;
        this.num = this.den;
        this.den = temp;

        return this;
    }

    public Rational power(int exponent) {
        int resultNum = 1;
        int resultDen = 1;
        for (int i = 0; i < exponent; i++) {
            resultNum *= num;
            resultDen *= den;
        }
        return new Rational(resultNum, resultDen);
    }

    public int intValue() {
        return num / den;
    }

    public long longValue() {
        return (long) num / den;
    }

    public float floatValue() {
        return (float) num / den;
    }

    public double doubleValue() {
        return (double) num / den;
    }

    public void printRational() {
        System.out.println(num + "/" + den);
    }

    public String toString() {
        return num + "/" + den;
    }

    public void showRational() {
        System.out.println(toString());
    }

    public boolean isZero() {
        return num == 0;
    }

    public boolean isProper() {
        return (num < den) && (num > 0);
    }

    public String toMixed() {
        int wholePart = num / den;
        int remainder = num % den;
        return wholePart + " " + remainder + "/" + den;
    }

    public int floor() {
        return num / den;
    }

    public int ceil() {
        return (num % den == 0) ? num / den : num / den + 1;
    }

    public boolean equals(Rational other) {
        return this.num == other.num && this.den == other.den;
    }

    public int compareTo(Rational other) {
        int thisNum = this.num * other.den;
        int otherNum = other.num * this.den;

        return Integer.compare(thisNum, otherNum);
    }

    public Rational findMax(Rational other) {
        return (compareTo(other) >= 0) ? this : other;
    }

    public Rational findMin(Rational other) {
        return (compareTo(other) <= 0) ? this : other;
    }

    public static Rational readRational() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter numerator: ");
        int num = scanner.nextInt();
        System.out.print("Enter denominator: ");
        int den = scanner.nextInt();
        return new Rational(num, den);
    }

    public static Rational createRational(int num, int den) {
        return new Rational(num, den);
    }

    public static Rational valueOf(String s) {
        String[] parts = s.split("/");
        int num = Integer.parseInt(parts[0]);
        int den = Integer.parseInt(parts[1]);
        return new Rational(num, den);
    }

    public static Rational valueOf(double value) {
        int scale = 1;
        while ((int) value != value) {
            value *= 10;
            scale *= 10;
        }
        return new Rational((int) value, scale);
    }

    public static void main(String[] args) {
        // Test constructors
        Rational r1 = new Rational(); // 0/1
        System.out.println(r1); // Expected output: 0/1

        Rational r2 = new Rational(5); // 5/1
        System.out.println(r2); // Expected output: 5/1

        Rational r3 = new Rational(3, 4); // 3/4
        System.out.println(r3); // Expected output: 3/4

        Rational r4 = new Rational(6, -8); // -3/4 (simplified)
        System.out.println(r4); // Expected output: -3/4

        Rational r5 = new Rational(r3); // 3/4 (copy constructor)
        System.out.println(r5); // Expected output: 3/4

        // Test methods
        Rational r6 = new Rational(1, 2);
        Rational r7 = new Rational(1, 3);

        // Add
        Rational sum = r6.add(r7); // 5/6
        System.out.println(sum); // Expected output: 5/6

        // Subtract
        Rational difference = r6.subtract(r7); // 1/6
        System.out.println(difference); // Expected output: 1/6

        // Multiply
        Rational product = r6.multiply(r7); // 1/6
        System.out.println(product); // Expected output: 1/6

        // Divide
        Rational quotient = r6.divide(r7); // 3/2
        System.out.println(quotient); // Expected output: 3/2

        // Increment and Decrement
        Rational incremented = r6.increment(); // 3/2
        System.out.println(incremented); // Expected output: 3/2

        Rational decremented = r6.decrement(); // -1/2
        System.out.println(decremented); // Expected output: -1/2

        // Increment and Decrement by value
        Rational incrementByValue = r6.incrementBy(3); // 7/2
        System.out.println(incrementByValue); // Expected output: 7/2

        Rational decrementByValue = r6.decrementBy(1); // 1/2
        System.out.println(decrementByValue); // Expected output: -1/2

        // Multiply and Divide by value
        Rational multipliedByValue = r6.multiplyBy(2); // 1/1
        System.out.println(multipliedByValue); // Expected output: 1/1

        Rational dividedByValue = r6.divideBy(2); // 1/4
        System.out.println(dividedByValue); // Expected output: 1/4

        // Absolute
        Rational absoluteR6 = r6.absolute(); // 1/2
        System.out.println(absoluteR6); // Expected output: 1/2

        // Signum
        System.out.println(r6.signum()); // Expected output: 1
        System.out.println(new Rational(-3, 4).signum()); // Expected output: -1
        System.out.println(new Rational(0).signum()); // Expected output: 0

        // Negate
        Rational negated = r6.negate(); // -1/2
        System.out.println(negated); // Expected output: -1/2

        // Reciprocal
        Rational reciprocalR6 = r6.reciprocal(); // 2/1
        System.out.println(reciprocalR6); // Expected output: 2/1

        // Invert
        Rational invertedR6 = r6.invert(); // 2/1
        System.out.println(invertedR6); // Expected output: 2/1

        r6 = new Rational(1, 2);

        // Power
        Rational powered = r6.power(3); // 1/8
        System.out.println(powered); // Expected output: 1/8

        // Int and Long values
        System.out.println(r6.intValue()); // Expected output: 0
        System.out.println(r6.longValue()); // Expected output: 0

        // Float and Double values
        System.out.println(r6.floatValue()); // Expected output: 0.5
        System.out.println(r6.doubleValue()); // Expected output: 0.5

        // Print rational
        r6.printRational(); // Expected output: 1/2

        // Check properties
        System.out.println(r6.isZero()); // Expected output: false
        System.out.println(r6.isProper()); // Expected output: true

        // Mixed representation
        System.out.println(new Rational(7, 4).toMixed()); // Expected output: 1 3/4

        // Floor and Ceiling
        System.out.println(r6.floor()); // Expected output: 0
        System.out.println(r6.ceil()); // Expected output: 1

        // Equals
        System.out.println(r6.equals(new Rational(1, 2))); // Expected output: true
        System.out.println(r6.equals(new Rational(2, 4))); // Expected output: true

        // CompareTo
        System.out.println(r6.compareTo(r7)); // Expected output: 1
        System.out.println(r7.compareTo(r6)); // Expected output: -1
        System.out.println(r6.compareTo(new Rational(1, 2))); // Expected output: 0

        // Find max and min
        System.out.println(r6.findMax(r7)); // Expected output: 1/2
        System.out.println(r6.findMin(r7)); // Expected output: 1/3

        // Read Rational
        // Uncomment the following line to test input
        // Rational inputRational = Rational.readRational(); // Will prompt for input

        // Create Rational
        Rational created = Rational.createRational(3, 5);
        System.out.println(created); // Expected output: 3/5

        // ValueOf from String
        Rational valueFromString = Rational.valueOf("4/5");
        System.out.println(valueFromString); // Expected output: 4/5

        // ValueOf from double
        Rational valueFromDouble = Rational.valueOf(2.5);
        System.out.println(valueFromDouble); // Expected output: 5/2
    }

}