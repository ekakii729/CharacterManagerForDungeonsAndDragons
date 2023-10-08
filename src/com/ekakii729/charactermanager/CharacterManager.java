/*
 * Author: Abhay Manoj
 * Purpose:
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
            System.out.println("FILE NOT FOUND ERROR, CHECK WHERE FILE WAS PLACED --> " + e);
        } catch (IOException e) {
            System.out.println("I/O ERROR --> " + e);
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
            System.out.println("FILE NOT FOUND ERROR, CHECK WHERE FILE WAS PLACED --> " + e);
        } catch (IOException e) {
            System.out.println("I/O ERROR --> " + e);
        } return numberOfRecords / NUMBER_OF_LINES_IN_RECORD;
    }

    /** Method Name: createCharacterUtils
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Creates CharacterUtil object with number of records from file
     * @Parameters input - Scanner used to take input
     * @Returns CharacterUtil object for accessing character array, Data Type: CharacterUtil
     * Dependencies: CharacterUtil
     * Throws/Exceptions: N/A
     */

    private static CharacterUtils createCharacterUtils(Scanner input) {
        final int TEXT_FILE_CHOICE = 1; // the choice for the text file
        final int BINARY_FILE_CHOICE = 2; // the choice for the binary file
        int userChoice;  // the option that the user chooses for the menu
        int numberOfRecords; //  the number of records in the file that the user chooses to read from
        String fileName; // the name of the file that the user chooses to read from
        CharacterUtils characterUtils; // character util object that is going to be returned
        System.out.println("Welcome to the Dungeons & Dragons Character Manager, enter 1 to read from a text file, or 2 to read from a binary file.");
        do {
            userChoice = Integer.parseInt(input.nextLine());
            if (userChoice != TEXT_FILE_CHOICE && userChoice != BINARY_FILE_CHOICE) System.out.println("That is not valid, please try again.");
        } while (userChoice != TEXT_FILE_CHOICE && userChoice != BINARY_FILE_CHOICE);
        System.out.println("Enter name of file: ");
        fileName = input.nextLine();
        if (userChoice == TEXT_FILE_CHOICE) {
            numberOfRecords = getTextNumOfRecords(fileName);
            characterUtils = new CharacterUtils(numberOfRecords);
            characterUtils.readFromTextFile(fileName);
            return characterUtils;
        } numberOfRecords = getBinaryNumOfRecords(fileName);
        characterUtils = new CharacterUtils(numberOfRecords);
        characterUtils.readFromBinaryFile(fileName);
        return characterUtils;
    }

    private static void menuLoop(Scanner input) {
        int userChoice; // the option that the user chooses for the menu
        String stringChoice; // an option that is for strings
        boolean isRunning = true; // checks if the program is running or not
        CharacterUtils myHelper = createCharacterUtils(input); // CharacterUtil object used to access character array
        while (isRunning) {System.out.println("\nSelect an Option:\n1. Read from Text File\n2. Read from Binary File\n3. Write to Binary File\n4. Print All Characters\n5. Add New Character\n6. Delete Character\n7. Change Race of Character\n8. Change Class of Character\n9. Update Level of Character\n10. Update Character Stats\n11. Search by Character Name\n12. Quit");
            userChoice = Integer.parseInt(input.nextLine());
            switch (userChoice) {
                case 1 -> {
                    System.out.print("Enter name of file: ");
                    stringChoice = input.nextLine();
                    myHelper.readFromTextFile(stringChoice);
                } case 2 -> {
                    System.out.print("Enter name of file: ");
                    stringChoice = input.nextLine();
                    myHelper.readFromBinaryFile(stringChoice);
                } case 3 -> {
                    System.out.print("Enter name of file to write to: ");
                    stringChoice = input.nextLine();
                    myHelper.writeToFile(stringChoice);
                } case 4 -> {
                    myHelper.printAllCharacters();
                } case 5 -> {
                    myHelper.addNewCharacterToList();
                    System.out.println("Base character template has been added, please edit stats using menu.");
                } case 6 -> {
                    System.out.print("Enter name of character to delete: ");
                    stringChoice = input.nextLine();
                    myHelper.deleteCharacterFromList(stringChoice);
                } case 7 -> {

                } case 8 -> {

                } case 9 -> {
                    System.out.print("Enter name of the character: ");
                    stringChoice = input.nextLine();
                    int characterIndex = myHelper.searchForCharacter(stringChoice); // the index of the character in the array
                    System.out.print("Enter the current level of this character: ");
                    userChoice = Integer.parseInt(input.nextLine());
                    myHelper.getCharacters()[characterIndex].levelUp(userChoice - myHelper.getCharacters()[characterIndex].getLevel());
                } case 10 -> {
                    myHelper.updateStats(input);
                } case 11 -> {
                    System.out.print("Enter name of character to search for: ");
                    stringChoice = input.nextLine();
                    myHelper.searchForCharacter(stringChoice);
                } case 12 -> {
                    System.out.println("Program has been closed.");
                    isRunning = false;
                    input.close();
                } default -> {
                    System.out.println("That is not a valid option. Please try again.");
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        menuLoop(input);
    }
}