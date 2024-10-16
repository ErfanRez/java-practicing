import java.util.*;

class GFG{

static class Node {
	int data;
	Node next;

	Node(int data)
	{
		this.data = data;
		next = null;
	}
};

// initialize a new head for the linked list
static Node head = null;
static class Linkedlist {
	// insert new value at the start
	void insert(int value)
	{
		Node newNode = new Node(value);
		if (head == null)
			head = newNode;
		else {
			newNode.next = head;
			head = newNode;
		}
	}

	// detect if there is a loop
	// in the linked list
	boolean detectLoop()
	{
		Node slowPointer = head,
			fastPointer = head;

		while (slowPointer != null
			&& fastPointer != null
			&& fastPointer.next != null) {
			slowPointer = slowPointer.next;
			fastPointer = fastPointer.next.next;
			if (slowPointer == fastPointer)
				return true;
		}

	return false;
	}
}

public static void main(String[] args)
{
	Linkedlist l1 = new Linkedlist();
	// inserting new values
	l1.insert(10);
	l1.insert(20);
	l1.insert(30);
	l1.insert(40);
	l1.insert(50);

	// adding a loop for the sake
	// of this example
	Node temp = head;
	while (temp.next != null)
		temp = temp.next;

	temp.next = head;

	// loop added;

	if (l1.detectLoop())
		System.out.print("Loop exists in the"
			+ " Linked List" +"\n");
	else
		System.out.print("Loop does not exists "
			+ "in the Linked List" +"\n");

}
}


//* Sample problem (happy numbers):

class Solution {
    public boolean isHappy(int n) {
        if(n == 1) return true;
        
        int slow = n;
        int fast = n;
//while loop is not used here because initially slow and 
//fast pointer will be equal only, so the loop won't run.
        do {
//slow moving one step ahead and fast moving two steps ahead

            slow = square(slow);
            fast = square(square(fast));
        } while (slow != fast);

//if a cycle exists, then the number is not a happy number
//and slow will have a value other than 1

        return slow == 1;
    }
    
//Finding the square of the digits of a number

    public int square(int num) {
        
        int ans = 0;
        
        while(num > 0) {
            int remainder = num % 10;
            ans += remainder * remainder;
            num /= 10;
        }
        
        return ans;
    }
}