import java.util.ArrayList;
import java.util.Scanner;
//author:hala
//This is the main class that initializes the shared data and spawns two threads to perform a subset-sum check on the array.
//It manages user input, thread coordination, and displays the results.
public class TestThreadCheckArray {
    public static void main(String[] args) {
        // Try-with-resources statement to automatically close the Scanner
        try (Scanner input = new Scanner(System.in)) {
            // Declare two threads
            Thread thread1, thread2;

            // Prompt the user to enter the size of the array
            System.out.println("Enter array size");
            int num = input.nextInt();
            // Initialize an ArrayList to store the array elements
            ArrayList<Integer> array = new ArrayList<>();

            // Prompt the user to input numbers for the array
            System.out.println("Enter numbers for array");
            for (int index = 0; index < num; index++) 
                array.add(input.nextInt()); // Add each entered number to the array

            // Prompt the user to enter the target number
            System.out.println("Enter number");
            num = input.nextInt();

            // Create a SharedData object to store the array and the target number
            SharedData sd = new SharedData(array, num);

            // Create two threads, each running an instance of ThreadCheckArray
            thread1 = new Thread(new ThreadCheckArray(sd), "thread1");
            thread2 = new Thread(new ThreadCheckArray(sd), "thread2");
            // Start both threads
            thread1.start();
            thread2.start();

            // Wait for both threads to complete execution
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                // Handle exceptions related to thread interruptions
                e.printStackTrace();
            }

            // Check the flag in the shared data to determine success
            if (!sd.getFlag()) {
                System.out.println("Sorry");
                return; // Exit the program if the flag indicates failure
            }

            // Display the results
            System.out.println("Solution for b: " + sd.getB() + ", n = " + sd.getArray().size());

            // Print the array indices
            System.out.print("I:    ");
            for (int index = 0; index < sd.getArray().size(); index++)
                System.out.print(index + "    ");
            System.out.println();

            // Print the array values, ensuring proper alignment
            System.out.print("A:    ");
            for (int value : sd.getArray()) {
                System.out.print(value);
                int counter = 5; // Set initial alignment space
                while (true) {
                    value = value / 10; // Reduce the value by a factor of 10
                    counter--;         // Decrease alignment space
                    if (value == 0)
                        break; // Break loop if the value becomes zero
                }
                // Add spaces for alignment
                for (int i = 0; i < counter; i++)
                    System.out.print(" ");
            }

            System.out.println();

            // Print the winArray values (C array) as 1 for true and 0 for false
            System.out.print("C:    ");
            for (boolean index : sd.getWinArray()) {
                if (index)
                    System.out.print("1    "); // Print 1 for true
                else
                    System.out.print("0    "); // Print 0 for false
            }
        }
    }
}