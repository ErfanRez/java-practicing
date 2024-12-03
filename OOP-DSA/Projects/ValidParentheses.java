//! The following code is written by Erfan Rezaei
//! Data Structures, Alireza Nikian, Fall 2024
//! Islamic Azad University of Najafabad



import java.util.*;

public class validParentheses {
  
    public static String validParentheses(String s){
      if(s.length() == 0) return "Unbalanced";
      
      Stack<Character> stack = new Stack<>();
      
      
      for(char c : s.toCharArray()){
        
        if(c == '(' || c == '{' || c == '['){
          stack.push(c);
          
        } else{
          
          if(stack.isEmpty()) return "Unbalanced";
          
          char top = stack.peek();
          
          if(
              (c == ')' && top == '(') ||
              (c == ']' && top == '[') ||
              (c == '}' && top == '{')
              
            ) stack.pop();
          
          else return "Unbalanced";  
        }
        
      }
      
      return stack.isEmpty() ? "Balanced" : "Unbalanced";
    }
  
    public static void main(String[] args) {
        System.out.println(validParentheses("((())"));         // Unbalanced
        System.out.println(validParentheses("())(()"));        // Unbalanced
        System.out.println(validParentheses("(()))()"));       // Unbalanced
        System.out.println(validParentheses("(()())"));        // Balanced
        System.out.println(validParentheses(")(("));           // Unbalanced
        System.out.println(validParentheses("[()]"));          // Balanced
        System.out.println(validParentheses("[(])"));          // Unbalanced
        System.out.println(validParentheses("[()()]"));        // Balanced
        System.out.println(validParentheses("{[({})[]]}"));    // Balanced
        System.out.println(validParentheses("[()]{[()]()}"));  // Balanced
        System.out.println(validParentheses("{()}[]"));        // Balanced
        System.out.println(validParentheses("{(})[]"));        // Unbalanced
        System.out.println(validParentheses("{()[]}"));        // Balanced
        System.out.println(validParentheses("{})([]"));        // Unbalanced
        System.out.println(validParentheses("({{}})[]()"));    // Balanced
        System.out.println(validParentheses("()(()){([()])}")); // Balanced
        System.out.println(validParentheses("((()(()){([()])}))")); // Unbalanced
        System.out.println(validParentheses(")(()){([()])}")); // Unbalanced
        System.out.println(validParentheses("({[])})"));       // Unbalanced
        System.out.println(validParentheses("("));             // Unbalanced
        System.out.println(validParentheses("{[()()]()}"));    // Balanced
        System.out.println(validParentheses("{[()]}"));        // Balanced
        System.out.println(validParentheses("{[(])}"));        // Unbalanced
        System.out.println(validParentheses("[()]};"));        // Unbalanced
        System.out.println(validParentheses("{[()]}"));        // Balanced
        System.out.println(validParentheses("}("));            // Unbalanced
        System.out.println(validParentheses("[[["));           // Unbalanced
        System.out.println(validParentheses("[)]"));           // Unbalanced
    }
}