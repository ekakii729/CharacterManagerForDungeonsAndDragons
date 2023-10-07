/*
 * Author: Abhay Manoj
 * Purpose:
 * Date of Creation: October 04, 2023
 */

package com.ekakii729.charactermanager;

import java.util.Scanner;

public class CharacterManager {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String tName = "CharacterStats.txt";
        String bName = "Characters.bin";
        CharacterUtils helper = new CharacterUtils();
        int numOfRecords = helper.getTextNumOfRecords(tName);
        Character[] characters = helper.createCharacterArray(numOfRecords);
        helper.readFromTextFile(tName, characters);
        characters = helper.addNewCharacterToList(characters);
        System.out.println();
        helper.writeToFile(bName, characters);
        helper.readFromBinaryFile(bName, characters);
        helper.updateStats(input, characters);
        input.close();
        System.out.println();
        helper.printAllCharacters(characters);
    }
}
