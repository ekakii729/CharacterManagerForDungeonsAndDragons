/*
 * Author: Abhay Manoj
 * Purpose: Utility class for the Character Manager
 * Date of Creation: October 04, 2023
 */

package com.ekakii729.charactermanager;

import java.io.*;

public class CharacterUtils {

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
        try {
            BufferedReader recordPointer = new BufferedReader(new FileReader(fileName)); // accesses the records within the file
            while (recordPointer.ready()) {
                recordPointer.readLine();
                numberOfRecords++;
            } recordPointer.close();
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND ERROR, CHECK WHERE FILE WAS PLACED --> " + e);
        } catch (IOException e) {
            System.out.println("I/O ERROR --> " + e);
        } return numberOfRecords / 11; // 11 lines in 1 record
    }

    /** Method Name: createCharacterArray
     * @Author Abhay Manoj
     * @Date October 7, 2023
     * @Modified October 7, 2023
     * @Description Returns null array of characters
     * @Parameters size - size of array
     * @Returns Array of characters, Data Type: Character[]
     * Dependencies: N/A
     * Throws/Exceptions: N/A
     */

    public static Character[] createCharacterArray(int size) {
        return new Character[size];
    }
}
