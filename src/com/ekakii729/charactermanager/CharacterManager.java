/*
 * Author: Abhay Manoj
 * Purpose:
 * Date of Creation: October 04, 2023
 */

// hello devin

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

    /** Method Name: createCharacterUtils
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Creates CharacterUtil object with number of records from file
     * @Parameters input - Scanner used to take input
     * @Returns CharacterUtil object for accessing character array, Data Type: CharacterUtil
     * Dependencies: CharacterUtil, Scanner
     * Throws/Exceptions: N/A
     */

    private static CharacterUtils createCharacterUtils(Scanner input) {
        final int TEXT_FILE_CHOICE = 1; // the choice for the text file
        final int BINARY_FILE_CHOICE = 2; // the choice for the binary file
        int userChoice;  // the option that the user chooses for the menu
        String fileName; // the name of the file that the user chooses to read from
        CharacterUtils characterUtils; // character util object that is going to be returned
        System.out.println("\nWelcome to the Dungeons & Dragons Character Manager, enter 1 to read from a text file, or 2 to read from a binary file.");
        do {
            userChoice = Integer.parseInt(input.nextLine());
            if (userChoice != TEXT_FILE_CHOICE && userChoice != BINARY_FILE_CHOICE) System.out.println("\nThat is not valid, please try again.");
        } while (userChoice != TEXT_FILE_CHOICE && userChoice != BINARY_FILE_CHOICE);
        System.out.print("\nEnter name of file: ");
        fileName = input.nextLine();
        if (userChoice == TEXT_FILE_CHOICE) {
            characterUtils = new CharacterUtils(getTextNumOfRecords(fileName));
            characterUtils.readFromTextFile(fileName);
            return characterUtils;
        } characterUtils = new CharacterUtils(getBinaryNumOfRecords(fileName));
        characterUtils.readFromBinaryFile(fileName);
        return characterUtils;
    }

    /** Method Name: getNameOfFile
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Gets the name of a file from user
     * @Parameters input - Scanner used to take input
     * @Returns The name of the file, Data Type: String
     * Dependencies: Scanner
     * Throws/Exceptions: N/A
     */
    private static String getNameOfFile(Scanner input) {
        System.out.print("\nEnter name of file: ");
        return input.nextLine();
    }

    /** Method Name: getNameOfCharacter
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Gets the name of a character from user
     * @Parameters input - Scanner used to take input
     * @Returns The name of the character, Data Type: String
     * Dependencies: Scanner
     * Throws/Exceptions: N/A
     */

    public static String getNameOfCharacter(Scanner input) {
        System.out.print("\nEnter name of character: ");
        return input.nextLine();
    }

    /** Method Name: getCharacterIndex
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Gets the index of a character specified from the user
     * @Parameters input - Scanner used to take input, myHelper - CharacterUtil object used to access character array
     * @Returns The index of the character in the array, Data Type: Integer
     * Dependencies: CharacterUtils, Scanner
     * Throws/Exceptions: N/A
     */

    private static int getCharacterIndex(Scanner input, CharacterUtils myHelper) {
        return myHelper.searchForCharacter(getNameOfCharacter(input));
    }

    /** Method Name: convertCharacter
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Converts the race or class of the character
     * @Parameters input - Scanner used to take input, myHelper - CharacterUtil object used to access character array, mode - changing either race or class
     * @Returns N/A, Data Type: Void
     * Dependencies: CharacterUtils, Scanner
     * Throws/Exceptions: N/A
     */

    private static void convertCharacter(Scanner input, CharacterUtils myHelper,String mode) {
        String choice; // the choice that the user enters
        int characterIndex; // the index of the character
        if (mode.equals("Race")) {
            System.out.print("\nEnter the race that you wish to convert them to: ");
            choice = input.nextLine();
            characterIndex = getCharacterIndex(input, myHelper);
            myHelper.getCharacters()[characterIndex].changeRace(choice);
        } else {
            System.out.print("\nEnter the class that you wish to convert them to: ");
            choice = input.nextLine();
            characterIndex = getCharacterIndex(input, myHelper);
            myHelper.getCharacters()[characterIndex].changeClass(choice);
        } System.out.println("---------------------------------------------------------------------------------");
        myHelper.getCharacters()[characterIndex].display();
    }

    /** Method Name: closeProgram
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Closes the program
     * @Parameters input - Scanner used to take input
     * @Returns The boolean signaling the program to close, Data Type: Boolean
     * Dependencies: Scanner
     * Throws/Exceptions: N/A
     */

    private static boolean closeProgram(Scanner input) {
        System.out.println("\nProgram has been closed.");
        input.close();
        return false;
    }

    /** Method Name: updateLevelOfCharacter
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Updates level of character
     * @Parameters input - Scanner used to take input, myHelper - CharacterUtil object used to access character array
     * @Returns N/A, Data Type: Void
     * Dependencies: CharacterUtils, Scanner
     * Throws/Exceptions: N/A
     */

    private static void updateLevelOfCharacter(Scanner input, CharacterUtils myHelper) {
        int characterIndex = getCharacterIndex(input, myHelper); // index of the character
        System.out.print("\nEnter the current level of this character: ");
        myHelper.getCharacters()[characterIndex].levelUp(Integer.parseInt(input.nextLine()) - myHelper.getCharacters()[characterIndex].getLevel());
        myHelper.getCharacters()[characterIndex].display();
    }

    /** Method Name: menuLoop
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Runs the main loop of the program
     * @Parameters input - Scanner used to take input
     * @Returns N/A, Data Type: Void
     * Dependencies: CharacterUtils, Scanner
     * Throws/Exceptions: N/A
     */

    private static void menuLoop(Scanner input) {
        final String RACE_MODE = "Race"; // the race mode for the convert character method
        final String CLASS_MODE = "Class"; // the class mode for the convert character method
        boolean isRunning = true; // checks if the program is running or not
        CharacterUtils myHelper = createCharacterUtils(input); // CharacterUtil object used to access character array
        while (isRunning) {
            System.out.println("\nSelect an Option:\n1. Read from Text File\n2. Read from Binary File\n3. Write to Binary File\n4. Print All Characters\n5. Add New Character\n6. Delete Character\n7. Change Race of Character\n8. Change Class of Character\n9. Update Level of Character\n10. Update Character Stats\n11. Search by Character Name\n12. Quit");
            switch (Integer.parseInt(input.nextLine())) {
                case 1 -> myHelper.readFromTextFile(getNameOfFile(input));
                case 2 -> myHelper.readFromBinaryFile(getNameOfFile(input));
                case 3 -> myHelper.writeToFile(getNameOfFile(input));
                case 4 -> myHelper.printAllCharacters();
                case 5 -> myHelper.addNewCharacterToList();
                case 6 -> myHelper.deleteCharacterFromList(getNameOfCharacter(input));
                case 7 -> convertCharacter(input, myHelper, RACE_MODE);
                case 8 -> convertCharacter(input, myHelper, CLASS_MODE);
                case 9 -> updateLevelOfCharacter(input, myHelper);
                case 10 -> myHelper.updateStats(input);
                case 11 -> myHelper.searchForCharacter(getNameOfCharacter(input));
                case 12 -> isRunning = closeProgram(input);
                default -> System.out.println("\nThat is not a valid option. Please try again.");
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
     * Throws/Exceptions: N/A
     */

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        menuLoop(input);
    }
}