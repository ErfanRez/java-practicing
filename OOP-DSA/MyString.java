//*************************************************
// Author: Alireza Nikian
// Faculty of Computer Engineering
// Islamic Azad University, Najafabad Branch
//
// class MyString
//*************************************************
public class MyString { // Immutable Class
    private final char[] value;

    public MyString() {
        value = new char[0];
//        a = null;
//        this("");
    }

    public MyString(String s) {
        value = new char[s.length()];
        for (int i = 0; i < value.length; i++)
            value[i] = s.charAt(i);
    }

    public MyString(char[] charArray) {
        value = new char[charArray.length];
        for (int i = 0; i < value.length; i++)
            value[i] = charArray[i];
    }

    public MyString(long n) { // ONLY in MyString class (NOT in String class)
        this("" + n);
    }

    public MyString(MyString original) { // Copy Constructor (shallow copy)
        value = original.value;
    }

//    public MyString(MyString original) { // Copy Constructor (deep copy)
//        // deep copy is NOT needed
//        a = new char[original.a.length];
//        for (int i = 0; i < a.length; i++)
//            a[i] = original.a[i];
//    }

    public int length() {
        return value.length;
    }

    public boolean isEmpty() {
        return value.length == 0;
    }

    public char charAt(int index) {
        return value[index];
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < value.length; i++)
            s += value[i];
        return s;
    }

    public MyString concat(MyString s) {
        char[] b = new char[value.length + s.value.length];
        for (int i = 0; i < value.length; i++)
            b[i] = value[i];
        for (int i = value.length; i < b.length; i++)
            b[i] = s.value[i - value.length];
        return new MyString(b);
    }

    public MyString concat(String s) {
        return concat(new MyString(s));
    }

//    public MyString concat(String s) {
//        char[] b = new char[a.length + s.length()];
//        for (int i = 0; i < a.length; i++)
//            b[i] = a[i];
//        for (int i = a.length; i < b.length; i++)
//            b[i] = s.charAt(i - a.length);
//        return new MyString(b);
//    }

    public int compareTo(MyString s) {
        int min = Math.min(value.length, s.value.length);
        for (int i = 0; i < min; i++)
            if (value[i] != s.value[i])
                return value[i] - s.value[i];
        return value.length - s.value.length;
    }

    public int compareTo(String s) {
        return compareTo(new MyString(s));
    }

    public MyString toUpperCase() {
        char[] b = new char[value.length];
        for (int i = 0; i < value.length; i++)
            if (value[i] >= 'a' && value[i] <= 'z')
                b[i] = (char) (value[i] + 'A' - 'a');
            else
                b[i] = value[i];
        return new MyString(b);
    }

    public MyString swapCase() { // ONLY in MyString class (NOT in String class)
        char[] b = new char[value.length];
        for (int i = 0; i < value.length; i++) {
            char ch = value[i];
            if (ch >= 'a' && ch <= 'z')
                ch += 'A' - 'a';
            else if (ch >= 'A' && ch <= 'Z')
                ch += 'a' - 'A';
            b[i] = ch;
        }
        return new MyString(b);
    }

    public boolean contains(MyString s) {
        for (int i = 0; i < value.length; i++) {
            if (value[i] == s.value[0]) {
                if (s.value.length == 1)
                    return true;
                for (int j = 1; j < s.value.length && i + j < value.length && value[i + j] == s.value[j]; j++)
                    if (j == s.value.length - 1)
                        return true;
            }
        }
        return false;
    }

    public boolean contains(String s) {
        return contains(new MyString(s));
    }

    public int indexOf(MyString s, int fromIndex) {
        if (fromIndex < 0)
            fromIndex = 0;
        else if (fromIndex >= value.length)
            return -1;
        for (int i = fromIndex; i < value.length; i++) {
            if (value[i] == s.value[0]) {
                if (s.value.length == 1)
                    return i;
                for (int j = 1; j < s.value.length && i + j < value.length && value[i + j] == s.value[j]; j++)
                    if (j == s.value.length - 1)
                        return i;
            }
        }
        return -1;
    }

    public int indexOf(MyString s) {
        return indexOf(s, 0);
    }

    public int indexOf(String s, int fromIndex) {
        return indexOf(new MyString(s), fromIndex);
    }

    public int indexOf(String s) {
        return indexOf(new MyString(s), 0);
    }

    public static MyString valueOf(int n) {
        byte count = 0;
        int temp = n;
        while (n != 0) {
            count++;
            n /= 10;
        }
        n = temp;
        char[] b = new char[count];
        for (int i = b.length - 1; i >= 0; i--) {
            b[i] = (char) (n % 10 + '0');
            n /= 10;
        }
        return new MyString(b);
    }

    //************************************
    //* The following methods are added  *
    //* by Erfan Rezaei                  *
    //************************************

    public MyString subString(int beginIndex) {
        return subString(beginIndex, value.length);
    }

    public MyString subString(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }
        if (endIndex > value.length) {
            endIndex = value.length;
        }
        if (beginIndex > endIndex) {
            throw new IllegalArgumentException("Begin index cannot be greater than end index");
        }
        char[] sub = new char[endIndex - beginIndex];
        for (int i = beginIndex, j = 0; i < endIndex; i++, j++) {
            sub[j] = value[i];
        }
        return new MyString(sub);
    }

    public int lastIndexOf(int ch) {
        for (int i = value.length - 1; i >= 0; i--) {
            if (value[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(int ch, int fromIndex) {
        for (int i = Math.min(fromIndex, value.length - 1); i >= 0; i--) {
            if (value[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(MyString str) {
        for (int i = value.length - str.length(); i >= 0; i--) {
            int j;
            for (j = 0; j < str.length(); j++) {
                if (value[i + j] != str.charAt(j)) {
                    break;
                }
            }
            if (j == str.length()) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(String str) {
        return lastIndexOf(new MyString(str));
    }

    public boolean startsWith(MyString prefix) {
        if (prefix.length() > value.length) {
            return false;
        }
        for (int i = 0; i < prefix.length(); i++) {
            if (value[i] != prefix.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean startsWith(String prefix) {
        return startsWith(new MyString(prefix));
    }

    public boolean endsWith(MyString suffix) {
        if (suffix.length() > value.length) {
            return false;
        }
        for (int i = 0; i < suffix.length(); i++) {
            if (value[value.length - suffix.length() + i] != suffix.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean endsWith(String suffix) {
        return endsWith(new MyString(suffix));
    }

    public static MyString valueOf(double d) {
       String s = Double.toString(d);
       return new MyString(s);
    }

    public char[] toCharArray() {
        //* return this.value;

        char[] result = new char[value.length];
        for (int i = 0; i < value.length; i++) {
            result[i] = value[i];
        }
        return result;
    }


    public MyString reverse() {
        char[] reversed = new char[value.length];
        for (int i = 0; i < value.length; i++) {
            reversed[i] = value[value.length - i - 1];
        }
        return new MyString(reversed);
    }


    public MyString removeDuplicates() {
        char[] result = new char[value.length];
        int index = 0;
        boolean[] seen = new boolean[256]; // ASCII characters
        for (char ch: value) {
            if (!seen[ch]) {
                result[index] = ch;
                index++;
                seen[ch] = true;
            }
        }
        return new MyString(result);
    }



    public MyString repeat(int times) {
        char[] result = new char[value.length * times];
        int index = 0;
        for (int i = 0; i < times; i++) {
            for (char ch: value) {
                result[index] = ch;
                index++;
            }
        }
        return new MyString(result);
    }

    public MyString toLowerCase() {
        char[] result = new char[value.length];
        for (int i = 0; i < value.length; i++) {
            char ch = value[i];
            if (ch >= 'A' && ch <= 'Z') {
                result[i] = (char)(ch + 32); // ch + 'a'-'A'
            } else {
                result[i] = ch;
            }
        }
        return new MyString(result);
    }


    public boolean isUpperCase() {
        for (char ch: value) {
            if (ch < 'A' || ch > 'Z') {
                if(ch == ' '){
                    continue;
                }else{
                    return false;
                }
            }
        }
        return true;
    }


    public boolean isLowerCase() {
        for (char ch: value) {
            if (ch < 'a' || ch > 'z') {
                if(ch == ' '){
                    continue;
                }else{
                    return false;
                }
            }
        }
        return true;
    }


    public int findLongestLength() {
        int maxLength = 0;
        int currentLength = 0;
        for (char ch: value) {
            if (ch == ' ') {
                maxLength = Math.max(maxLength, currentLength);
                currentLength = 0;
            } else {
                currentLength++;
            }
        }
        return Math.max(maxLength, currentLength);
    }


    public boolean hasDuplicate() {
        boolean[] seen = new boolean[256]; // ASCII characters
        for (char ch: value) {
            if (seen[ch]) {
                return true;
            }
            seen[ch] = true;
        }
        return false;
    }


    public MyString trimStart() {
        int start = 0;
        while (start < value.length && value[start] == ' ') {
            start++;
        }

        char[] trimmed = new char[value.length - start];
        for (int i = start, j = 0; i < value.length; i++, j++) {
            trimmed[j] = value[i];
        }
        return new MyString(trimmed);
    }


    public MyString trimEnd() {
        int end = value.length - 1;
        while (end >= 0 && value[end] == ' ') {
            end--;
        }
        char[] trimmed = new char[end + 1];
        for (int i = 0; i <= end; i++) {
            trimmed[i] = value[i];
        }
        return new MyString(trimmed);
    }

    public int compareToIgnoreCase(MyString str) {
        return this.toLowerCase().compareTo(str.toLowerCase());
    }

    public MyString slice(int start, int end) {
        if (start < 0) {
            start = 0;
        }
        if (end > value.length) {
            end = value.length;
        }
        if (start > end) {
            throw new IllegalArgumentException("Start index cannot be greater than end index");
        }
        char[] sliced = new char[end - start];
        for (int i = start, j = 0; i < end; i++, j++) {
            sliced[j] = value[i];
        }
        return new MyString(sliced);
    }

    public MyString padStart(int length, char padChar) {
        if (length <= value.length) {
            return this;
        }
        char[] padded = new char[length];
        int padLength = length - value.length;
        for (int i = 0; i < padLength; i++) {
            padded[i] = padChar;
        }
        for (int i = 0; i < value.length; i++) {
            padded[padLength + i] = value[i];
        }
        return new MyString(padded);
    }

    public MyString padEnd(int length, char padChar) {
        if (length <= value.length) {
            return this;
        }
        char[] padded = new char[length];
        for (int i = 0; i < value.length; i++) {
            padded[i] = value[i];
        }
        for (int i = value.length; i < length; i++) {
            padded[i] = padChar;
        }
        return new MyString(padded);
    }

    public static void main(String[] args) {
        // Testing subString method
        MyString str = new MyString("Hello World");
        System.out.println("subString(6): " + str.subString(6)); // Output: "World"
        System.out.println("subString(0, 5): " + str.subString(0, 5)); // Output: "Hello"

        // Testing lastIndexOf methods
        System.out.println("lastIndexOf('o'): " + str.lastIndexOf('o')); // Output: 7
        System.out.println("lastIndexOf('o', 6): " + str.lastIndexOf('o', 6)); // Output: 4
        MyString subStr = new MyString("llo");
        System.out.println("lastIndexOf('llo'): " + str.lastIndexOf(subStr)); // Output: 2
        System.out.println("lastIndexOf('llo'): " + str.lastIndexOf("llo")); // Output: 2

        // Testing startsWith and endsWith methods
        MyString prefix = new MyString("Hello");
        System.out.println("startsWith('Hello'): " + str.startsWith(prefix)); // Output: true
        System.out.println("startsWith('Hello'): " + str.startsWith("Hello")); // Output: true
        MyString suffix = new MyString("World");
        System.out.println("endsWith('World'): " + str.endsWith(suffix)); // Output: true
        System.out.println("endsWith('World'): " + str.endsWith("World")); // Output: true

        // Testing valueOf(double d) method
        MyString doubleStr = MyString.valueOf(123.456);
        System.out.println("valueOf(123.456): " + doubleStr); // Output: "123.456"

        // Testing toCharArray method
        char[] charArray = str.toCharArray();
        System.out.println("toCharArray: " + new String(charArray)); // Output: "Hello World"

        // Testing reverse method
        MyString reversedStr = str.reverse();
        System.out.println("reverse: " + reversedStr); // Output: "dlroW olleH"

        // Testing removeDuplicates method
        MyString strWithDuplicates = new MyString("aabbcc");
        MyString strWithoutDuplicates = strWithDuplicates.removeDuplicates();
        System.out.println("removeDuplicates: " + strWithoutDuplicates); // Output: "abc"

        // Testing repeat method
        MyString repeatedStr = str.repeat(3);
        System.out.println("repeat: " + repeatedStr); // Output: "Hello WorldHello WorldHello World"

        // Testing toLowerCase method
        MyString upperStr = new MyString("HELLO WORLD");
        MyString lowerStr = upperStr.toLowerCase();
        System.out.println("toLowerCase: " + lowerStr); // Output: "hello world"

        // Testing isUpperCase method
        System.out.println("isUpperCase: " + upperStr.isUpperCase()); // Output: true

        // Testing isLowerCase method
        System.out.println("isLowerCase: " + lowerStr.isLowerCase()); // Output: true

        // Testing findLongestLength method
        MyString sentence = new MyString("The quick brown fox");
        System.out.println("findLongestLength: " + sentence.findLongestLength()); // Output: 5

        // Testing hasDuplicate method
        System.out.println("hasDuplicate: " + strWithDuplicates.hasDuplicate()); // Output: true

        // Testing trimStart method
        MyString strWithSpaces = new MyString("   Hello World");
        MyString trimmedStartStr = strWithSpaces.trimStart();
        System.out.println("trimStart: " + trimmedStartStr); // Output: "Hello World"

        // Testing trimEnd method
        MyString strWithEndSpaces = new MyString("Hello World   ");
        MyString trimmedEndStr = strWithEndSpaces.trimEnd();
        System.out.println("trimEnd: " + trimmedEndStr); // Output: "Hello World"

        
        MyString str1 = new MyString("hello");
        MyString str2 = new MyString("HELLO");
        System.out.println("compareToIgnoreCase: " + str1.compareToIgnoreCase(str2)); // Output: 0

        // Testing slice method
        MyString slicedStr = str.slice(0, 5);
        System.out.println("slice(0, 5): " + slicedStr); // Output: "Hello"

        // Testing padStart method
        MyString paddedStartStr = str.padStart(15, '*');
        System.out.println("padStart(15, '*'): " + paddedStartStr); // Output: "****Hello World"

        // Testing padEnd method
        MyString paddedEndStr = str.padEnd(15, '*');
        System.out.println("padEnd(15, '*'): " + paddedEndStr); // Output: "Hello World****"
    }

}