/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesecretbank;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import methods.AccountList;

/**
 *
 * @author Nguyen Ngoc Phuong - SE150998 - SE1506 -LAB211 - LAB0005 - SPR2021
 */
public class TheSecretBank {

    static AccountList accList = new AccountList();

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        Menu mn = new Menu("-----------------------------\n\tThe Secret Bank");
        mn.addMenu("1-Create new account");
        mn.addMenu("2-Login");
        mn.addMenu("3-Withdrawal function");
        mn.addMenu("4-Deposit function");
        mn.addMenu("5-Transfer money");
        mn.addMenu("6-Show Balance");
        mn.addMenu("7-Remove account");
        mn.addMenu("8-Exit");
        int choice = 0;
        String fName = "user.dat", fName1 = "bank.dat";
        accList.loadAccountsFromFile(fName, fName1);
        // print all if the user want to know all the information of list
        accList.printAll();
        do {
            choice = mn.inputChoice();
            switch (choice) {
                case 1:
                    accList.createAccount();
                    break;
                case 2:
                    accList.logInAccount();
                    break;
                case 3:
                    accList.withDrawal();
                    break;
                case 4:
                    accList.deposit();
                    break;
                case 5:
                    accList.transferMoney();
                    break;
                case 6:
                    accList.showAccountList("", true);
                    break;
                case 7:
                    accList.removeAccount();
                    break;
                default:
                    accList.saveToFile(fName, fName1);
                    System.out.println("Thank you!");
                    break;
            }
        } while (choice != mn.menuSize());
    }

}
