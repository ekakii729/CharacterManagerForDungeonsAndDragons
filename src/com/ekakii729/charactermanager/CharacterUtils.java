/*
 * Author: Abhay Manoj
 * Purpose: Utility class for the Character Manager
 * Date of Creation: October 07, 2023
 */

package com.ekakii729.charactermanager;
import java.io.*;
import java.util.Scanner;

public class CharacterUtils {

    private Character[] characters; // the list of characters
    private String binaryFileName; //  the name of the binary file that is being written to

    public CharacterUtils(int size) {
        characters = new Character[size];
        binaryFileName = "myCharacters.bin";
    }

    /** Method Name: readFromTextFile
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Reads content of text file and writes to character array while writing to new binary file
     * @Parameters fileName - name of the file
     * @Returns N/A, Data Type: Void
     * Dependencies: BufferedReader, FileReader
     * Throws/Exceptions: FileNotFoundException, IOException
     */

    public void userTextFileChoiceToArray(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName)); // used to access the text file
            int i = 0; // used to access indexes in the character array
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
            writeToFile();
            System.out.println("\nFile has been successfully read.");
        } catch (FileNotFoundException e) {
            System.out.println("\nFILE NOT FOUND ERROR, CHECK WHERE FILE WAS PLACED --> " + e);
        } catch (IOException e) {
            System.out.println("\nI/O ERROR --> " + e);
        }
    }

    /** Method Name: readKnownBinaryFileToArray
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Reads content of binary file and writes to character array
     * @Parameters N/A
     * @Returns N/A, Data Type: Void
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: FileNotFoundException, IOException
     */

    public void readKnownBinaryFileToArray() {
        try {
            RandomAccessFile randomAccessor = new RandomAccessFile(binaryFileName, "rw"); // used to read binary file
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

    /** Method Name: userBinaryFileChoiceToArray
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Reads content of user chosen binary file and writes to character array
     * @Parameters fileName - name of the file
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void userBinaryFileChoiceToArray(String fileName) {
        binaryFileName = fileName;
        readKnownBinaryFileToArray();
    }

    /** Method Name: writeToFile
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Writes content of character array to binary file
     * @Parameters N/A
     * @Returns N/A, Data Type: Void
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: IOException
     */
    public void writeToFile() {
        try {
            RandomAccessFile randomAccessor = new RandomAccessFile(binaryFileName, "rw"); // accesses binary file
            randomAccessor.setLength(characters.length * Character.getRecordLength());
            for (int i = 0; i < characters.length; i++) characters[i].writeRecord(randomAccessor, i);
            randomAccessor.close();
        } catch (IOException e) {
            System.out.println("I/O ERROR --> " + e);
        }
    }

    /** Method Name: userWriteToFile
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Writes content of character array to binary file of user's choice
     * @Parameters input - used to take user input
     * @Returns N/A, Data Type: Void
     * Dependencies: Scanner
     * Throws/Exceptions: N/A
     */

    public void userWriteToFile(Scanner input) {
        System.out.print("\nEnter EXACT name of file (ex. characterStats.txt, myCharacters.bin): ");
        binaryFileName = input.nextLine();
        writeToFile();
        System.out.println("The file has been written to.");
    }

    /** Method Name: getCharacterIndex
     * @Author Abhay Manoj
     * @Date October 10, 2023
     * @Modified October 10, 2023
     * @Description Gets index of a character in the array
     * @Parameters input - used to take user input
     * @Returns The index of the character, Data Type: Integer
     * Dependencies: Scanner
     * Throws/Exceptions: N/A
     */

    private int getCharacterIndex(Scanner input) {
        System.out.print("\nEnter EXACT name of character (ex. Magnus Carlsen): ");
        String characterName = input.nextLine(); // name of the character
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].getName().equals(characterName)) {
                return i; }
        } System.out.println("\nCharacter was not found. Please check your spelling.");
        return -1;
    }

    /** Method Name: deleteCharacterFromList
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Deletes a character
     * @Parameters input - used to take user input
     * @Returns N/A, Data Type: Void
     * Dependencies: Scanner
     * Throws/Exceptions: N/A
     */

    public void deleteCharacterFromList(Scanner input) {
        int characterIndex = getCharacterIndex(input); // index of the character
        if (characterIndex != -1) {
            Character[] newCharacters = new Character[characters.length - 1]; // new list that is one element smaller than before
            int newArrayPointer = 0; // pointer used to access elements in new characters array
            for (int i = 0; i < characters.length; i++) {
                if (i == characterIndex) continue;
                newCharacters[newArrayPointer] = characters[i];
                newArrayPointer++;
            } characters = newCharacters;
            writeToFile();
            readKnownBinaryFileToArray();
            System.out.println("\nCharacter has been deleted.");
        }
    }

    /** Method Name: writeCharacterRecord
     * @Author Abhay Manoj
     * @Date October 10, 2023
     * @Modified October 10, 2023
     * @Description Writes a single character to the binary file
     * @Parameters numberOfRecord - the index of the character
     * @Returns N/A, Data Type: Void
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: IOException
     */

    private void writeCharacterRecord(int numberOfRecord) throws IOException {
        RandomAccessFile randomAccessor = new RandomAccessFile(binaryFileName,"rw"); // used to write record to file
        randomAccessor.seek(numberOfRecord * Character.getRecordLength());
        characters[numberOfRecord].writeRecord(randomAccessor, numberOfRecord);
    }

    /** Method Name: addNewCharacterToList
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Adds new character to list and file
     * @Parameters input - used to take user input
     * @Returns N/A, Data Type: Void
     * Dependencies: Scanner
     * Throws/Exceptions: IOException
     */

    public void addNewCharacter(Scanner input) throws IOException {
        int newCharacterIndex = characters.length; // the index of the new character that will be added
        Character[] newCharacters = new Character[characters.length + 1]; // new list that is one element bigger than before
        System.arraycopy(characters, 0, newCharacters, 0, characters.length);
        newCharacters[newCharacterIndex] = new Character();
        System.out.print("\nEnter the EXACT name of your character (ex. Magnus Carlsen): ");
        newCharacters[newCharacterIndex].setName(input.nextLine());
        System.out.print("\nEnter the race of your character (Human, Halfling, Elf, Orc, Dwarf, Gnome): ");
        newCharacters[newCharacterIndex].setRace(input.nextLine());
        System.out.print("\nEnter the class of your character (Warrior, Mage, Rogue, Cleric, Bard, Ranger): ");
        newCharacters[newCharacterIndex].setClassOfCharacter(input.nextLine());
        newCharacters[newCharacterIndex].generateStats();
        newCharacters[newCharacterIndex].setLevel(1);
        characters = newCharacters;
        writeCharacterRecord(newCharacterIndex);
        System.out.println("\nCharacter has been added with randomly generated stats.");
        newCharacters[newCharacterIndex].display();
    }

    /** Method Name: changeRace
     * @Author Abhay Manoj
     * @Date October 10, 2023
     * @Modified October 10, 2023
     * @Description Changes race of character
     * @Parameters input - used to take user input
     * @Returns N/A, Data Type: Void
     * Dependencies: Scanner
     * Throws/Exceptions: IOException
     */

    public void changeRace(Scanner input) throws IOException {
        int characterIndex = getCharacterIndex(input); // index of the character
        if (characterIndex != -1) {
            System.out.print("\nEnter desired race (Human, Halfling, Elf, Orc, Dwarf, Gnome): ");
            characters[characterIndex].changeRace(input.nextLine());
            writeCharacterRecord(characterIndex);
        }
    }

    /** Method Name: changeClass
     * @Author Abhay Manoj
     * @Date October 10, 2023
     * @Modified October 10, 2023
     * @Description Changes race of character
     * @Parameters input - used to take user input
     * @Returns N/A, Data Type: Void
     * Dependencies: Scanner
     * Throws/Exceptions: IOException
     */

    public void changeClass(Scanner input) throws IOException {
        int characterIndex = getCharacterIndex(input); // index of the character
        if (characterIndex != -1) {
            System.out.print("\nEnter desired class (Warrior, Mage, Rogue, Cleric, Bard, Ranger): ");
            characters[characterIndex].changeClass(input.nextLine());
            writeCharacterRecord(characterIndex);
        }
    }

    /** Method Name: updateLevel
     * @Author Abhay Manoj
     * @Date October 10, 2023
     * @Modified October 10, 2023
     * @Description updates level of a character
     * @Parameters input - used to take user input
     * @Returns N/A, Data Type: Void
     * Dependencies: Scanner
     * Throws/Exceptions: IOException
     */

    public void updateLevel(Scanner input) throws IOException {
        int characterIndex = getCharacterIndex(input); // index of the character
        if (characterIndex != -1) {
            System.out.print("\nEnter new level of character: ");
            int numberOfLevels = Integer.parseInt(input.nextLine()) - characters[characterIndex].getLevel(); // number of new levels to be added to character
            characters[characterIndex].levelUp(numberOfLevels);
            writeCharacterRecord(characterIndex);
        }
    }

    /** Method Name: updateStats
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Submenu to update stats of a character
     * @Parameters input - used to take user input
     * @Returns N/A, Data Type: Void
     * Dependencies: Scanner
     * Throws/Exceptions: IOException
     */

    public void updateStats(Scanner input) throws IOException {
        int characterIndex = getCharacterIndex(input); // index of the character
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
            writeCharacterRecord(characterIndex);
            characters[characterIndex].display();
        }
    }

    /** Method Name: printCharacter
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Prints out a single character and their attributes
     * @Parameters input - used to take user input
     * @Returns N/A, Data Type: Void
     * Dependencies: Scanner
     * Throws/Exceptions: N/A
     */

    public void printCharacter(Scanner input) {
        int characterIndex = getCharacterIndex(input); // index of the character
        if (characterIndex != -1) getCharacterList()[characterIndex].display();
    }

    /** Method Name: printCharacter
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 10, 2023
     * @Description Prints out all characters and attributes
     * @Parameters N/A
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void printAllCharacters() {
        System.out.println();
        if (characters.length == 0) System.out.println("\nThere are no characters currently available. Please add some characters.");
        else for (Character character : characters) character.display();
    }

    public Character[] getCharacterList() {
        return characters;
    }
}