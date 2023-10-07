/*
 * Author: Abhay Manoj
 * Purpose:
 * Date of Creation: October 04, 2023
 */

package com.ekakii729.charactermanager;

import java.io.*;
import java.util.Scanner;

public class CharacterManager {

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

    public static int getBinaryNumOfRecords(String fileName) {
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

    public static int getTextNumOfRecords(String fileName) {
        int numberOfRecords = 0; // number of records within the file
        final int NUMBER_OF_LINES_IN_RECORD = 11; // 11 lines in 1 record
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
        } return numberOfRecords / NUMBER_OF_LINES_IN_RECORD;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String tName = "CharacterStats.txt";
        String bName = "Characters.bin";
        int numOfRecords = getTextNumOfRecords(tName);
        CharacterUtils helper = new CharacterUtils(numOfRecords);
        helper.readFromTextFile(tName);
        System.out.println();
        helper.addNewCharacterToList();
        System.out.println();
        helper.writeToFile(bName);
        helper.readFromBinaryFile(bName);
        helper.updateStats(input);
        input.close();
        System.out.println();
        helper.printAllCharacters();
    }
}