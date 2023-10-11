/*
 * Author: Abhay Manoj
 * Purpose: Create and maintain a list of dungeons and dragons characters, while being able to easily change stats and manipulate characters.
 * Date of Creation: October 04, 2023
 */

package com.ekakii729.charactermanager;
import java.io.*;
import java.util.Scanner;

public class CharacterManager {

    /** Method Name: getBinaryNumOfRecords
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Gets the number of records within a binary file
     * @Parameters fileName - the name of the file being accessed
     * @Returns The number of records, Data Type: Integer
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: FileNotFoundException, IOException
     */

    private static int getBinaryNumOfRecords(String fileName) {
        try {
            RandomAccessFile randomAccessor = new RandomAccessFile(fileName, "rw"); // accesses binary file
            long fileSize = randomAccessor.length(); // size of the binary file in bytes
            randomAccessor.close();
            return (int) (fileSize / Character.getRecordLength());
        } catch (FileNotFoundException e) {
            System.out.println("\nFILE NOT FOUND ERROR, CHECK WHERE FILE WAS PLACED --> " + e);
        } catch (IOException e) {
            System.out.println("\nI/O ERROR --> " + e);
        } return -1;
    }

    /** Method Name: getTextNumOfRecords
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Gets the number of records within a text file
     * @Parameters fileName - the name of the file being accessed
     * @Returns The number of records, Data Type: Integer
     * Dependencies: BufferedReader, FileReader
     * Throws/Exceptions: FileNotFoundException, IOException
     */

    private static int getTextNumOfRecords(String fileName) {
        int numberOfRecords = 0; // number of records within the file
        final int NUMBER_OF_LINES_IN_RECORD = 11; // 11 lines in 1 record
        try {
            BufferedReader recordPointer = new BufferedReader(new FileReader(fileName)); // accesses text file
            while (recordPointer.ready()) {
                recordPointer.readLine();
                numberOfRecords++;
            } recordPointer.close();
        } catch (FileNotFoundException e) {
            System.out.println("\nFILE NOT FOUND ERROR, CHECK WHERE FILE WAS PLACED --> " + e);
        } catch (IOException e) {
            System.out.println("\nI/O ERROR --> " + e);
        } return numberOfRecords / NUMBER_OF_LINES_IN_RECORD;
    }

    /** Method Name: initialReadOfFile
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Creates CharacterUtil object with number of records from file
     * @Parameters input - used to take user input
     * @Returns CharacterUtils object for accessing character array, Data Type: CharacterUtils
     * Dependencies: Scanner
     * Throws/Exceptions: N/A
     */

    public static CharacterUtils initialReadOfFile(Scanner input) {
        final int TEXT_FILE_CHOICE = 1; // the choice for a text file
        final int BINARY_FILE_CHOICE = 2; //  the choice for a binary file
        int userChoice; // the choice the user makes
        CharacterUtils myHelper; // CharacterUtils object for accessing character array
        System.out.println("\nWelcome to the Dungeons & Dragons Character Manager, enter 1 to read from a text file, or 2 to read from a binary file.");
        do {
            userChoice = Integer.parseInt(input.nextLine());
            if (userChoice != TEXT_FILE_CHOICE && userChoice != BINARY_FILE_CHOICE) System.out.println("\nThat is not valid, please try again.");
        } while (userChoice != TEXT_FILE_CHOICE && userChoice != BINARY_FILE_CHOICE);
        System.out.print("\nEnter EXACT name of file (ex. characterStats.txt, myCharacters.bin): ");
        String fileName = input.nextLine(); //  the name of the file
        if (userChoice == TEXT_FILE_CHOICE) {
            myHelper = new CharacterUtils(getTextNumOfRecords(fileName));
            myHelper.userTextFileChoiceToArray(fileName);
            System.out.println("\nA new file named \"myCharacters.bin\" has been created. Use this file to read in data from now on.");
        } else {
            myHelper = new CharacterUtils(getBinaryNumOfRecords(fileName));
            myHelper.userBinaryFileChoiceToArray(fileName);
        } return myHelper;
    }

    /** Method Name: menuLoop
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Runs the main loop of the program
     * @Parameters input - used to take user input
     * @Returns N/A, Data Type: Void
     * Dependencies: Scanner
     * Throws/Exceptions: IOException
     */

    public static void menuLoop(Scanner input) throws IOException {
        boolean isRunning = true; // checks if the program is running or not
        CharacterUtils myHelper = initialReadOfFile(input); // CharacterUtils object used for accessing the array
        while (isRunning) {
            System.out.println("\nSelect an Option:\n1. Read from Text File\n2. Read from Binary File\n3. Write to Binary File\n4. Print All Characters\n5. Add New Character\n6. Delete Character\n7. Change Race of Character\n8. Change Class of Character\n9. Update Level of Character\n10. Update Character Stats\n11. Search by Character Name\n12. Quit");
            switch (Integer.parseInt(input.nextLine())) {
                case 1 -> {
                    System.out.print("\nEnter EXACT name of file (ex. characterStats.txt, myCharacters.bin): ");
                    myHelper.userTextFileChoiceToArray(input.nextLine());
                    System.out.println("\nA new file named \"myCharacters.bin\" has been created. Use this file to read in data from now on.");
                } case 2 -> {
                    System.out.print("\nEnter EXACT name of file (ex. characterStats.txt, myCharacters.bin): ");
                    myHelper.userBinaryFileChoiceToArray(input.nextLine());
                } case 3 -> myHelper.userWriteToFile(input);
                case 4 -> myHelper.printAllCharacters();
                case 5 -> myHelper.addNewCharacter(input);
                case 6 -> myHelper.deleteCharacterFromList(input);
                case 7 -> myHelper.changeRace(input);
                case 8 -> myHelper.changeClass(input);
                case 9 -> myHelper.updateLevel(input);
                case 10 -> myHelper.updateStats(input);
                case 11 -> myHelper.printCharacter(input);
                case 12 -> {
                    System.out.println("\nProgram has been closed.");
                    isRunning = false;
                    input.close();
                } default -> System.out.println("\nThat is not a valid option. Please try again.");
            }
        }
    }

    /** Method Name: main
     * @Author Abhay Manoj
     * @Date October 4, 2023
     * @Modified October 4, 2023
     * @Description Driver method of program
     * @Parameters args - arguments that can be passed in
     * @Returns N/A, Data Type: Void
     * Dependencies: Scanner
     * Throws/Exceptions: IOException
     */

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in); // used for user input
        menuLoop(input);
    }
}