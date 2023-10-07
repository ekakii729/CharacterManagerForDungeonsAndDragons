/*
 * Author: Abhay Manoj
 * Purpose: Utility class to handle input and output of files
 * Date of Creation: October 04, 2023
 */

package com.ekakii729.charactermanager;
import java.io.*;

public class CharacterIO {

    /**
     * Method Name: readTextFile
     * @Author Abhay Manoj
     * @Date October 4, 2023
     * @Modified October 4, 2023
     * @Description Reads in text file and fills character array with information from file
     * @Parameters fileName - the name of the file, characters - list of characters
     * @Returns The number of characters or records, Data Type: Integer
     * Dependencies: io
     * Throws/Exceptions: N/A
     */

    public static int readTextFile(String fileName, Character[] characters) {
        int i = 0; // used to access indexes in given array
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName)); // used to read the text file
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
                characters[i].setWisdom(Integer.parseInt(reader.readLine()));//
                characters[i].setDexterity(Integer.parseInt(reader.readLine()));
                characters[i].setCharisma(Integer.parseInt(reader.readLine()));
                i++;
            } reader.close();
        } catch (FileNotFoundException FNFe) {
            System.out.println("FILE NOT FOUND, CHECK NAME OF PASSED FILE --> " + FNFe);
        } catch (IOException e) {
            System.out.println("ERROR --> " + e);
        } return i;
    }

    /**
     * Method Name: writeToBinaryFile
     * @Author Abhay Manoj
     * @Date October 4, 2023
     * @Modified October 4, 2023
     * @Description Prints content of characters array to a binary file
     * @Parameters fileName - the name of the file, characters - list of characters
     * @Returns N/A, Data Type: Void
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: IOException
     */

    public static void writeToBinaryFile(String fileName, Character[] characters) throws IOException {
        RandomAccessFile randomAccessor = new RandomAccessFile(fileName, "rw"); // used to write binary file
        for (int i = 0; i < characters.length; i++) characters[i].writeRecord(randomAccessor, i);
        System.out.println(randomAccessor.length());
        randomAccessor.close();
    }

    /**
     * Method Name: readBinaryFile
     * @Author Abhay Manoj
     * @Date October 4, 2023
     * @Modified October 4, 2023
     * @Description Reads content of binary file and puts information in characters array
     * @Parameters fileName - the name of the file, characters - list of characters
     * @Returns The number of records in the file, Data Type: Integer
     * Dependencies: RandomAccessFile
     * Throws/Exceptions: IOException
     */

    public static int readBinaryFile(String fileName, Character[] characters) throws IOException {
        RandomAccessFile randomAccessor = new RandomAccessFile(fileName, "rw"); // used to write binary file
        randomAccessor.setLength(characters.length * Character.getRecordLength());
        int numberOfRecords = (int) (randomAccessor.length() / Character.getRecordLength()); // number of records in the binary file
        for (int i = 0; i < numberOfRecords; i++) {
            characters[i] = new Character();
            characters[i].readRecord(randomAccessor, i);
        } randomAccessor.close();
        return numberOfRecords;
    }
}
