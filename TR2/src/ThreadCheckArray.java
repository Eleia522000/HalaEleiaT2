import java.util.ArrayList;
//author:eleia
//This class implements the Runnable interface and performs a recursive subset-sum check. It communicates with SharedData to update the solution
//and maintains thread safety using synchronization.
public class ThreadCheckArray implements Runnable {
    // Local flag to indicate if the target condition is met
    private boolean flag;
    // Array to store the winning state for each element
    private boolean[] winArray;
    // SharedData object to hold shared information between threads
    SharedData sd;
    // Local reference to the array from SharedData
    ArrayList<Integer> array;
    // Local copy of the target value from SharedData
    int b;

    // Constructor that initializes the shared data and local references
    public ThreadCheckArray(SharedData sd) {
        this.sd = sd;
        synchronized (sd) {
            // Copy the shared array and target value
            array = sd.getArray();
            b = sd.getB();
        }
        // Initialize the winArray with the size of the input array
        winArray = new boolean[array.size()];
    }

    // Recursive function to check subsets for the target value
    void rec(int n, int b) {
        synchronized (sd) {
            // If the flag in SharedData is already set, terminate recursion
            if (sd.getFlag())
                return;
        }

        // Base case: when n reaches 1 (single element left to check)
        if (n == 1) {
            // Check if the remaining target value is zero or equals the current array element
            if (b == 0 || b == array.get(n - 1)) {
                flag = true; // Mark success
                synchronized (sd) {
                    sd.setFlag(true); // Update the shared flag
                }
            }
            // Mark this element as part of the solution if it matches the target
            if (b == array.get(n - 1))
                winArray[n - 1] = true;
            return;
        }

        // Recursive call to check the subset including the current element
        rec(n - 1, b - array.get(n - 1));
        if (flag) // If a solution is found, mark this element
            winArray[n - 1] = true;

        synchronized (sd) {
            // Terminate recursion if the shared flag is already set
            if (sd.getFlag())
                return;
        }

        // Recursive call to check the subset excluding the current element
        rec(n - 1, b);
    }

    // The run method implements the logic for each thread
    public void run() {
        // If the array has more than one element
        if (array.size() != 1) {
            // Determine behavior based on thread name
            if (Thread.currentThread().getName().equals("thread1"))
                rec(array.size() - 1, b - array.get(array.size() - 1)); // Include the last element
            else
                rec(array.size() - 1, b); // Exclude the last element
        }

        // Handle the special case for single-element arrays
        if (array.size() == 1) {
            // Check if the single element matches the target value
            if (b == array.get(0) && !flag) {
                winArray[0] = true; // Mark as a solution
                flag = true;
                synchronized (sd) {
                    sd.setFlag(true); // Update the shared flag
                }
            }
        }

        // If a solution is found, update the winArray in SharedData
        if (flag) {
            if (Thread.currentThread().getName().equals("thread1"))
                winArray[array.size() - 1] = true; // Mark the last element for thread1
            synchronized (sd) {
                sd.setWinArray(winArray); // Save the winArray in SharedData
            }
        }
    }
}