package actividad.pkg2;

/*
 * Algorithms and Complexity                                August 24, 2022
 * IST 4310_01
 * Prof. M. Diaz-Maldonado
 * Estudiante: Alan Daniel Florez Cerro
 * Código: 200148604
 * Workshop 3: Counting Duplicates in a Plain Text File (cases)
 *
 * Synopsis:
 * Analisys of best, worst and average cases for a previous algorithm that finds
 * the number of duplicates in plain text files storing N random integers
 * each integer within a specified range, the amount of duplicates of each number is stored
 * in an array, using 16 samples to make a graphic for each case, the result of a sample is stored
 * in a file called times.txt that will be used to plot the data gathered for comparison
 *
 *
 * Copyright (c) 2022 Misael Diaz-Maldonado
 * This file is released under the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *
 * References:
 * [0] Files: www.w3schools.com/java/java_files_create.asp
 * [1] PrintWriter: (docs.oracle.com/en/java/javase/11/docs/api/java.base/
 *                   java/io/PrintWriter.html)
 * [2] IOException: (docs.oracle.com/javase/7/docs/api/java/io/
 *                   IOException.html)
 * [3] FileNotFoundException: (docs.oracle.com/javase/7/docs/api/java/io/
 *                             FileNotFoundException.html)
 * [4] Scanner: docs.oracle.com/javase/7/docs/api/java/util/Scanner.html
 * [5] www.javatpoint.com/throw-keyword
 *
 */
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class Actividad2 {
static long iter;
    public static void main(String[] args) {
        File stored = new File("times.txt");
        try {
            stored.createNewFile();
            PrintWriter printer = new PrintWriter("times.txt");
            for (int i = 1; i <= 18; i++) {
                create();	// creates a file
                iter = 0;
                write((int) Math.pow(2, i % 19), (int) Math.pow(2, i % 19)+1);	// writes data to the file
                long start = System.nanoTime();
                store();	// stores data in a two-dimention array
                long end = System.nanoTime();
                long time = end - start; //calculates the time taken by the algorythm
                //Stores the amount of data, amount of iterations and time taken
                printer.printf("%s\n", (int) Math.pow(2, i % 19) +" "+iter+" "+time);
            }
            printer.close();
        } catch (IOException ex) {
            Logger.getLogger(Actividad2.class.getName()).log(Level.SEVERE, null, ex);
        }

        return;
    }

    // implementations:
    private static void create() // creates a file
    {
        try {
            // defines the filename
            String fname = ("data.txt");
            // creates a new File object
            File f = new File(fname);

            String msg = "creating file `" + fname + "' ... ";
            System.out.println();
            System.out.printf("%s", msg);
            // creates the new file
            f.createNewFile();
            System.out.println("done");

        } catch (IOException err) {
            // complains if there is an Input/Output Error
            err.printStackTrace();
        }

        return;
    }

    /**
     * The following method recieves an integer and a range and fills the file
     * data.txt with the N amount of random integers
     * <p>
     * Each integer can be a value within the specified range and takes one line
     * of the file
     * <p>
     * This method is a modified version of the write() method created by
     * Diaz-Maldonado
     * <p>
     * Inputs: Integer N and integer Range
     * <p>
     * Outputs: data.txt file filled with N random integers
     *
     * @param N The amount of integers that will be written to the file
     * @param range The maximun number you want the random numbers to possibly
     * have [0,range)
     */
    private static void write(int N, int range) //TLDR: writes N random integers in the file, each value in the specified range
    {
        try {
            // defines the filename
            String filename = ("data.txt");
            // creates new PrintWriter object for writing file
            PrintWriter out = new PrintWriter(filename);

            String msg = "writing %d numbers ... ";
            System.out.printf(msg, N);
            // Creates the Random object to obtain a random number from an specified range
            Random random = new Random();
            // writes the N integers in the specified range
            for (int i = 0; i != N; ++i) {
                //generates a random integer and stores it in one line of the file
                // To simulate the worst case we replace random.nextInt for i
                //out.printf("%d\n", i);
                out.printf("%d\n", random.nextInt(range));
            }

            System.out.println("done");

            System.out.printf("closing file ... ");
            out.close();	// closes the output stream
            System.out.println("done");
        } catch (FileNotFoundException err) {
            // complains if file does not exist
            err.printStackTrace();
        }

        return;
    }

    private static void read() // reads the file contents and prints them to the console
    {
        // defines the filename
        String filename = ("data.txt");
        // creates a File object
        File f = new File(filename);
        try {
            // attempts to create a Scanner object
            Scanner in = new Scanner(f);

            System.out.printf("\nnumbers in file:\n");

            int x;
            int count = 0;
            // reads integers in file until EOF
            while (in.hasNextInt()) {
                // reads  number and prints it
                x = in.nextInt();
                System.out.printf("%4d\n", x);
                ++count;
            }

            String msg = ("%d numbers have been read\n");
            System.out.printf(msg, count);

            in.close();	// closes the input stream

        } catch (FileNotFoundException err) {
            // complains if file does not exist
            err.printStackTrace();
        }

        return;
    }

    /**
     * The following method reads the file data.txt, creates a two-dimention
     * array where the values stored in the file are in the first column and the
     * ammount of times that value was found in the file are stored in the
     * second column
     * <p>
     * After that, the elements of the array are printed showing the numbers
     * found in the file and how many times they were found(duplicates) until
     * zero is found in the second column
     * <p>
     * Input: The maximun number that can be in the file
     * <p>
     * Output: The numbers found in the file and how many times they were found
     * as console text
     * <p>
     * This method is a modified version of the store() method created by
     * Diaz-Maldonado
     *
     */
    private static void store() //TLDR: stores the file contents into a two-dimention array (to count instances) and prints it
    {
        String filename = "data.txt";
        File f = new File(filename);
        try {
            Scanner in = new Scanner(f);
            // allocates list for storing the numbers in file
            int size = lines(filename);
            // declares the two dimention array
            int[][] mat = new int[size][2];
            //Goes through all the file
            while (in.hasNextInt()) {
                int key = in.nextInt();
                int pos = 0;
                //searches the number in the array
                while (mat[pos][0] != key && mat[pos][1] != 0) {
                    pos++;
                    iter++; // counting the iterations made while reading the file
                }
                iter++; //counts last iteration
                if (mat[pos][0] == key) {
                    //if the number is found, then it adds to it's counter (second column)
                    mat[pos][1] = mat[pos][1] + 1;
                } else {
                    //if the number is not found, then it's the first occurance
                    //the number is added after the previous in the array and it's counter
                    // is initializad
                    mat[pos][0] = key;
                    mat[pos][1] = mat[pos][1] + 1;
                }
            }
            //This for while loop iterates through the array until it finds the zero in the second column
            //while iterating it prints the integers an their counters
            int i = 0;
            while (i<size && mat[i][1] != 0) {
                System.out.println("Integer: " + mat[i][0] + ", ammount: " + mat[i][1]);
                i++;
            }
            in.close();	// closes the input stream
        } catch (FileNotFoundException err) {
            // complains if file does not exist
            err.printStackTrace();
        }
        return;
    }

    private static int lines(String filename) // counts number of lines (or records) in a file
    {

        int count = 0;
        // creates a File object
        File f = new File(filename);
        try {
            // attempts to create a Scanner object
            Scanner in = new Scanner(f);

            // reads integers in file until EOF for counting
            while (in.hasNextInt()) {
                in.nextInt();
                ++count;
            }

            in.close();	// closes the input stream
        } catch (FileNotFoundException err) {
            // complains if file does not exist
            err.printStackTrace();
        }

        return count;
    }
}

/*
 * COMMENTS:
 *
 * Reading Data:
 * Reads integers until the scanner object finds something that it is not
 * an integer, such as a String or an End-Of-File EOF for instance.
 *
 * Static methods:
 * Static methods are not bound to any object of the class and these can
 * be conveniently invoked directly from the main program (as done here).
 *
 * Variables:
 * Note that local variables are destroyed (freed from memory) after the
 * method executes. Do not use global variables unless you really have to.
 *
 */
