public class Rational {
    private int num;
    private int den;

    // Constructors
    public Rational() {
        this.num = 0;
        this.den = 1;
    }

    public Rational(int num) {
        this.num = num;
        this.den = 1;
    }

    public Rational(int num, int den) {
        if (den == 0) throw new ArithmeticException("Denominator cannot be zero.");
        this.num = num;
        this.den = den;
        simplify();
    }

    public Rational(Rational original) {
        this.num = original.num;
        this.den = original.den;
    }

    // Private method to simplify the fraction
    private void simplify() {
        int gcd = gcd(num, den);
        num /= gcd;
        den /= gcd;
        if (den < 0) { // Ensure the denominator is positive
            num = -num;
            den = -den;
        }
    }

    // GCD helper function
    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // Getters and Setters
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
        if (den == 0) throw new ArithmeticException("Denominator cannot be zero.");
        this.den = den;
        simplify();
    }

    public void setRational(int num, int den) {
        if (den == 0) throw new ArithmeticException("Denominator cannot be zero.");
        this.num = num;
        this.den = den;
        simplify();
    }

    // Arithmetic Operations
    public Rational add(Rational other) {
        return new Rational(this.num * other.den + other.num * this.den, this.den * other.den);
    }

    public Rational subtract(Rational other) {
        return new Rational(this.num * other.den - other.num * this.den, this.den * other.den);
    }

    public Rational multiply(Rational other) {
        return new Rational(this.num * other.num, this.den * other.den);
    }

    public Rational divide(Rational other) {
        if (other.num == 0) throw new ArithmeticException("Cannot divide by zero.");
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
        if (value == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Rational(this.num, this.den * value);
    }

    public Rational absolute() {
        return new Rational(Math.abs(num), den);
    }

    public int signum() {
        return Integer.compare(num, 0);
    }

    public Rational negate() {
        return new Rational(-num, den);
    }

    public Rational reciprocal() {
        if (num == 0) throw new ArithmeticException("Cannot find reciprocal of zero.");
        return new Rational(den, num);
    }

    public Rational invert() {
        return reciprocal();
    }

    public Rational power(int exponent) {
        return new Rational((int) Math.pow(num, exponent), (int) Math.pow(den, exponent));
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
        System.out.println(toString());
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
        return Math.abs(num) < den;
    }

    public String toMixed() {
        int whole = num / den;
        int remainder = num % den;
        if (remainder == 0) {
            return String.valueOf(whole);
        } else {
            return whole + " " + Math.abs(remainder) + "/" + den;
        }
    }

    public int floor() {
        return (int) Math.floor((double) num / den);
    }

    public int ceil() {
        return (int) Math.ceil((double) num / den);
    }

    public boolean equals(Rational other) {
        return this.num == other.num && this.den == other.den;
    }

    public int compareTo(Rational other) {
        return Integer.compare(this.num * other.den, other.num * this.den);
    }

    public Rational findMax(Rational other) {
        return this.compareTo(other) >= 0 ? this : other;
    }

    public Rational findMin(Rational other) {
        return this.compareTo(other) <= 0 ? this : other;
    }

    // Factory Methods
    public static Rational readRational() {
        // Implement user input-based method if required
        return new Rational(); // Placeholder
    }

    public static Rational createRational(int num, int den) {
        return new Rational(num, den);
    }

    public static Rational valueOf(String s) {
        String[] parts = s.split("/");
        int num = Integer.parseInt(parts[0]);
        int den = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;
        return new Rational(num, den);
    }

    public static Rational valueOf(double value) {
        int den = 1000000; // Precision up to 6 decimal places
        int num = (int) (value * den);
        return new Rational(num, den);
    }

    // Main method to test the class
    public static void main(String[] args) {
        Rational r1 = new Rational(); // 0/1
        System.out.println(r1); // Expected output: 0/1

        Rational r2 = new Rational(3); // 3/1
        System.out.println(r2); // Expected output: 3/1

        Rational r3 = new Rational(4, 8); // 1/2 (simplified)
        System.out.println(r3); // Expected output: 1/2

        Rational r4 = new Rational(r3); // Copy of r3
        System.out.println(r4); // Expected output: 1/2

        r4.setNum(5); // Set numerator to 5
        System.out.println(r4); // Expected output: 5/2

        r4.setDen(10); // Set denominator to 10, should simplify to 1/2
        System.out.println(r4); // Expected output: 1/2

        r4.setRational(7, 14); // Set new values, should simplify to 1/2
        System.out.println(r4); // Expected output: 1/2

        Rational r5 = r3.add(r2); // 1/2 + 3/1 = 7/2
        System.out.println(r5); // Expected output: 7/2

        Rational r6 = r5.subtract(new Rational(1, 2)); // 7/2 - 1/2 = 6/2 = 3/1
        System.out.println(r6); // Expected output: 3/1

        Rational r7 = r6.multiply(new Rational(2, 3)); // 3/1 * 2/3 = 6/3 = 2/1
        System.out.println(r7); // Expected output: 2/1

        Rational r8 = r7.divide(new Rational(1, 2)); // 2/1 ÷ 1/2 = 4/1
        System.out.println(r8); // Expected output: 4/1

        Rational r9 = r8.increment(); // 4/1 + 1/1 = 5/1
        System.out.println(r9); // Expected output: 5/1

        Rational r10 = r9.decrement(); // 5/1 - 1/1 = 4/1
        System.out.println(r10); // Expected output: 4/1

        Rational r11 = r10.incrementBy(3); // 4/1 + 3/1 = 7/1
        System.out.println(r11); // Expected output: 7/1

        Rational r12 = r11.decrementBy(2); // 7/1 - 2/1 = 5/1
        System.out.println(r12); // Expected output: 5/1

        Rational r13 = r12.multiplyBy(3); // 5/1 * 3 = 15/1
        System.out.println(r13); // Expected output: 15/1

        Rational r14 = r13.divideBy(5); // 15/1 ÷ 5 = 3/1
        System.out.println(r14); // Expected output: 3/1

        Rational r15 = r14.absolute(); // |3/1| = 3/1
        System.out.println(r15); // Expected output: 3/1

        int signum = r15.signum(); // Signum of 3/1 is 1
        System.out.println(signum); // Expected output: 1

        Rational r16 = r15.negate(); // Negate 3/1 = -3/1
        System.out.println(r16); // Expected output: -3/1

        Rational r17 = r16.reciprocal(); // Reciprocal of -3/1 = -1/3
        System.out.println(r17); // Expected output: -1/3

        Rational r18 = r17.invert(); // Invert -1/3 = -3/1
        System.out.println(r18); // Expected output: -3/1

        Rational r19 = r18.power(2); // (-3/1)^2 = 9/1
        System.out.println(r19); // Expected output: 9/1

        int intValue = r19.intValue(); // int value of 9/1 = 9
        System.out.println(intValue); // Expected output: 9

        long longValue = r19.longValue(); // long value of 9/1 = 9
        System.out.println(longValue); // Expected output: 9

        float floatValue = r19.floatValue(); // float value of 9/1 = 9.0
        System.out.println(floatValue); // Expected output: 9.0

        double doubleValue = r19.doubleValue(); // double value of 9/1 = 9.0
        System.out.println(doubleValue); // Expected output: 9.0

        r19.printRational(); // Print 9/1
        // Expected output: 9/1

        boolean isZero = r19.isZero(); // 9/1 is not zero
        System.out.println(isZero); // Expected output: false

        boolean isProper = r19.isProper(); // 9/1 is not proper
        System.out.println(isProper); // Expected output: false

        String mixed = r19.toMixed(); // 9/1 as mixed = 9
        System.out.println(mixed); // Expected output: 9

        int floor = r19.floor(); // Floor of 9/1 = 9
        System.out.println(floor); // Expected output: 9

        int ceil = r19.ceil(); // Ceil of 9/1 = 9
        System.out.println(ceil); // Expected output: 9

        boolean equals = r19.equals(new Rational(9)); // 9/1 equals 9/1
        System.out.println(equals); // Expected output: true

        int compareTo = r19.compareTo(new Rational(10)); // Compare 9/1 to 10/1 = -1
        System.out.println(compareTo); // Expected output: -1

        Rational max = r19.findMax(new Rational(10)); // Max of 9/1 and 10/1 = 10/1
        System.out.println(max); // Expected output: 10/1

        Rational min = r19.findMin(new Rational(10)); // Min of 9/1 and 10/1 = 9/1
        System.out.println(min); // Expected output: 9/1

        Rational r20 = Rational.valueOf("7/3"); // Create Rational from "7/3"
        System.out.println(r20); // Expected output: 7/3

        Rational r21 = Rational.valueOf(0.25); // Create Rational from 0.25 = 1/4
        System.out.println(r21); // Expected output: 1/4
    }
}
