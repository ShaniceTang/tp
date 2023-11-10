package seedu.cafectrl.storage;


import seedu.cafectrl.CafeCtrl;
import seedu.cafectrl.data.Pantry;
import seedu.cafectrl.data.Order;
import seedu.cafectrl.data.OrderList;
import seedu.cafectrl.data.Menu;
import seedu.cafectrl.data.Sales;
import seedu.cafectrl.data.dish.Dish;
import seedu.cafectrl.data.dish.Ingredient;
import seedu.cafectrl.ui.ErrorMessages;
import seedu.cafectrl.ui.Ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;


/**
 * The Decoder class offers methods to interpret string representations from text files,
 * decoding them into appropriate data structures. It includes methods to decode a Menu,
 * Pantry stock, and OrderList, allowing retrieval of data stored in a file.
 */
public class Decoder {

    private static final String DIVIDER = "\\| ";
    private static final Ui ui = new Ui();
    private static Logger logger = Logger.getLogger(CafeCtrl.class.getName());
    //@@author ShaniceTang
    /**
     * Decodes an ArrayList of string lines into a Menu object, reconstructing its content.
     *
     * @param textLines An ArrayList of strings representing the encoded Menu data.
     * @return A Menu object containing the decoded Menu data.
     */
    public static Menu decodeMenuData(ArrayList<String> textLines) {
        logger.info("Decoding menu.txt to Menu...");
        ArrayList<Dish> menuDishList = new ArrayList<>();
        for(String dishString : textLines) {
            logger.info("Line to decode: " + dishString);
            String[] dishStringArray = dishString.split(DIVIDER);
            String dishName = dishStringArray[0].trim();
            float dishPrice = Float.parseFloat(dishStringArray[1]);
            String[] ingredientStringArray = Arrays.copyOfRange(dishStringArray, 2, dishStringArray.length);
            ArrayList<Ingredient> ingredientsList = decodeIngredientData(ingredientStringArray);
            menuDishList.add(new Dish(dishName, ingredientsList, dishPrice));
        }
        return new Menu(menuDishList);
    }

    /**
     * Decodes an array of strings representing ingredient data into a list of Ingredient objects.
     *
     * @param ingredientsStringArray An array of strings containing encoded ingredient data.
     * @return An ArrayList of Ingredient objects containing the decoded ingredient information.
     */
    private static ArrayList<Ingredient> decodeIngredientData(String[] ingredientsStringArray) {
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        for(String ingredientString : ingredientsStringArray) {
            logger.info("Ingredient to decode: " + ingredientString);
            String[] array = ingredientString.split(" ");
            String name = array[0].trim();
            int qty = Integer.parseInt(array[1]);
            String unit = array[2].trim();
            ingredientList.add(new Ingredient(name, qty, unit));
        }
        return ingredientList;
    }

    //@@author ziyi105
    /**
     * Decodes raw string from pantry stock data file and create ingredient object from the data
     * @param encodedPantryStock raw string from pantry stock data file
     * @return a new pantry object with data from the pantry stock data file
     */
    public static Pantry decodePantryStockData(ArrayList<String> encodedPantryStock) {
        ArrayList<Ingredient> pantryStock = new ArrayList<>();

        if (encodedPantryStock.isEmpty()) {
            return new Pantry(ui);
        }
        for (String encodedData : encodedPantryStock) {
            String[] decodedData = encodedData.split(DIVIDER);
            if (!isValidPantryStockFormat(decodedData)) {
                ui.showToUser(ErrorMessages.ERROR_IN_PANTRY_STOCK_DATA);
            } else {
                Ingredient ingredient = new Ingredient(decodedData[0],
                        Integer.parseInt(decodedData[1].trim()), decodedData[2]);
                pantryStock.add(ingredient);
            }
        }
        return new Pantry(ui, pantryStock);
    }

    /**
     * Checks whether the pantry stock is in the format of ingredient name | quantity (int) | unit
     * @param decodedPantryStock string array of the raw data string from pantry stock data file
     *                           split with "|"
     * @return true if the format is correct, false otherwise
     */
    private static boolean isValidPantryStockFormat(String[] decodedPantryStock) {
        if (decodedPantryStock.length != 3) {
            return false;
        } else {
            try {
                Integer.parseInt(decodedPantryStock[1].trim());
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    //@@author NaychiMin
    /**
     * Decodes a list of order data and constructs a Sales object using an array of OrderList objects.
     *
     * @param textLines List of order strings in the format "dishName|quantity|totalOrderCost".
     * @param menu Menu instance to retrieve Dish objects based on dishName.
     * @return Sales object containing OrderList objects decoded from the provided strings.
     */
    public static Sales decodeSales(ArrayList<String> textLines, Menu menu) {
        logger.info("Decoding orders.txt to Sales...");
        ArrayList<OrderList> orderLists = new ArrayList<>();
        if(textLines.isEmpty()) {
            return new Sales();
        }
        //for each 'order' in text file
        for (String line : textLines) {
            logger.info("Line to decode: " + line);
            String[] orderData = line.split(DIVIDER);
            int day = Integer.parseInt(orderData[0].trim()) - 1;
            String dishName = orderData[1].trim();
            int quantity = Integer.parseInt(orderData[2].trim());
            float totalOrderCost = Float.parseFloat(orderData[3].trim());
            boolean isComplete = "true".equals(orderData[4].trim());

            Order orderedDish = new Order(menu.getDishFromName(dishName), quantity, totalOrderCost, isComplete);

            //increase size of orderLists if needed
            //this can be used in the event that the text file's first order is not day 0
            while (orderLists.size() <= day) {
                orderLists.add(new OrderList());
            }

            orderLists.get(day).addOrder(orderedDish);
        }
        return new Sales(orderLists);
    }
}
