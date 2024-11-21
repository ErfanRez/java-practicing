import java.util.*;

//! This code snippet is written by Erfan Rezaei
//! Advanced Java Programming, Alireza Nikian, Spring 1403
//! Islamic Azad University of Najafabad

//* There is a static main method to test all methods so you can compile and run the file directly
//* or paste your own test cases in the main method body!

public class MyList {
  
  public static <T extends Comparable<T>> void removeMax(ArrayList<T> list){
    if(list.isEmpty()) return;

     T maxEl = list.get(0);
      for(T element : list){
        if(element.compareTo(maxEl) > 0) maxEl = element;
      }

     list.remove(maxEl);
  }

  public static <T extends Comparable<T>> boolean isSorted(ArrayList<T> list){
    if(list.isEmpty()) return false;

    for(int i = 0; i < list.size() - 2; i++){
      if (list.get(i).compareTo(list.get(i+1)) > 0){
        return false;
      }
    }

    return true;

  }

  public static <T extends Comparable<T>> int frequency(ArrayList<T> list, T o){
    if(list.isEmpty()) return 0;

    int count = 0;

    for(T element : list){
      if (element.compareTo(o) == 0) count++;
    }

    return count;
  }

  public static <T> void  removeDuplicates(ArrayList<T> list){
    if(list.isEmpty()) return;

    ArrayList<T> unique= new ArrayList<>();

    for(T element : list){
      if(!unique.contains(element)){
        unique.add(element);
      }
    }

    list.clear();
    list.addAll(unique);

    
  }

  public static <T extends Comparable<T>> void removeAll(ArrayList<T> list, T o) {
    if (list.isEmpty()) return;

    int i = 0;
    while (i < list.size()) {
        T element = list.get(i);
        if (element.compareTo(o) == 0) {
            list.remove(i);
        } else {
            i++;
        }
    }
}


  public static <T> void reverse(ArrayList<T> list){
    if(list.isEmpty()) return;
    
    int left = 0;
    int right = list.size() - 1;

    while ( left < right ){
      T temp = list.get(left);
      list.set(left, list.get(right));
      list.set(right, temp);

      left++;
      right--;
    }
  }
  
  
  public static void change(ArrayList<Integer> list){
    if(list.isEmpty()) return;
    
    for(int i = 0; i < list.size(); i++){
      Integer element = list.get(i);
      if(element % 2 == 0){
        list.set(i, element + 1);
      } else {
        list.set(i, element - 1);
      }
    }
  }

  //* main method: you can use my test cases or paste yours in method body
  
  public static void main(String[] args) {
        ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9, 2, 4, 6, 8, 10));
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4));
        ArrayList<Integer> list3 = new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1));
        ArrayList<String> list4 = new ArrayList<>(Arrays.asList("apple", "banana", "apple", "cherry", "banana", "date"));
        
        System.out.println("Original list1: " + list1); // [1, 3, 5, 7, 9, 2, 4, 6, 8, 10]
        removeMax(list1);
        System.out.println("After removeMax(list1): " + list1); // [1, 3, 5, 7, 9, 2, 4, 6, 8]
        
        System.out.println("Original list2: " + list2); // [1, 2, 2, 3, 3, 3, 4, 4, 4, 4]
        System.out.println("Frequency of 3 in list2: " + frequency(list2, 3)); // 3
        System.out.println("Is list3 sorted? " + isSorted(list3)); // false
        System.out.println("Is list2 sorted? " + isSorted(list2)); // true
        
        System.out.println("Original list4: " + list4); // [apple, banana, apple, cherry, banana, date]
        removeDuplicates(list4);
        System.out.println("After removeDuplicates(list4): " + list4); // [apple, banana, cherry, date]
        
        System.out.println("Original list2: " + list2); // [1, 2, 2, 3, 3, 3, 4, 4, 4, 4]
        removeAll(list2, 3);
        System.out.println("After removeAll(list2, 3): " + list2); // [1, 2, 2, 4, 4, 4, 4]
        
        System.out.println("Original list3: " + list3); // [5, 4, 3, 2, 1]
        reverse(list3);
        System.out.println("After reverse(list3): " + list3); // [1, 2, 3, 4, 5]
        System.out.println("Is list3 sorted? " + isSorted(list3)); // true
        
        System.out.println("Original list1: " + list1); // [1, 3, 5, 7, 9, 2, 4, 6, 8]
        change(list1);
        System.out.println("After change(list1): " + list1); // [0, 4, 4, 6, 8, 3, 5, 7, 9]
    }


}