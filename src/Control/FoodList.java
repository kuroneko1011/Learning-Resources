package Control;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

import Model.Food;
import Utility.Inputter;

public class FoodList extends ArrayList<Food> {
    public FoodList() {
        super();
    }

    // Hàm tìm kiếm theo ID
    public int searchID(int id) {
        for (Food f : this) {
            if (id == f.getId())
                return -1;
        }
        return 1;
    }

    // Hàm check duplicated ID
    public int checkDuplicateID(int id) {
        int check = searchID(id);
        if (check != 1)
            return -1;
        return 1;
    }

    static Scanner sc = new Scanner(System.in);
    Scanner sc1 = new Scanner(System.in);
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    // Hàm add
    public void add() {
        int ID, weight;
        String name, type, place = null;
        String date1 = null;
        Date date = null;
        boolean check = true;
        int choice = 0;

        do {
            ID = Inputter.getAnInteger("Enter the ID: ", "This ID is invalid. Try again!");
            if (checkDuplicateID(ID) != 1)
                System.out.println("This ID is duplicated");
            if (ID <= 0)
                System.out.println("This ID is invalid. Try again!");
        } while (checkDuplicateID(ID) != 1 || ID <= 0);

        type = Inputter.getString("Enter the type: ", "This type is invalid. Try again!");

        name = Inputter.getString("Enter the name: ", "This name is invalid. Try again!");

        do {
            try {
                System.out.println("Choose the place to place this food.");
                System.out.println("1: Cooler, 2: Freezer");
                System.out.println();
                System.out.println("Your choice is: ");
                choice = Integer.parseInt(sc1.nextLine());
                if (choice < 1 || choice > 2)
                    throw new Exception();
                switch (choice) {
                    case 1:
                        place = "Cooler";
                        break;
                    case 2:
                        place = "Freezer";
                        break;
                }
                check = false;
            } catch (Exception e) {
                System.out.println("You must choose 0 or 1");
                check = true;
            }
        } while (check);

        do {
            weight = Inputter.getAnInteger("Enter the weight (gram): ", "This weight is invalid. Try again!");
            if (weight <= 0)
                System.out.println("Weight must be greater than zero");
            if (weight >= 3000)
                System.out.println("This food is too big!");
        } while (weight <= 0 || weight >= 3000);

        // Nhập và check valid day
        do {
            System.out.println("Enter the expired date: ");
            date1 = sc.nextLine();
            df.setLenient(false);
            Date today = new Date();
            try {
                date = df.parse(date1);
                if (date.compareTo(today) < 0)
                    throw new Exception();
                check = true;
            } catch (ParseException e) {
                check = false;
                System.out.println("This date is invalid. Try again!");
            } catch (Exception e) {
                System.out.println("This food is expired. Try again!");
                check = false;
            }
        } while (!check);

        Food food = new Food(ID, name, weight, type, place, date);
        this.add(food);
        System.out.println("***Add successfully***");

        // hàm lặp lại
        doItAgain();
        do {
            choice = sc1.nextInt();
            switch (choice) {
                case 1:
                    add();
                    break;
                case 0:
                    System.out.println("Return to menu");
                    break;
                default:
                    System.out.println("You must choose 0 or 1");
                    break;
            }
        } while (0 > choice || choice > 1);
    }

    // Hàm tìm kiếm Food theo tên
    public void searchFoodByName() {
        if (this.isEmpty())
            System.out.println("Empty list!");
        else {
            String name = Inputter.getString("Enter the name of food you want to search: ", "Not blank! Try again!");
            int count = 0;
            for (Food food : this) {
                if (food.getName().contains(name)) {
                    System.out.println(food.toString());
                    count += 1;
                }
                ;
            }
            if (count == 0)
                System.out.println("This food does not exist.");

            doItAgain();
            int choice;
            do {
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        searchFoodByName();
                        break;
                    case 0:
                        System.out.println("Return to menu");
                        break;
                    default:
                        System.out.println("You must choose 0 or 1");
                        break;
                }
            } while (0 > choice || choice > 1);
        }
    }

    // Hàm xóa food bằng ID
    public void removeFoodByID() {
        if (this.isEmpty())
            System.out.println("Empty list!");
        else {
            int id = Inputter.getAnInteger("Enter the ID of food you wanna delete:", "Not blank! Try again!");
            int check = searchID(id);
            if (check == -1) {
                ArrayList<Food> toRemove = new ArrayList<Food>();
                for (Food f : this) {
                    if (id == f.getId()) {
                        toRemove.add(f);
                    }
                }
                System.out.println("Do you want to delete this food?");
                System.out.println("Press 1 for Yes, 0 for No.");
                int choiceToDelete = Integer.parseInt(sc.nextLine());
                do {
                    switch (choiceToDelete) {
                        case 0:
                            System.out.println("***Delete failed. Return to menu***");
                            break;
                        case 1:
                            this.removeAll(toRemove);
                            System.out.println("***Delete successfully***");
                            listFoodByExpiredDay();
                            break;
                        default:
                            System.out.println("Please enter 0 or 1");
                    }
                } while (choiceToDelete > 0 && choiceToDelete < 1);
            }
        }
    }

    public void listFoodByExpiredDay() {
        if (this.isEmpty())
            System.out.println("Empty list!");
        else {
            Collections.sort(this, new Comparator<Food>() {
                @Override
                public int compare(Food f1, Food f2) {
                    return f2.comparedTo(f1);
                }
            });
            System.out.println("                             ========= LIST OF FOOD ========");
            System.out.println();
            System.out.println(
                    "ID        Name             Weight(gram)           Type           Place        Date of expired");
            System.out.println();
            for (Food food : this) {
                System.out.println(food.toString());
            }
        }
    }

    public boolean saveToFile(String fileName) {
        try {
            File f = new File(fileName);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for (Food food : this) {
                pw.println(food.toString());
                System.out.println();
            }
            pw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("***Save successfully!***");
        return true;
    }

    public static void doItAgain() {
        System.out.println("Do you want to do it again?");
        System.out.println("Press 1 to yes, 0 to no.");
        System.out.println("Your choice is:");
    }
}
