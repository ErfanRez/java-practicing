import java.util.Scanner;

//! Test cases:

/* 1-input:
    2
    additive-cipher -text "HELP me" -key 1
    additive-cipher -text " HELP me " -key 1

   1-output:
    IFMQ NF
    IFMQ NF
*/

/* 2-input:
    2
    multiplicative-cipher -text "danger" -key 3
    multiplicative-cipher -key 3 -text "danger"

   2-output:
    JANSMZ
    JANSMZ
*/

/* 3-input:
    1
    affine-cipher -text "Hi" -a 3 -b 1

   3-output:
    WZ
*/

/* 4-input:
    1
    mapping-cipher -text "hello" -mapping "zyxwvutsrqponmlkjihgfedcba"

   4-output:
    SVOOL
*/

public class Test {

    private static final char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    public static String additiveCipher(String s, int k){
        String newStr = s.trim().toLowerCase();        
        String temp = "";

        for(int i = 0; i < newStr.length(); i++){
            int index = findIndex(Character.toLowerCase(newStr.charAt(i)));
            if (index != -1) {
                char c = letters[(index + k) % 26];
                temp += c;
            } else {
                temp += newStr.charAt(i);
            }
        }

        return temp.toUpperCase();
    }

    public static String multiplicativeCipher(String s, int k){
        String newStr = s.trim().toLowerCase();
        String temp = "";

        for(int i = 0; i < newStr.length(); i++){
            int index = findIndex(Character.toLowerCase(newStr.charAt(i)));
            if (index != -1) {
                char c = letters[(index * k) % 26];
                temp += c;
            } else {
                temp += newStr.charAt(i);
            }
        }

        return temp.toUpperCase();
    }

    public static String affineCipher(String s, int a, int b){
        String newStr = s.trim().toLowerCase();
        String temp = "";

        for(int i = 0; i < newStr.length(); i++){
            int index = findIndex(Character.toLowerCase(newStr.charAt(i)));
            if (index != -1) {
                char c = letters[((index * a) + b) % 26];
                temp += c;
            } else {
                temp += newStr.charAt(i);
            }
        }

        return temp.toUpperCase();
    }

    public static String mappingCipher(String s, String map){
        String newStr = s.trim().toLowerCase();
        String temp = "";
        
        char[] charMap = map.toCharArray();
        for(int i = 0; i < newStr.length(); i++){
            int index = findIndex(Character.toLowerCase(newStr.charAt(i)));
            if (index != -1) {
                char c = charMap[index];
                temp += c;
            } else {
                temp += newStr.charAt(i);
            }
        }
        
       return temp.toUpperCase();
    }

    public static int findIndex(char target) {
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == target) {
                return i;
            }
        }
        return -1; 
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numCommands = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numCommands; i++) {
            String inputLine = scanner.nextLine();
            String[] tokens = inputLine.split(" -");

            String cipherType = tokens[0].trim();
            String text = "";
            int key = 0;
            int a = 0;
            int b = 0;
            String mapping = "";

            for (String token : tokens) {
                if (token.startsWith("text ")) {
                    text = token.substring(5).replace("\"", "").trim();
                } else if (token.startsWith("key ")) {
                    key = Integer.parseInt(token.substring(4).trim());
                } else if (token.startsWith("a ")) {
                    a = Integer.parseInt(token.substring(2).trim());
                } else if (token.startsWith("b ")) {
                    b = Integer.parseInt(token.substring(2).trim());
                } else if (token.startsWith("mapping ")) {
                    mapping = token.substring(8).replace("\"", "").trim();
                }
            }

            String result = "";
            switch (cipherType) {
                case "additive-cipher":
                    result = additiveCipher(text, key);
                    break;
                case "multiplicative-cipher":
                    result = multiplicativeCipher(text, key);
                    break;
                case "affine-cipher":
                    result = affineCipher(text, a, b);
                    break;
                case "mapping-cipher":
                    result = mappingCipher(text, mapping);
                    break;
            }

            System.out.println(result);
        }
        
        scanner.close();
    }
}
