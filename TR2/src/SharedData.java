import java.util.ArrayList;
//author:eleia
//This class serves as a container for shared data between threads, including an integer array, a target value, a flag indicating success, 
//and an array to track winning states. It provides synchronized access to these shared resources.
public class SharedData {
    // An ArrayList of integers to hold shared data
    private ArrayList<Integer> array;
    // A boolean array to track win states or conditions
    private boolean[] winArray;
    // A boolean flag to represent some state or condition
    private boolean flag;
    // A constant integer value, initialized via the constructor
    private final int b;

    // Constructor to initialize the SharedData object
    // Takes an ArrayList of integers and an integer as parameters
    public SharedData(ArrayList<Integer> array, int b) {
        this.array = array; // Initialize the array with the provided ArrayList
        this.b = b;         // Set the constant value b
    }

    // Getter method for the winArray
    // Returns the boolean array indicating win states
    public boolean[] getWinArray() {
        return winArray;
    }

    // Setter method for the winArray
    // Allows setting the winArray with a provided boolean array
    public void setWinArray(boolean[] winArray) {
        this.winArray = winArray;
    }

    // Getter method for the array
    // Returns the ArrayList of integers
    public ArrayList<Integer> getArray() {
        return array;
    }

    // Getter method for the constant integer b
    // Returns the value of b
    public int getB() {
        return b;
    }

    // Getter method for the flag
    // Returns the boolean flag
    public boolean getFlag() {
        return flag;
    }

    // Setter method for the flag
    // Allows updating the boolean flag
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}