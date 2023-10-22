package seedu.cafectrl.ui;

import seedu.cafectrl.data.dish.Dish;
import seedu.cafectrl.data.dish.Ingredient;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Ui {
    public static final int OFFSET_LIST_INDEX = 1;
    public static final String LINE_STRING = "-----------------------------------------------------";
    private final Scanner scanner;

    /**
     * Constructs a UI instance with a Scanner for user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    public static void printLine() {
        showToUser(LINE_STRING);
    }

    public String receiveUserInput() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    public static void showWelcome() {
        showToUser(UserOutput.WELCOME_MESSAGE.message);
    }

    public static void showGoodbye() {
        showToUser(UserOutput.GOODBYE_MESSAGE.message);
    }

    /**
     * Prints out the quantity of each ingredient needed for the
     * dish that the user selects.
     *
     * @param selectedDish Dish for ingredients to be listed out.
     */
    public static void printIngredients(Dish selectedDish) {
        String ingredientsString = selectedDish.getName() + " Ingredients: \n";

        for (Ingredient ingredient : selectedDish.getIngredients()) {
            ingredientsString += ingredient.toString() + "\n";
        }

        showToUser(ingredientsString.trim());
    }

    public static void printAddDishMessage(Dish dish) {
        String dishNameString = "Dish Name: " + dish.getName();
        DecimalFormat dollarValue = new DecimalFormat("0.00");
        float dishPrice = dish.getPrice();
        String dishPriceString = "Dish Price: $" + dollarValue.format(dishPrice);
        StringBuilder dishIngredientsString = new StringBuilder("Ingredients:\n");

        for (int i = 0; i < dish.getIngredients().size(); i++) {
            Ingredient ingredient = dish.getIngredients().get(i);

            dishIngredientsString.append("\t")
                    .append(i + OFFSET_LIST_INDEX)
                    .append(". ")
                    .append(ingredient.toString())
                    .append("\n");
        }

        showToUser(UserOutput.ADD_DISH_MESSAGE.message,
                dishNameString,
                dishPriceString,
                dishIngredientsString.toString());
    }

    /**
     * Shows delete message to user
     *
     * @param selectedDish Dish to be deleted
     */
    public static void printDeleteMessage(Dish selectedDish) {
        showToUser("Okay! " + selectedDish.getName() + " is deleted! :)");
    }


    /**
     * Shows messages(s) to the user
     * @param message string(s) of messages to print
     */
    public static void showToUser(String... message) {
        for (String m: message) {
            System.out.println(m);
        }
    }

    /**
     * show edit price message to user
     * @param menuItem menuItem that has been modified
     */
    public static void showEditPriceMessage(String menuItem) {
        showToUser(Messages.PRICE_MODIFIED_MESSAGE, menuItem);
    }
}
