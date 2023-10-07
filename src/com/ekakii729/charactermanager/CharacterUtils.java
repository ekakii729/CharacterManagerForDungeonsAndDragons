/*
 * Author: Abhay Manoj
 * Purpose: Utility class for the Character Manager
 * Date of Creation: October 04, 2023
 */

package com.ekakii729.charactermanager;

import java.io.*;

public class CharacterUtils {

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

    public int getBinaryNumOfRecords(String fileName) {
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

    public int getTextNumOfRecords(String fileName) {
        int numberOfRecords = 0; // number of records within the file
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
        } return numberOfRecords / 11; // 11 lines in 1 record
    }

    /** Method Name: createCharacterArray
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Returns null array of characters
     * @Parameters size - size of array
     * @Returns Array of characters, Data Type: Character[]
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public Character[] createCharacterArray(int size) {
        return new Character[size];
    }

    /** Method Name: writeToFile
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Writes content of character array to binary file
     * @Parameters fileName - name of the file, characters - list of characters
     * @Returns N/A, Data Type: Void
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: IOException
     */

    public void writeToFile(String fileName, Character[] characters) {
        try {
            RandomAccessFile randomAccessor = new RandomAccessFile(fileName, "rw"); // accesses binary file
            randomAccessor.setLength(characters.length * Character.getRecordLength());
            for (int i = 0; i < characters.length; i++) characters[i].writeRecord(randomAccessor, i);
            randomAccessor.close();
        } catch (IOException e) {
            System.out.println("I/O ERROR --> " + e);
        }
    }

    /** Method Name: readFromBinaryFile
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Reads content of binary file and writes to character array
     * @Parameters fileName - name of the file, characters - list of characters
     * @Returns N/A, Data Type: Void
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: FileNotFoundException, IOException
     */

    public void readFromBinaryFile(String fileName, Character[] characters) {
        try {
            RandomAccessFile randomAccessor = new RandomAccessFile(fileName, "rw"); // access binary file
            for (int i = 0; i < characters.length; i++) {
                characters[i] = new Character();
                characters[i].readRecord(randomAccessor, i);
            } randomAccessor.close();
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND ERROR, CHECK WHERE FILE WAS PLACED --> " + e);
        } catch (IOException e) {
            System.out.println("I/O ERROR --> " + e);
        }
    }

    /** Method Name: readFromTextFile
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Reads content of text file and writes to character array
     * @Parameters fileName - name of the file, characters - list of characters
     * @Returns N/A, Data Type: Void
     * Dependencies: BufferedReader, FileReader
     * Throws/Exceptions: FileNotFoundException, IOException
     */

    public void readFromTextFile(String fileName, Character[] characters) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName)); // accesses text file
            int i = 0; // counter to access indexes of character array
            while (reader.ready()) {
                characters[i] = new Character();
                characters[i].setName(reader.readLine());
                characters[i].setRace(reader.readLine());
                characters[i].setClassOfCharacter(reader.readLine());
                characters[i].setLevel(Integer.parseInt(reader.readLine()));
                characters[i].setHitPoints(Integer.parseInt(reader.readLine()));
                characters[i].setStrength(Integer.parseInt(reader.readLine()));
                characters[i].setConstitution(Integer.parseInt(reader.readLine()));
                characters[i].setIntelligence(Integer.parseInt(reader.readLine()));
                characters[i].setWisdom(Integer.parseInt(reader.readLine()));
                characters[i].setDexterity(Integer.parseInt(reader.readLine()));
                characters[i].setCharisma(Integer.parseInt(reader.readLine()));
                i++;
            } reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND ERROR, CHECK WHERE FILE WAS PLACED --> " + e);
        } catch (IOException e) {
            System.out.println("I/O ERROR --> " + e);
        }
    }

    /** Method Name: printAllCharacters
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Prints all characters in array
     * @Parameters characters - list of characters
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void printAllCharacters(Character[] characters) {
        for (Character character : characters) character.display();
    }

    /** Method Name: deleteCharacter
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Deletes a character by their name
     * @Parameters characterName - the name of the character characters - list of characters
     * @Returns The character array without the given character, Data Type: Character[]
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public Character[] deleteCharacter(String characterName, Character[] characters) {
        Character[] newCharacters = null; // the new array of characters
        boolean hasBeenFound = false; // checking if the character has been found
        int indexOfCharacter = -1; // index of the character if they are found
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].getName().equals(characterName)) {
                indexOfCharacter = i;
                hasBeenFound = true;
                newCharacters = createCharacterArray(characters.length - 1);
                break; }
        } if (hasBeenFound) {
            int newCharacterPointer = 0; // pointer to access indexes of new character array
            for (int i = 0; i < characters.length; i++) {
                if (i == indexOfCharacter) continue;
                newCharacters[newCharacterPointer] = characters[i];
                newCharacterPointer++;
            } return newCharacters;
        } System.out.println(characterName + " was not found. PLease try again.");
        return characters;
    }

    /** Method Name: searchForCharacter
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Searches for a character and displays their stats
     * @Parameters characterName - the name of the character characters - list of characters
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void searchForCharacter(String characterName, Character[] characters) {
        for (Character character : characters) {
            if (character.getName().equals(characterName)) {
                character.display();
                break;
            }
        }
    }
}