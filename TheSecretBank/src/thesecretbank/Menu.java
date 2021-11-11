/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thesecretbank;

import java.util.ArrayList;
import util.Validation;

/**
 *
 * @author Nguyen Ngoc Phuong - SE150998 - SE1506 -LAB211 - LAB0005 - SPR2021
 */
public class Menu {

    private final String menuTitle;
    ArrayList<String> mainMenu = new ArrayList<>();

    public Menu(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public int menuSize() {
        return mainMenu.size();
    }

    public void addMenu(String line) {
        mainMenu.add(line);
    }

    public int inputChoice() {
        System.out.println(menuTitle);
        for (String main : mainMenu) {
            System.out.println(main.toString());
        }
        int choice = Validation.checkInputMenu("Enter choice: ", "Enter 1 -" + mainMenu.size(), 1, mainMenu.size());
        return choice;
    }

}
