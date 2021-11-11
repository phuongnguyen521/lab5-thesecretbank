/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import java.util.ArrayList;
import data.Accounts;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import util.Validation;

/**
 *
 * @author Nguyen Ngoc Phuong - SE150998 - SE1506 -LAB211 - LAB0005 - SPR2021
 */
public class AccountList extends ArrayList<Accounts> {

    // id, position for login
    private String id = "";
    private int position = -1;
    private final DecimalFormat currency = new DecimalFormat("VNĐ #,###,###"); // format currency
    String fName = "user.dat", fName1 = "bank.dat";

    // finding account as require (element)
    // choice used for finding as id or password
    public int searchAccount(String element, int choice) {
        int positionElement = -1;
        if (this.isEmpty()) {
            return position;
        }
        switch (choice) {
            case 1: // finding element by id
                positionElement = this.indexOf(new Accounts(element));
                break;
            case 2: // finding element by password
                for (int i = 0; i < this.size(); i++) {
                    if (this.get(i).getPassword().equals(element)) {
                        positionElement = i;
                        break;
                    }
                }
                break;
        }
        return positionElement;
    }

    // create account
    public void createAccount() throws NoSuchAlgorithmException, IOException {
        String idAcc = "", name = "", password = "", confirmPassword = "";
        int positionAcc = 0;
        Accounts acc = null;
        System.out.println("------------------------------------------------\n"
                + "\tCreate new Account\n");
        do {
            idAcc = enterValue(1, "");
            if ((positionAcc = searchAccount(idAcc, 1)) > -1) { // check if id is existed or not
                System.out.println(idAcc + " is existed. Enter another ID");
            }
        } while (positionAcc > -1);
        name = enterValue(2, ""); //enter name
        System.out.println("------------------------------------------------\n"
                + "Password should at least 6 character including\n"
                + "Capitals, lower characters and special signs\n"
                + "The special signs must have one of these: .,#?!@$%^&*-_\n"
                + "------------------------------------------------");
        password = enterValue(3, "Password: "); // enter password
        System.out.println("Confirm password (enter as the same password)");
        do {
            confirmPassword = enterValue(3, "Confirm password: ");
            if (!(confirmPassword.equals(password)) == true) { //check if confirm is same as password
                System.out.println("Enter confirm password again");
            }
        } while (!confirmPassword.equals(password));
        acc = new Accounts(idAcc, name, toHexString(getSHA(password)), 0);
        this.add(acc);
        System.out.println("\n--------------------------------------------\n"
                + "\t**Create new account successfully**\n");
        showAccountList(idAcc, false);
        System.out.println("\n--------------------------------------------\n");
        saveToFile(fName, fName1);
    }

    public boolean logInAccount() throws NoSuchAlgorithmException {
        String idLogin = "", passwordLogin = "";
        int positionPass = -1, positionIDlogin = -1;
        boolean checkingValue = true;
        if (this.isEmpty()) { // if the list empty
            System.out.println("You should create account");
            checkingValue = false;
        } else { //if the list has some element
            System.out.println("---------------------------------------\n"
                    + "\tLogin Account\n");
            while (checkingValue) {
                idLogin = enterValue(1, ""); // enter id
                System.out.println("------------------------------------------------\n"
                        + "Password should at least 6 character including\n"
                        + "Capitals, lower characters and special signs\n"
                        + "The special signs must have one of these: .,#?!@$%^&*-_\n"
                        + "------------------------------------------------");
                passwordLogin = enterValue(3, "Password: "); // enter password
                positionIDlogin = searchAccount(idLogin, 1); //find position of id
                positionPass = searchAccount(toHexString(getSHA(passwordLogin)), 2); //find position of password
                if (positionIDlogin > -1 && positionPass > -1) { // if id and password is existed
                    System.out.println("\n\t** Login successfully **\n");
                    this.id = idLogin;
                    this.position = positionIDlogin;
                    break;
                } else {
                    if (positionIDlogin == -1) { // if id is not existed
                        System.out.println("Cannot find ID " + id + "!");
                    } else if (positionIDlogin > -1 && positionPass == -1) { // if id is existed but password is incorrect
                        System.out.println("Password is incorrect!");
                    }
                    // if id or password is incorrect, asking user to continue or not
                    // if 'Y' then login again, else back to main menu 
                    checkingValue = Validation.checkYN("Do you want to login again (Y/N)? ");
                    if (checkingValue) {
                        System.out.println();
                    }
                }
            }
        }
        return checkingValue;
    }

    public void showAccountList(String id, boolean showInMenu) throws NoSuchAlgorithmException {
        if (showInMenu) { // checking if showing account to user in menu
            if (this.id.isEmpty()) { // if id login is not login, then go to method LoginAccount
                logInAccount();
            } // showing information of id Login
            System.out.println("------------------------------------------------\n"
                    + this.get(position).toString(1)
                    + "\n------------------------------------------------\n");
        } else { // if showing account as the methods in this class required
            if (!this.isEmpty()) { // if the list is not empty
                int posisitionElement = searchAccount(id, 1);
                if (posisitionElement > -1) {
                    System.out.println(this.get(posisitionElement).toString(1)); // showing the information of that id
                } else {
                    System.out.println("id " + id + "is not existed");
                }
            } else { // if the list is empty
                System.out.println("Empty Account list");
            }
        }
    }

    // withdrawal
    public void withDrawal() throws NoSuchAlgorithmException, IOException {
        if (checkLogin()) { // check if login or not
            boolean checkingValue = true;
            double moneyRequest = 0;
            System.out.println("------------------------------------------------\n"
                    + "\tWithDrawal\n");
            while (checkingValue) {
                System.out.println("Money withdraw must be more than or equal to VNĐ 10,000");
                moneyRequest = Double.parseDouble(enterValue(4, "Money withdraw: ")); // enter money Request
                if (updateBalance(moneyRequest, "withdraw") == false) { // if money is larger than balanceAccounts
                    checkingValue = Validation.checkYN("Do you want to withdraw again (Y/N)?"); // asking user to continue to withdraw money or not
                    if (checkingValue == false) { // if 'N' then announce to user and back to main menu
                        System.out.println("\t**Withdrawal fail**\n");
                    }
                } else {
                    saveToFile(fName, fName1);
                    break;
                }
            }
        }
    }

    // update Balance
    public boolean updateBalance(double moneyRequest, String kind) {
        double balanceMoney = this.get(position).getBalanceAmount(); // get the balance in account firstly
        if (balanceMoney >= moneyRequest) { // if moneyRequest is suitable
            balanceMoney -= moneyRequest;
            this.get(position).setBalanceAmount(balanceMoney);
            System.out.println("\n\t**Withdraw successully**\n"
                    + currency.format(moneyRequest) + " has been " + kind + "\n"
                    + "\n----------------------------------------------\n"
                    + this.get(position).toString(1)
                    + "\n----------------------------------------------\n");
            return true;
        } else { // if moneyRequest is larger than balance in account
            System.out.println("Money " + kind + " is larger than " + currency.format(balanceMoney));
        }
        return false;
    }

    // deposit
    public void deposit() throws NoSuchAlgorithmException, IOException {
        if (checkLogin()) { // check if login or not
            System.out.println("------------------------------------------------\n"
                    + "\tDeposit\n");
            double moneyDeposit = 0;
            System.out.println("Deposit must be larger or equal to VNĐ 10,000");
            moneyDeposit = Double.parseDouble(enterValue(4, "Deposit: ")); // enter money for depositing
            boolean checkingValue = Validation.checkYN("Add deposit " + currency.format(moneyDeposit) + " into your account " + id + " (Y/N)? "); // asking if user want to add this money or not
            if (checkingValue) { // if 'Y'
                moneyDeposit += this.get(position).getBalanceAmount();
                this.get(position).setBalanceAmount(moneyDeposit);
                System.out.println("\n\t**Add deposit successfully**\n");
                showAccountList(id, true);
                saveToFile(fName, fName1);
            } else { //if 'N' then annouce fail and back to main menu
                System.out.println("\n\t**Add deposit fail**\n");
            }
        }
    }

    // transfer money
    public void transferMoney() throws NoSuchAlgorithmException, IOException {
        if (checkLogin()) { // check if login or not
            String receiver = "";
            int positionRec = -1;
            boolean checkingValue = true;
            System.out.println("------------------------------------------------\n"
                    + "\tTransfer Money\n");
            while (checkingValue) {
                receiver = enterValue(5, ""); // enter id receiver
                if (receiver.equals(id)) { // if reciever is same as id login 
                    System.out.println("Enter other ID or name");
                } else {
                    // search for receiver is existed or 
                    positionRec = searchAccount(receiver, 1);
                    if (positionRec == -1) { // if receiver is not existed
                        System.out.println("Cannot find " + receiver);
                        checkingValue = Validation.checkYN("Do you want to enter receiver again(Y/N)? "); // asking user to enter again or not?
                        if (checkingValue == false) { // if 'N' then announce fail
                            System.out.println("\t**Transfer fail**\n");
                        }
                    } else { // if receiver is existed
                        transferMoneyDetail(this.get(positionRec).getId(), positionRec);
                        break;
                    }
                }
            }
        }
    }

    // this method is for entering moneyTransfer and update 2 account transferer and receiver
    public void transferMoneyDetail(String idRec, int positionRec) throws NoSuchAlgorithmException, IOException {
        double moneyTransfer = 0;
        boolean checkingValue = true;
        while (checkingValue) {
            System.out.println("Money to tranfer should more than or equal to VNĐ 10,000");
            moneyTransfer = Double.parseDouble(enterValue(4, "Money transfer: ")); // enter moneyTransfer
            if (updateBalance(moneyTransfer, "transfer") == false) { // if moneyTransfer is larger than balance of transfer account
                checkingValue = Validation.checkYN("Do you want to enter money again (Y/N)?"); // asking if user want to enter money or 
                if (checkingValue == false) { // if 'N' then announce and back to main menu
                    System.out.println("\t**Transfer fail**\n");
                }
            } else { // if moneyTransfer is suitable, then transfer money to receiver
                moneyTransfer += this.get(positionRec).getBalanceAmount();
                this.get(positionRec).setBalanceAmount(moneyTransfer);
                System.out.println("\t**Transfer successfully**\n");
                saveToFile(fName, fName1);
                break;
            }
        }
    }

    // remove account
    public void removeAccount() throws NoSuchAlgorithmException, IOException {
        if (checkLogin()) { // check if login or not
            System.out.println("------------------------------------------------\n"
                    + "\tRemove Account\n");
            if (Validation.checkYN("Do you want to remove this account " + id + " (Y/N)? ")) { // asking if user want to remove account or not
                // if 'Y' then remove and log out the account
                this.remove(position);
                System.out.println("\n**Remove account " + id + " successfully**\n");
                id = "";
                position = -1;
                saveToFile(fName, fName1);
            } else { // if 'N' then announce and back to main menu
                System.out.println("\n**Remove account " + id + " fail**\n");
            }
        }
    }

    // for enter attribute as require from methods of this class
    public String enterValue(int choice, String information) {
        String enter = "";
        double minMoney = 10000;
        boolean checkingValue = true;
        switch (choice) {
            case 1: // enter id
                enter = Validation.checkStringFormat("ID xxxx(at least 1 number): ", "Just number", "^\\d{1,}$");
                break;
            case 2: // enter full name
                enter = Validation.checkStringName("Full name: ", "Just characters, not numbers or special signs", "^.*(?=.*[^A-Za-z]){2,}.*$");
                break;
            case 3: // enter password or confirm password
                while (checkingValue) {
                    enter = Validation.freeString(information);
                    checkingValue = Validation.checkPassword(enter, "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
                }
                break;
            case 4: // enter money
                enter += Validation.checkDouble(information, "Enter number!\n", minMoney, 0, 2);
                break;
            case 5: // enter receiver id
                enter = Validation.checkStringFormat("ID receiver xxxx(at least 1 number): ", "Just number", "^\\d{1,}$");
                break;
            case 6:
                break;
        }
        return enter.trim();
    }

    // encrypted as SHA256
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    // encrypted into HexString
    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    // load accounts from file to list
    public void loadAccountsFromFile(String fName, String fName1) throws ClassNotFoundException, NoSuchAlgorithmException {
        int counter = 0;
        try {
            File f = new File(fName);
            if (!f.exists()) { // if f is not existed
                return;
            }
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream oi = new ObjectInputStream(fi);
            Accounts acc;
            // load user file to list
            while (fi.available() > 0) {
                counter = 1;
                acc = (Accounts) oi.readObject();
                if (searchAccount(acc.getId(), 1) == -1) {
                    this.add(acc);
                }
            }
            // load bank file to list
            f = new File(fName1);
            fi = new FileInputStream(f);
            oi = new ObjectInputStream(fi);
            while (fi.available() > 0) {
                acc = (Accounts) oi.readObject();
                int positionOfElement = searchAccount(acc.getId(), 1);
                if (positionOfElement > -1) {
                    this.get(positionOfElement).setBalanceAmount(acc.getBalanceAmount());
                }
            }
            fi.close();
            oi.close();
            if (counter == 1) {
                System.out.println("Load accounts from " + fName + " and " + fName1 + " successfully!");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // save list to file
    public void saveToFile(String fName, String fName1) throws FileNotFoundException, IOException {
        if (this.isEmpty()) {
            System.out.println("Empty list");
        } else {
            try {
                File f = new File(fName);
                if (!f.exists()) { // if user file is not existed
                    fName = Validation.checkFileName("user file", true);
                }
                File f1 = new File(fName1);
                if (!f1.exists()) { // if bank file is not existed
                    fName1 = Validation.checkFileName("bank file", true);
                }
                FileOutputStream fo = new FileOutputStream(fName);
                ObjectOutputStream oo = new ObjectOutputStream(fo);
                for (Accounts acc : this) { // load list user into user file as object
                    Accounts acc1 = new Accounts(acc.getId(), acc.getName(), acc.getPassword());
                    oo.writeObject(acc1);
                }
                fo = new FileOutputStream(fName1);
                oo = new ObjectOutputStream(fo);
                for (Accounts acc : this) { // load list bank into bank file as object
                    Accounts acc2 = new Accounts(acc.getId(), acc.getBalanceAmount());
                    oo.writeObject(acc2);
                }
                fo.close();
                oo.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    // show all the accounts
    public void printAll() {
        for (Accounts acc : this) {
            System.out.println(acc.toString(3));
        }
    }

    // check if login or not
    public boolean checkLogin() throws NoSuchAlgorithmException {
        if (id.isEmpty()) { // if id is not login yet
            return logInAccount();
        }
        // if id is login
        return true;
    }
}
