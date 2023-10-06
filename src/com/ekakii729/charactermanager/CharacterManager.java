/*
 * Author: Abhay Manoj
 * Purpose:
 * Date of Creation: October 04, 2023
 */

package com.ekakii729.charactermanager;
import java.io.*;

public class CharacterManager {

    /**
     * Method Name: printAllCharacters
     * @Author Abhay Manoj
     * @Date October 4, 2023
     * @Modified October 4, 2023
     * @Description Prints characters that have all values assigned
     * @Parameters characters - list of characters
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public static void printAllCharacters(Character[] characters) {
        for (Character character : characters) if (!character.hasEmptyValue()) character.display();
    }

    /**
     * Method Name: searchForCharacter
     * @Author Abhay Manoj
     * @Date October 6, 2023
     * @Modified October 6, 2023
     * @Description Prints a character record
     * @Parameters characterName - name of character that is being searched for characters - list of characters
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public static void searchForCharacter(String characterName, Character[] characters) {
        boolean hasBeenFound = false; // to see if the character has been found
        for (Character character : characters) {
            if (character.getName().equals(characterName)) {
                hasBeenFound = true;
                character.display();
                break;
            }
        } if (!hasBeenFound) System.out.println("Character not found, please try again.");
    }

    /**
     * Method Name: removeCharacter
     * @Author Abhay Manoj
     * @Date October 6, 2023
     * @Modified October 6, 2023
     * @Description Removes a character from the array
     * @Parameters characterName - name of character that is being searched for characters - list of characters
     * @Returns The array of characters minus the removed character, Data Type: Character[]
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public static Character[] removeCharacter(String characterName, Character[] characters) {
        Character[] newCharacters = null; // declaring the new array of characters
        boolean hasBeenFound = false; // to see if the character has been found
        int indexOfCharacter = -1; // the index of the character that is being looked for
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].getName().equals(characterName)) {
                hasBeenFound = true;
                indexOfCharacter = i;
                newCharacters = new Character[characters.length - 1];
                break; }
        } if (hasBeenFound) {
            int newCharactersPointer = 0; // pointer to access indexes in newCharacters array
            for (int i = 0; i < characters.length; i++) {
                if (i == indexOfCharacter) continue;
                newCharacters[newCharactersPointer] = characters[i];
                newCharactersPointer++;
            } return newCharacters;
        } System.out.println("Character was not found, please try again.");
        return characters;
    }

    public static void main(String[] args) throws IOException {
        Character[] characters = new Character[6];
        int recs = CharacterIO.readTextFile("CharacterStats.txt", characters);
        printAllCharacters(characters);
        System.out.println();
        characters = removeCharacter("Ugh SingleTooth", characters);
        System.out.println();
        printAllCharacters(characters);
    }
}
