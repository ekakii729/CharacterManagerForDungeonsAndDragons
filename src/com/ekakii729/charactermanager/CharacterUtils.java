/*
 * Author: Abhay Manoj
 * Purpose: Utility class for the Character Manager
 * Date of Creation: October 04, 2023
 */

package com.ekakii729.charactermanager;
import java.io.*;
import java.util.Scanner;

public class CharacterUtils {

    private Character[] characters;

    public CharacterUtils() {

    }

    public CharacterUtils(int size) {
        characters = new Character[size];
    }

    /** Method Name: writeToFile
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Writes content of character array to binary file
     * @Parameters fileName - name of the file
     * @Returns N/A, Data Type: Void
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: IOException
     */

    public void writeToFile(String fileName) {
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
     * @Parameters fileName - name of the file
     * @Returns N/A, Data Type: Void
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: FileNotFoundException, IOException
     */

    public void readFromBinaryFile(String fileName) {
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
     * @Parameters fileName - name of the file
     * @Returns N/A, Data Type: Void
     * Dependencies: BufferedReader, FileReader
     * Throws/Exceptions: FileNotFoundException, IOException
     */

    public void readFromTextFile(String fileName) {
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
     * @Parameters N/A
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void printAllCharacters() {
        for (Character character : characters) character.display();
    }

    /** Method Name: deleteCharacterFromList
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Deletes a character by their name
     * @Parameters characterName - the name of the character
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void deleteCharacterFromList(String characterName) {
        Character[] newCharacters = null; // the new array of characters
        boolean hasBeenFound = false; // checking if the character has been found
        int indexOfCharacter = -1; // index of the character if they are found
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].getName().equals(characterName)) {
                indexOfCharacter = i;
                hasBeenFound = true;
                newCharacters = new Character[characters.length - 1];
                break; }
        } if (hasBeenFound) {
            int newCharacterPointer = 0; // pointer to access indexes of new character array
            for (int i = 0; i < characters.length; i++) {
                if (i == indexOfCharacter) continue;
                newCharacters[newCharacterPointer] = characters[i];
                newCharacterPointer++;
            } characters = newCharacters;
        } System.out.println(characterName + " was not found. PLease try again.");
    }

    /** Method Name: searchForCharacter
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Searches for a character and displays their stats
     * @Parameters characterName - the name of the character
     * @Returns The index of the character, Data Type: Integer
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public int searchForCharacter(String characterName) {
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].getName().equals(characterName)) {
                characters[i].display();
                return i; }
        } System.out.println(characterName + " was not found, please try again.");
        return -1;
    }

    /** Method Name: addNewCharacterToList
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Adds new empty character to character array
     * @Parameters characters - list of characters
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void addNewCharacterToList() {
        Character[] newCharacters = new Character[characters.length + 1]; // new array that will contain new character
        System.arraycopy(characters, 0, newCharacters, 0, characters.length);
        int newCharacterIndex = newCharacters.length - 1; // the index of the new character
        newCharacters[newCharacterIndex] = new Character();
        newCharacters[newCharacterIndex].setName("John Doe"); // base template
        newCharacters[newCharacterIndex].setRace("Human");
        newCharacters[newCharacterIndex].setClassOfCharacter("Warrior");
        characters = newCharacters;
    }

    /** Method Name: updateStats
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Submenu to update stats of a character
     * @Parameters N/A
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void updateStats(Scanner input) {
        System.out.print("\nEnter the name of the character that you wish to modify: ");
        int characterIndex = searchForCharacter(input.nextLine()); // index of the character that is being searched for
        if (characterIndex != -1) {
            String stringChoice; // if the user enters a string
            int intChoice; // if the user enters an int
            System.out.println("\nWhat would you like to modify?\n1. Name\n2. Race\n3. Class\n4. Level \n5. HitPoints \n6. Strength\n7. Constitution\n8. Intelligence\n9. Wisdom\n10. Dexterity\n11. Charisma");
            switch (Integer.parseInt(input.nextLine())) {
                case 1 -> {
                    System.out.print("Enter their name: ");
                    stringChoice = input.nextLine();
                    characters[characterIndex].setName(stringChoice);
                } case 2 -> {
                    System.out.print("Enter their race: ");
                    stringChoice = input.nextLine();
                    characters[characterIndex].changeRace(stringChoice);
                } case 3 -> {
                    System.out.print("Enter their class: ");
                    stringChoice = input.nextLine();
                    characters[characterIndex].changeClass(stringChoice);
                } case 4 -> {
                    System.out.print("Enter their level: ");
                    intChoice = Integer.parseInt(input.nextLine());
                    characters[characterIndex].setLevel(intChoice);
                } case 5 -> {
                    System.out.print("Enter their HitPoints: ");
                    intChoice = Integer.parseInt(input.nextLine());
                    characters[characterIndex].setHitPoints(intChoice);
                } case 6 -> {
                    System.out.print("Enter their strength: ");
                    intChoice = Integer.parseInt(input.nextLine());
                    characters[characterIndex].setStrength(intChoice);
                } case 7 -> {
                    System.out.print("Enter their constitution: ");
                    intChoice = Integer.parseInt(input.nextLine());
                    characters[characterIndex].setConstitution(intChoice);
                } case 8 -> {
                    System.out.print("Enter their intelligence: ");
                    intChoice = Integer.parseInt(input.nextLine());
                    characters[characterIndex].setIntelligence(intChoice);
                } case 9 -> {
                    System.out.print("Enter their wisdom: ");
                    intChoice = Integer.parseInt(input.nextLine());
                    characters[characterIndex].setWisdom(intChoice);
                } case 10 -> {
                    System.out.print("Enter their dexterity: ");
                    intChoice = Integer.parseInt(input.nextLine());
                    characters[characterIndex].setDexterity(intChoice);
                } case 11 -> {
                    System.out.print("Enter their charisma: ");
                    intChoice = Integer.parseInt(input.nextLine());
                    characters[characterIndex].setCharisma(intChoice);
                } default -> System.out.println("Not a valid option, try again.");
            }
        }
    }

    public Character[] getCharacters() {
        return characters;
    }

    public void setCharacters(Character[] characters) {
        this.characters = characters;
    }
}
