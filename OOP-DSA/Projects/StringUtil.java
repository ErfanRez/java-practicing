//! This code snippet is written by Erfan Rezaei
//! Advanced Java Programming, Alireza Nikian, Spring 2024
//! Islamic Azad University of Najafabad

//* There is a static main method to test all constructors and methods so you can compile and run the file directly
//* or paste your own test cases in the main method body!

public class StringUtil {

    public static String repeat(String str, int num) {
        String repeat = "";
        for (int i = 0; i < num; i++) {
            repeat += str;
        }
        return repeat;
    }

    public static String trimStart(String str) {
        String trimmed = "";
        int lastCharIndex = 0;
        for(int i = 0; i < str.length(); i++){
         char c = str.charAt(i);
            if( (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')){
                trimmed += c;
                lastCharIndex = i;
            } else {
                continue;
            }
        }

        for(int i = lastCharIndex + 1 ; i < str.length(); i++){
            trimmed += str.charAt(i);
        } //Optimized: trimmed += str.substring(lastCharIndex + 1);

        return trimmed;
    
    //! Clean Code:
        // return str.replaceAll("^\\s+", ""); //Using REGEX :)
    }

    public static boolean isUpperCase(String str) {
        String newStr = str.replaceAll(" ", "");
        for (int i = 0; i < newStr.length(); i++) { // for (char c : newStr.toCharArray())...
            char c = newStr.charAt(i);
            if (c < 'A' || c > 'Z') return false;
        }
            return true;
    }

    public static boolean isPalindrome(String str) {
        String cleanedStr = str.replaceAll(" ", "").toLowerCase(); // for multi and single words in a line
        String reversedStr = reverseString(cleanedStr);
        return cleanedStr.equals(reversedStr);
    }

    public static String reverseString(String str) {
        String rev = "";
        for (int i = str.length()-1 ; i >= 0 ; i-- ) rev += str.charAt(i);
        return rev;
    }

    public static int findLongestLength(String str) {
        String[] words = str.split(" ");
        int max = 0;
        for (int i = 0; i < words.length; i++) {
            max = words[i].length() > max ? words[i].length() : max;
        }
        
        return max;
    
    //! Clean Code:
        // String[] words = str.split(" ");
        // int max = 0;
        // for (String word : words) {
        //     max = Math.max(max, word.length());
        // }
        // return max;
    }

    public static boolean hasDuplicate(String str) {
    //! Brute Force:    
        String res = "" + str.charAt(0);

        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);

            for (int j = 0; j < res.length(); j++) {
                if (c == res.charAt(j)) {
                    return true;
                }
            }

            res += c;
        }
       
        return false;

        
    //! Optimized:
        // str = str.toLowerCase();
        // boolean[] charSet = new boolean[256];
        // for (char c : str.toCharArray()) {
        //     if (charSet[c]) {
        //         return true;
        //     }
        //     charSet[c] = true;
        // }
        // return false;
    }

     //! case sensitive
     public static String removeDuplicate(String str) {
        
        //! Brute Force:    
        String res = "" + str.charAt(0);

        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            boolean found = false;

            for (int j = 0; j < res.length(); j++) {
                if (c == res.charAt(j)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                res += c;
            }
        }
       
        return res;
        
    //! Optimized:
        // String result = "";
        // for (int i = 0 ; i < str.length() ; i++) {
        //     String c = str.charAt(i) + "";
        //     if (result.contains(c)) continue;
            
        //     result += c;
        // }
        
        // return result;
    }


    //* main method: you can use my test cases or paste yours in function body
    
    public static void main(String[] args) {
        System.out.println(repeat("hello", 3)); // "hellohellohello"
        System.out.println(trimStart("   hello")); // "hello"
        System.out.println(trimStart("   Erfan     ")); // "Erfan     "
        System.out.println(isUpperCase("HELLO HI HOW")); // true
        System.out.println(isUpperCase("Hello")); // false
        System.out.println(isPalindrome("A man a plan a canal Panama")); // true
        System.out.println(isPalindrome("hello")); // false
        System.out.println(findLongestLength("The quick brown fox")); // 5
        System.out.println(findLongestLength("hello")); // 5
        System.out.println(hasDuplicate("Mississipi")); // true
        System.out.println(hasDuplicate("helo")); // false
        System.out.println(removeDuplicate("hello")); // "helo"
        System.out.println(removeDuplicate("aabbcc")); // "abc"
    }
}
