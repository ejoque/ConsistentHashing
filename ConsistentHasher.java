import java.util.*;

/**
 * Contains the main method to run the consistent hashing simulation. 
 * @author Elizabeth Joque
 * @date 12/8/2025
 */
public class ConsistentHasher {

    /**
     * Runs the program that creates an consistent hash index that the user can update through keyboard input.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        System.out.println("Welcome to the consistent hashing simulator!");
        System.out.println("Please enter the necessary integer arguments: <bucket-size> <ring-size>");
        Scanner keyboard = new Scanner(System.in);
       
        String[] values = new String[2];
        while (keyboard.hasNext()) {
            String arguments = keyboard.nextLine().trim();
            String[] input = arguments.split(" ");
            if (input.length == 2) {
                if (Integer.parseInt(input[0]) > 0 && Integer.parseInt(input[1]) > 0) {
                    // input is good
                    values = input;
                    break;
                }
                else {
                    System.out.println("Both arguments must be greater than 0. Try again: ");
                };
            }
            else {
                System.out.println("Incorrect number of arguments. Try again: ");
            }
        }

        int bucketSize = Integer.parseInt(values[0]);
        int ringSize = Integer.parseInt(values[1]);

        HashRing conHash = new HashRing(ringSize,bucketSize);

        System.out.println("Index created successfully. You can now utilize the following commands:");
        System.out.println("i <x> to insert the key x given as an integer into the index.");
        System.out.println("s <x> to search the index for the key x given as an integer.");
        System.out.println("p to print out the index.");
        System.out.println("q to exit the program");
        System.out.println("Please enter a command:");

        while(keyboard.hasNext()) {
            String[] tokens = keyboard.nextLine().split(" ");

            if (tokens[0].equalsIgnoreCase("q")) {
                break;
            }
            else if (tokens[0].equalsIgnoreCase("i")) {
                // insert the given key
                int newKey = Integer.parseInt(tokens[1]);
                if (conHash.insertKey(newKey)) {
                    System.out.println("SUCCESS");
                }
                else {
                    System.out.println("FAILED");
                }
            }

            else if (tokens[0].equalsIgnoreCase("s")) {
                // search for the given key
                int searchKey = Integer.parseInt(tokens[1]);
                if (conHash.searchIndex(searchKey)) {
                    System.out.println(searchKey + " FOUND");
                }
                else {
                    System.out.println(searchKey + " NOT FOUND");
                }
            }
    
            else if (tokens[0].equalsIgnoreCase("p")) {
                // print it out 
                System.out.println(conHash.printIndex());
            }
            
            else {
                System.out.println("Invalid command. Try again.");
            }

            System.out.println("Please enter a command:");
        }

        System.out.println("Bye! Thanks for hashing around!");
        keyboard.close();

    }
}