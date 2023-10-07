/*
 * Author: Abhay Manoj
 * Purpose: Represents a character in Dungeons and Dragons, contains various methods to manipulate stats of the character as well as read and write to binary file
 * Date of Creation: October 02, 2023
 */

package com.ekakii729.charactermanager;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Random;

public class Character {

    private String name; // the name of the character
    private String race; //  the race of the character
    private String classOfCharacter; // the class of the character
    private int level; // the level of the character
    private int hitPoints; // the hit points of the character
    private int strength; //  the strength of the character
    private int constitution; // the constitution of the character
    private int intelligence; // the intelligence of the character
    private int wisdom; // the wisdom of the character
    private int dexterity; // the dexterity of the character
    private int charisma; // the charisma of the character
    private final static int MAX_STRING_LENGTH = 20; // a string in this program can only be 20 characters long
    private final static long RECORD_LENGTH = 152; // the max length of a record is 152 bytes
    private final static Random random = new Random(); // a random object used to generate random numbers

    public Character() {
        name = null;
        race = null;
        classOfCharacter = null;
        level = -1;
        hitPoints = -1;
        strength = -1;
        constitution = -1;
        intelligence = -1;
        wisdom = -1;
        dexterity = -1;
        charisma = -1;
    }

    public Character(String name, String race, String classOfCharacter, int level, int hitPoints, int strength, int constitution, int intelligence, int wisdom, int dexterity, int charisma) {
        this.name = name;
        this.race = race;
        this.classOfCharacter = classOfCharacter;
        this.level = level;
        this.hitPoints = hitPoints;
        this.strength = strength;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.dexterity = dexterity;
        this.charisma = charisma;
    }

    /** Method Name: diceRoll
     * @Author Abhay Manoj
     * @Date October 2, 2023
     * @Modified October 2, 2023
     * @Description Generates a random number from 1 to the number passed in
     * @Parameters numberOfSides - the number of sides of the dice being rolled
     * @Returns The random number that was rolled, Data Type: Integer
     * Dependencies: Random
     * Throws/Exceptions: N/A
     */

    private int diceRoll(int numberOfSides) {
        return random.nextInt(numberOfSides) + 1;
    }

    /** Method Name: removeSmallestElement
     * @Author Abhay Manoj
     * @Date October 3, 2023
     * @Modified October 3, 2023
     * @Description Removes the smallest element in the array
     * @Parameters array - the array to be shortened
     * @Returns The array without its smallest element, Data Type: Integer[]
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    private int[] removeSmallestElement(int[] array) {
        Arrays.sort(array);
        int[] newArray = new int[array.length - 1]; // an array with a smaller size than the original by 1
        int newArrayCounter = 0; // counter used to access elements in the newArray
        for (int i = 1; i < array.length; i++) {
            newArray[newArrayCounter] = array[i];
            newArrayCounter++;
        } return newArray;
    }

    /** Method Name: generateStat
     * @Author Abhay Manoj
     * @Date October 3, 2023
     * @Modified October 3, 2023
     * @Description Generates a random number for a stat of the character
     * @Parameters N/A
     * @Returns The sum of the rolls, Data Type: Integer
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    private int generateStat() {
        final int NUMBER_OF_ROLLS = 4; // the number of rolls that will be done
        final int NUMBER_OF_SIDES = 6; //  the number of sides on the dice
        int[] rolls = new int[NUMBER_OF_ROLLS]; // an array containing all the rolls
        for (int i = 0; i < NUMBER_OF_ROLLS; i++) rolls[i] = diceRoll(NUMBER_OF_SIDES);
        rolls = removeSmallestElement(rolls);
        int rollSum = 0; // the sum of the elements in the rolls array
        for (int i : rolls) rollSum += i;
        return rollSum;
    }

    /** Method Name: generateStats
     * @Author Abhay Manoj
     * @Date October 3, 2023
     * @Modified October 3, 2023
     * @Description Assigns each stat to a number from 3 to 18
     * @Parameters N/A
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void generateStats() {
        hitPoints = generateStat();
        strength = generateStat();
        constitution = generateStat();
        intelligence = generateStat();
        wisdom = generateStat();
        dexterity = generateStat();
        charisma = generateStat();
    }

    /** Method Name: calculateIncreaseOfHitPoints
     * @Author Abhay Manoj
     * @Date October 3, 2023
     * @Modified October 3, 2023
     * @Description Calculates the increase of hit points based on character class
     * @Parameters N/A
     * @Returns The needed increase of hit points, Data Type: Integer
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    private int calculateIncreaseOfHitPoints() {
        int increaseHitPoints = 0; // the amount that the characters hit points is going to increase
        final int MAX_WARRIOR_INCREASE = 10; // warriors hit points go up by 10 points max
        final int MAX_CLERIC_INCREASE = 8; // clerics hit points go up by 8 points max
        final int MAX_ROGUE_INCREASE = 6; // rogues hit points go up by 6 points max
        final int MAX_MAGE_INCREASE = 4; // mages hit points go up by 4 points max
        switch (classOfCharacter) {
            case "Warrior" -> increaseHitPoints = diceRoll(MAX_WARRIOR_INCREASE);
            case "Cleric" -> increaseHitPoints = diceRoll(MAX_CLERIC_INCREASE);
            case "Ranger", "Bard", "Rogue", "Range" -> increaseHitPoints = diceRoll(MAX_ROGUE_INCREASE);
            case "Mage" -> increaseHitPoints = diceRoll(MAX_MAGE_INCREASE);
            default -> {
            }
        } return increaseHitPoints;
    }

    /** Method Name: levelUp
     * @Author Abhay Manoj
     * @Date October 3, 2023
     * @Modified October 3, 2023
     * @Description Levels up the character while applying hit point increases as per their class
     * @Parameters numberOfLevels - the number of levels that the character has increased since last updating
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void levelUp(int numberOfLevels) {
        int newHitPoints = 0; // the amount of new hit points that will be generated due to the level increase
        for (int i = 0; i < numberOfLevels; i++) newHitPoints += calculateIncreaseOfHitPoints();
        level += numberOfLevels;
        hitPoints += newHitPoints;
    }

    /** Method Name: changeClass
     * @Author Abhay Manoj
     * @Date October 3, 2023
     * @Modified October 3, 2023
     * @Description Changes the class of a character, adjusts their hit points accordingly
     * @Parameters newClass - the class the user wants to convert their character to
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void changeClass(String newClass) {
        int characterLevel = level; // saving the level of the character
        classOfCharacter = newClass;
        hitPoints = 0;
        level = 0;
        levelUp(characterLevel);
    }

    /** Method Name: convertToHuman
     * @Author Abhay Manoj
     * @Date October 3, 2023
     * @Modified October 3, 2023
     * @Description Converts character to a human, with the necessary stat changes
     * @Parameters N/A
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    private void convertToHuman() {
        final String NEW_RACE = "Human"; // the race that the character will be changed to
        switch (race) {
            case "Halfling" -> {
                dexterity -= 2;
                constitution--;
            } case "Elf" -> {
                dexterity -= 3;
                constitution += 2;
            } case "Dwarf" -> constitution -= 3;
            case "Orc" -> {
                strength -= 3;
                constitution--;
            } case "Gnome" -> {
                intelligence -= 2;
                wisdom--;
                strength += 3;
            } default -> {}
        } race = NEW_RACE;
    }

    /** Method Name: changeRace
     * @Author Abhay Manoj
     * @Date October 3, 2023
     * @Modified October 3, 2023
     * @Description Converts character to a different race, with necessary stat changes
     * @Parameters N/A
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void changeRace(String newRace) {
        convertToHuman();
        switch (newRace) {
            case "Halfling" -> {
                dexterity += 2;
                constitution++;
            } case "Elf" -> {
                dexterity += 3;
                constitution -= 2;
            } case "Dwarf" -> constitution += 3;
            case "Orc" -> {
                strength += 3;
                constitution++;
            } case "Gnome" -> {
                intelligence += 2;
                wisdom++;
                strength -= 3;
            } default -> {}
        }race = newRace;
    }

    /**
     * Method Name: writeString
     * @Author Abhay Manoj
     * @Date October 4, 2023
     * @Modified October 4, 2023
     * @Description Writes a string to a binary file
     * @Parameters randomAccessor - binary file accessor, string - string to be written
     * @Returns N/A, Data Type: Void
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: IOException
     */

    private void writeString(RandomAccessFile randomAccessor, String string) throws IOException {
        int stringLength = string.length(); // the length of the string
        if (stringLength > MAX_STRING_LENGTH) stringLength = MAX_STRING_LENGTH;
        int padLength = MAX_STRING_LENGTH - stringLength; // the amount of characters that is needed to pad the name to 20 total characters
        for (int i = 0; i < stringLength; i++) randomAccessor.writeChar(string.charAt(i));
        if (padLength > 0) for (int i = 0; i < padLength; i++) randomAccessor.writeChar(' ');
    }

    /**
     * Method Name: writeRecord
     * @Author Abhay Manoj
     * @Date October 4, 2023
     * @Modified October 4, 2023
     * @Description Prints values of record to a binary file
     * @Parameters randomAccessor - binary file accessor, numberOfRecord - the number of the record
     * @Returns N/A, Data Type: Void
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: IOException
     */

    public void writeRecord(RandomAccessFile randomAccessor, int numberOfRecord) throws IOException {
        randomAccessor.seek(numberOfRecord * RECORD_LENGTH);
        writeString(randomAccessor, name);
        writeString(randomAccessor, race);
        writeString(randomAccessor, classOfCharacter);
        randomAccessor.writeInt(level);
        randomAccessor.writeInt(hitPoints);
        randomAccessor.writeInt(strength);
        randomAccessor.writeInt(constitution);
        randomAccessor.writeInt(intelligence);
        randomAccessor.writeInt(wisdom);
        randomAccessor.writeInt(dexterity);
        randomAccessor.writeInt(charisma);
    }

    /**
     * Method Name: readString
     * @Author Abhay Manoj
     * @Date October 4, 2023
     * @Modified October 4, 2023
     * @Description Reads a string from a binary file
     * @Parameters randomAccessor - binary file accessor
     * @Returns The string that was read, Data Type: String
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: IOException
     */

    private String readString(RandomAccessFile randomAccessor) throws IOException {
        StringBuilder currentString = new StringBuilder(); // used to put characters together, better performance than using string
        for (int i = 0; i < MAX_STRING_LENGTH; i++) currentString.append(randomAccessor.readChar());
        return currentString.toString().trim();
    }

    /**
     * Method Name: readRecord
     * @Author Abhay Manoj
     * @Date October 4, 2023
     * @Modified October 4, 2023
     * @Description Reads values from a record of a binary file and assigns values to variables of character
     * @Parameters randomAccessor - binary file accessor, numberOfRecord - the number of the record
     * @Returns N/A, Data Type: Void
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: IOException
     */

    public void readRecord(RandomAccessFile randomAccessor, int numberOfRecord) throws IOException {
        randomAccessor.seek(numberOfRecord * RECORD_LENGTH);
        name = readString(randomAccessor);
        race = readString(randomAccessor);
        classOfCharacter = readString(randomAccessor);
        level = randomAccessor.readInt();
        hitPoints = randomAccessor.readInt();
        strength = randomAccessor.readInt();
        constitution = randomAccessor.readInt();
        intelligence = randomAccessor.readInt();
        wisdom = randomAccessor.readInt();
        dexterity = randomAccessor.readInt();
        charisma = randomAccessor.readInt();
    }

    /**
     * Method Name: display
     * @Author Abhay Manoj
     * @Date October 4, 2023
     * @Modified October 4, 2023
     * @Description Displays all attributes of character
     * @Parameters N/A
     * @Returns N/A, Data Type: Void
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public void display() {
        System.out.printf("Name: %s, Race: %s, Class: %s, Level: %d, HitPoints: %d, Strength: %d, Constitution: %d, Intelligence: %d, Wisdom: %d, Dexterity: %d, Charisma: %d\n", name, race, classOfCharacter, level, hitPoints, strength, constitution, intelligence, wisdom, dexterity, charisma);
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        if (!race.equals("Human") && !race.equals("Elf") && !race.equals("Halfling") && !race.equals("Dwarf") && !race.equals("Orc") && !race.equals("Gnome")) throw new IllegalArgumentException();
        this.race = race;
    }

    public String getClassOfCharacter() {
        return classOfCharacter;
    }

    public void setClassOfCharacter(String classOfCharacter) {
        if (!classOfCharacter.equals("Warrior") && !classOfCharacter.equals("Bard")  && !classOfCharacter.equals("Rogue") && !classOfCharacter.equals("Cleric") && !classOfCharacter.equals("Ranger") && !classOfCharacter.equals("Mage")) throw new IllegalArgumentException();
        this.classOfCharacter = classOfCharacter;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public static int getMaxStringLength() {
        return MAX_STRING_LENGTH;
    }

    public static long getRecordLength() {
        return RECORD_LENGTH;
    }
}