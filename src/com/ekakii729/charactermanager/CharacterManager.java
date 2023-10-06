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
        boolean hasBeenFound = false;
        for (Character character : characters) {
            if (character.getName().equals(characterName)) {
                hasBeenFound = true;
                character.display();
                break;
            }
        } if (!hasBeenFound) System.out.println("ERRor");
    }

    public static void main(String[] args) throws IOException {
        Character[] characters = new Character[6];
        int recs = CharacterIO.readTextFile("CharacterStats.txt", characters);
        printAllCharacters(characters);
        System.out.println();
        searchForCharacter("Ugh SingleTooth", characters);
        System.out.println();
    }
}
