/*
 * Author: Abhay Manoj
 * Purpose: Utility class for the Character Manager
 * Date of Creation: October 04, 2023
 */

package com.ekakii729.charactermanager;
import java.io.*;
import java.util.Scanner;

public class CharacterUtils {

    private Character[] characters; // list of characters

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
            System.out.println("\nFILE NOT FOUND ERROR, CHECK WHERE FILE WAS PLACED --> " + e);
        } catch (IOException e) {
            System.out.println("\nI/O ERROR --> " + e);
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
            System.out.println("\nFILE NOT FOUND ERROR, CHECK WHERE FILE WAS PLACED --> " + e);
        } catch (IOException e) {
            System.out.println("\nI/O ERROR --> " + e);
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
        if (characters.length == 0) System.out.println("\nThere are no characters currently available. Please add some characters.");
        System.out.println();
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
            System.out.println("Character has been deleted.");
        } else System.out.println("\n" + characterName + " was not found. PLease try again.");
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
        } System.out.println("\n" + characterName + " was not found, please try again.");
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
        System.out.println("\nBase character template has been added by the name of \"John Doe\", please edit stats using menu.");
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
        int characterIndex = searchForCharacter(input.nextLine()); // index of the character that is being modified
        if (characterIndex != -1) {
            do {
                System.out.println("\nWhat would you like to modify?\n1. Name\n2. Race\n3. Class\n4. Level \n5. HitPoints \n6. Strength\n7. Constitution\n8. Intelligence\n9. Wisdom\n10. Dexterity\n11. Charisma");
                switch (Integer.parseInt(input.nextLine())) {
                    case 1 -> {
                        System.out.print("\nEnter their name: ");
                        characters[characterIndex].setName(input.nextLine());
                    } case 2 -> {
                        System.out.print("\nEnter their race: ");
                        characters[characterIndex].changeRace(input.nextLine());
                    } case 3 -> {
                        System.out.print("\nEnter their class: ");
                        characters[characterIndex].changeClass(input.nextLine());
                    } case 4 -> {
                        System.out.print("\nEnter their level: ");
                        characters[characterIndex].setLevel(Integer.parseInt(input.nextLine()));
                    } case 5 -> {
                        System.out.print("\nEnter their HitPoints: ");
                        characters[characterIndex].setHitPoints(Integer.parseInt(input.nextLine()));
                    } case 6 -> {
                        System.out.print("\nEnter their strength: ");
                        characters[characterIndex].setStrength(Integer.parseInt(input.nextLine()));
                    } case 7 -> {
                        System.out.print("\nEnter their constitution: ");
                        characters[characterIndex].setConstitution(Integer.parseInt(input.nextLine()));
                    } case 8 -> {
                        System.out.print("\nEnter their intelligence: ");
                        characters[characterIndex].setIntelligence(Integer.parseInt(input.nextLine()));
                    } case 9 -> {
                        System.out.print("\nEnter their wisdom: ");
                        characters[characterIndex].setWisdom(Integer.parseInt(input.nextLine()));
                    } case 10 -> {
                        System.out.print("\nEnter their dexterity: ");
                        characters[characterIndex].setDexterity(Integer.parseInt(input.nextLine()));
                    } case 11 -> {
                        System.out.print("\nEnter their charisma: ");
                        characters[characterIndex].setCharisma(Integer.parseInt(input.nextLine()));
                    } default -> System.out.println("\nNot a valid option, try again.");
                } System.out.print("Would you like to continue editing this character? Enter 'y' or 'n': ");
            } while (input.nextLine().charAt(0) != 'n');
            System.out.println();
            characters[characterIndex].display();
        }
    }

    public Character[] getCharacters() {
        return characters;
    }

}
