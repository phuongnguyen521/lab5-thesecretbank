/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Nguyen Ngoc Phuong - SE150998 - SE1506 -LAB211 - LAB0005 - SPR2021
 */
public class Validation {

    static Scanner sc = new Scanner(System.in);
    static String enteringValue = "";

    // method for entering value without format
    public static String freeString(String information) {
        System.out.print(information); // showing the announce or information
        enteringValue = sc.nextLine(); // enter value
        return enteringValue;
    }

    // method for checking as format String
    public static String checkStringFormat(String information, String error, String format) {
        while (true) {
            System.out.print(information); //showing the announce
            enteringValue = sc.nextLine().trim(); //enter value as string
            if (enteringValue.isEmpty() || enteringValue.matches(format) == false) { //check if the value is empty or not as format
                System.out.println(error); // show the error
            } else {
                break;
            }
        }
        return enteringValue;
    }

    // method for checking password if it has space or special sign
    public static boolean checkPassword(String password,String format) {
        boolean checkingValue = false;
        if (password.isEmpty()) {
            System.out.println("Password is not empty");
            return checkingValue = true;
        }
        if (password.matches(format)) {
            int length = password.length(); //length is used for checking each character in password
            int counterSign = 0, counterSpace = 0;                // counter is used for counting special character
            for (int i = 0; i < length; i++) {
                char c = password.charAt(i);
                if (!Character.isWhitespace(c)) { // checking if it is space
                    if (!Character.isDigit(c) && !Character.isAlphabetic(c)) { // checking if it is special character
                        counterSign++;
                    }
                } else { // if it is space
                    counterSpace++;
                }
            }
            if (counterSign >= 2 && counterSpace > 0) {
                System.out.println("Not space and not more than 1 special character!");
                checkingValue = true;
            } else if (counterSpace > 0) {
                System.out.println("You should not input space for password!");
                checkingValue = true;
            } else if (counterSign >= 2) {
                System.out.println("You should not type 2 or more special signs!");
                checkingValue = true;
            } else if (counterSign == 0) {
                System.out.println("You should type 1 special sign");
                checkingValue = true;
            }
        } else {
            System.out.println("You should input as format!");
            checkingValue = true;
        }
        return checkingValue;
    }

    // method for checking String for name
    public static String checkStringName(String information, String error, String format) {
        while (true) {
            System.out.print(information);
            enteringValue = sc.nextLine().trim().toLowerCase();
            if (!enteringValue.isEmpty() && enteringValue.contains(" ") == true) { //checking if the value is empty or has the space among words
                break;
            } else if (enteringValue.isEmpty()) { // if the value is empty
                System.out.println("Enter again");
            } else { // if it is not a full name
                System.out.println("Enter Full name");
            }
        }
        // if it is correct as full name, then checking each words is valid or not
        enteringValue = checkName(information, enteringValue, error, format);
        // if it is valid full name, return it
        return enteringValue;
    }

    // method for checking each word in full name
    public static String checkName(String information, String name, String error, String format) {
        String rep = "";
        StringTokenizer temp = new StringTokenizer(name, " "); // split full name into word without spacing, put them into temp
        name = "";
        while (temp.hasMoreTokens()) { // if temp has more tokens
            rep = temp.nextToken().trim(); // rep will be next word of temp
            boolean match = rep.matches(format); //checking if it as format
            if (match == false) { // if it true
                name = name + " " + rep.substring(0, 1).toUpperCase() + rep.substring(1); // uppercase the first character in word 
                //and put it into the next word of name
                rep = "";
            } else { // if it is not as format
                System.out.println(error); // showing the error
                name = checkStringName(information, error, format); //enter full name again
            }
        }
        // if it is valid full name, return it
        return name.trim();

    }

    // method for checking number from min value to max value
    public static int checkInputMenu(String information, String error, int min, int max) {
        int enteringInteger = 0;
        if (max < min) { // checking if the min is larger than max, put it into correct value
            int temp = min;
            min = max;
            max = min;
        }
        while (true) {
            try {
                System.out.print(information);
                enteringInteger = Integer.parseInt(sc.nextLine().trim());
                if (enteringInteger < min || enteringInteger > max) { // if value is out of the range between min and max
                    throw new Exception(); //show the error through catch
                }
                // return if it is correct
                return enteringInteger;
            } catch (Exception e) {
                System.out.println(error);
            }
        }
    }

    // checking for double value in the range between min and max
    public static double checkDouble(String information, String error, double min, double max, int i) {
        double enteringDouble = 0;
        if (max < min) { // checking if the min is larger than max, put it into correct value
            double temp = min;
            min = max;
            max = min;
        }
        switch (i) {
            case 1:
                while (true) { // checking value between min and max
                    try {
                        System.out.print(information);
                        enteringDouble = Double.parseDouble(sc.nextLine().trim());
                        if (enteringDouble < min || enteringDouble > max) {
                            throw new Exception();
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println(error);
                    }
                }
                break;
            case 2:
                while (true) {
                    try { // checking value by min
                        System.out.print(information);
                        enteringDouble = Double.parseDouble(sc.nextLine().trim());
                        if (enteringDouble < 10000) { // if the value is smaller than 10000
                            System.out.println("Money should be larger or equal to VNĐ 10,000");
                        } else { //if it is valid number
                            break;
                        }
                    } catch (NumberFormatException e) { // if the value is not a number
                        System.out.println(error);
                    }
                }
                break;
        }
        return enteringDouble;
    }

    // checking option Y or N
    public static boolean checkYN(String information) {
        boolean check = false;
        enteringValue = checkStringFormat(information, "Enter Y or N", "[Y|y|N|n]{1}"); // enter value as Y or N through checkStringFormat
        if (enteringValue.equals("y") || enteringValue.equals("Y")) { // if value is Y or y
            check = true;
        }
        return check;
    }

    // checking exist file
    public static boolean checkExistFile(String filename) {
        boolean result = false;
        File f = new File(filename);
        if (f.exists()) {
            result = true;
        }
        return result;
    }

    //method for checking file
    public static String checkFileName(String kind, boolean savingOrNot) {
        kind = kind.trim();
        while (true) {
            String fileName = "";
            System.out.println("\n--------------------------------------------------");
            while (true) {
                fileName = freeString("Enter file name " + kind + "[filename.dat]: ");
                if (fileName.isEmpty()) { //if filename is empty
                    System.out.println("File name should not be blank. Enter again");

                    // if the filename is not as format or not containing .dat as Lab requirement
                } else if (fileName.matches("^[\\w,\\s-]+\\.[A-Za-z]{3}$") == false || fileName.contains(".dat") == false) {
                    System.out.println("File name should type as format [filename.dat]");
                } else { // if the filename is valid
                    break;
                }
            }
            if (checkExistFile(fileName)) { // check the filename is existed or not
                return fileName;
            } else { // if the filename is not existed
                if (savingOrNot) { // if the method caller is method for saving file
                    System.out.println("Cannot find " + fileName);
                    return checkSaveToFile(fileName, kind);
                } else {
                    // if the method caller is not method for saving file
                    // asking user to type another file or not
                    // If 'Y' then continue, otherwise it will return the method caller
                    boolean checkValue = checkYN(fileName + " is not existed.\n"
                            + "Do you want to import other file name (Y/N)? ");
                    if (checkValue == false) {
                        return fileName = "";
                    }
                }
            }
        }
    }

    //method for checking saving file
    public static String checkSaveToFile(String fileName, String kind) {
        int choice = 0;
        String result = "";
        String subMenu = "Do you want to"
                + "\n1-Enter other file(.dat) to save"
                + "\n2-Create file(.dat) to save"
                + "\nEnter choice: ";
        String error = "Enter 1 - 2";
        while (true) {
            choice = checkInputMenu(subMenu, error, 1, 2);
            if (choice == 1) { //enter other file
                result = checkFileName(kind, true);
                return result;
            }
            if (choice == 2) { //create new file to save
                try {
                    while (true) {
                        fileName = freeString("Enter file name to create " + kind + "[filename.dat]: ");
                        if (fileName.contains(".dat") == false || fileName.isEmpty() || fileName.matches("^[\\w,\\s-]+\\.[A-Za-z]{3}$") == false) {
                            System.out.println("Enter again");
                        } else {
                            break;
                        }
                    }
                    File f = new File(fileName);
                    if (!f.exists()) { //if the file is not existed
                        f.createNewFile(); //create new file
                        System.out.println(fileName + " is created");
                        return result = fileName;
                    } else {//if the file is existed
                        return result = fileName;
                    }
                } catch (IOException e) {
                }
            }
        }
    }
}
