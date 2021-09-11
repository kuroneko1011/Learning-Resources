package View;

import Control.FoodList;
import java.util.Scanner;

public class FoodListManagement {
    public static void main(String[] args) {

        FoodList foodList = new FoodList();
        String fileName = "FOOD.DAT";

        Scanner sc = new Scanner(System.in);
        int choice = 0;
        boolean check = true;

        do {
            System.out.println();
            System.out.println("\nWelcome to Food Management - @ 2021 by SE160120 - Ho Trong Nhan");
            System.out.println("1.Add a new food");
            System.out.println("2.Search a food by name");
            System.out.println("3.Remove the food by ID");
            System.out.println("4.Print the food list in the descending order of expired date");
            System.out.println("5.Store the food to file");
            System.out.println("6.Quit");
            System.out.println("----------------------");
            System.out.println();
            System.out.println("Your choice is: ");

            do {
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice < 1 || choice > 6)
                        throw new Exception();
                    check = false;
                } catch (Exception e) {
                    System.out.println("Please enter from 1 to 6");
                    check = true;
                }
            } while (check);

            switch (choice) {
                case 1:
                    foodList.add();
                    break;
                case 2:
                    foodList.searchFoodByName();
                    break;
                case 3:
                    foodList.removeFoodByID();
                    break;
                case 4:
                    foodList.listFoodByExpiredDay();
                    break;
                case 5:
                    foodList.saveToFile(fileName);
                    break;
                default:
                    System.out.println("See you later!!!");
            }
        } while (0 < choice && choice < 6);
    }
}
