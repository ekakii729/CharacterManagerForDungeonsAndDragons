/*
 * Author: Abhay Manoj
 * Purpose:
 * Date of Creation: October 04, 2023
 */

package com.ekakii729.charactermanager;

public class CharacterManager {
    public static void main(String[] args) {
        String tName = "CharacterStats.txt";
        String bName = "Characters.bin";
        CharacterUtils helper = new CharacterUtils();
        int numOfRecords = helper.getTextNumOfRecords(tName);
        Character[] characters = helper.createCharacterArray(numOfRecords);
        helper.readFromTextFile(tName, characters);
        helper.printAllCharacters(characters);
        helper.writeToFile(bName, characters);
        numOfRecords = helper.getBinaryNumOfRecords(bName);
        characters = helper.createCharacterArray(numOfRecords);
        helper.readFromBinaryFile(bName, characters);
        System.out.println();
        helper.printAllCharacters(characters);
    }
}
