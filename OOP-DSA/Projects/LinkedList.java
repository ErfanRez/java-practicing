//! The following code is written by Erfan Rezaei
//! Data Structures, Alireza Nikian, Fall 1403
//! Islamic Azad University of Najafabad

import java.util.*;

class EmptyListException extends Exception{
    public EmptyListException(){
        super();
    }
    public EmptyListException(String message){
        super(message);
    }
}

class ListNode{ // IntLinkedListNode
    // package visible data fields
    int data;
    ListNode next; // link

    public ListNode(){}

    public ListNode(int data){
        this.data=data;
        this.next=null;
    }
}

public class LinkedList { // IntLinkedList
    private ListNode first;
    private int size;

    public LinkedList() {
        first = null;
        size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    private void checkIndex(int index, int size) {
        if (!(index >= 0 && index < size))
            throw new IndexOutOfBoundsException("index=" + index + " size=" + size);
    }

    public void addLast(int item){
        ListNode temp = new ListNode(item);
        if (first==null)
            first=temp;
        else
        {
            ListNode p = first;;
            while(p.next != null){
                p = p.next;
            }

            p.next=temp;
        }
        size++;
    }
    public void add(int item){
        addLast(item); // add(size,item);
    }

    public int removeFirst() throws EmptyListException{
        if (isEmpty())
            throw new EmptyListException("list is empty");
        int item=first.data;
        first=first.next;
        size--;
        return item;
    }


    public int remove(int index) throws EmptyListException{
        checkIndex(index,size);
//        if (isEmpty())
//            throw new EmptyListException("list is empty");
        if (index==0)
            return removeFirst();
        ListNode p = first;
        for (int i = 1; i < index; i++)
            p=p.next;
        int item = p.next.data;
        p.next = p.next.next;
        size--;
        return item;
    }

    public boolean removeFirstOccurrence(int item) throws EmptyListException {
        if (isEmpty())
            throw new EmptyListException("list is empty");
        ListNode p = first;
        int index = -1;
        for(int i = 0; i < size; i++){
            if(p.data == item){
                index = i;
                break;
            }
            p = p.next;
        }

        if(index == -1 ){
            return false;
        }else {
            remove(index);
            return true;
        }
    }

    public boolean removeLastOccurrence(int item) throws EmptyListException {
        if (isEmpty())
            throw new EmptyListException("list is empty");
        ListNode p = first;
        int index = -1;
        for(int i = 0; i < size; i++){
            if(p.data == item) index = i;
            p = p.next;
        }

        if(index == -1 ){
            return false;
        }else {
            remove(index);
            return true;
        }
    }

    public int findSum() throws EmptyListException {
        if (isEmpty())
            throw new EmptyListException("list is empty");

        ListNode p = first;
        int sum = 0;
        while(p != null){
            sum += p.data;
            p = p.next;
        }

        return sum;
    }

    public boolean equals(LinkedList other){
        if(this.size != other.size) return false;

        if(this.isEmpty() && other.isEmpty()) return true;

        ListNode thisP = this.first;
        ListNode otherP = other.first;

        while(thisP != null && otherP != null){
            if(thisP.data != otherP.data) return false;
            thisP = thisP.next;
            otherP = otherP.next;
        }

        return  true;
    }

    public void printList(){
        for (ListNode p=first; p!=null; p=p.next)
            System.out.print(p.data + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        /* Output:
            Original list:
            10 20 30 40 50 30 60
            Removing first occurrence of 30:
            10 20 40 50 30 60
            Removing last occurrence of 30:
            10 20 40 50 60
            Sum of elements: 180
            Second list:
            10 20 40 50 60
            List1 equals List2? true
            Third list:
            30 40 50 30 60
            List1 equals List3? false
        */

        // Create a linked list
        LinkedList list1 = new LinkedList();
        list1.add(10);
        list1.add(20);
        list1.add(30);
        list1.add(40);
        list1.add(50);
        list1.add(30);
        list1.add(60);

        System.out.println("Original list:");
        list1.printList();

        // Test removeFirstOccurrence
        try {
            System.out.println("Removing first occurrence of 30:");
            list1.removeFirstOccurrence(30);
            list1.printList();
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
        }

        // Test removeLastOccurrence
        try {
            System.out.println("Removing last occurrence of 30:");
            list1.removeLastOccurrence(30);
            list1.printList();
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
        }

        // Test findSum
        try {
            System.out.println("Sum of elements: " + list1.findSum());
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
        }

        // Test equals method
        LinkedList list2 = new LinkedList();
        list2.add(10);
        list2.add(20);
        list2.add(40);
        list2.add(50);
        list2.add(60);

        System.out.println("Second list:");
        list2.printList();

        System.out.println("List1 equals List2? " + list1.equals(list2));

        LinkedList list3 = new LinkedList();
        list3.add(30);
        list3.add(40);
        list3.add(50);
        list3.add(30);
        list3.add(60);

        System.out.println("Third list:");
        list3.printList();

        System.out.println("List1 equals List3? " + list1.equals(list3));
    }

}
