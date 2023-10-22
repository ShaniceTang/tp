package seedu.cafectrl.command;

import seedu.cafectrl.data.Menu;
import seedu.cafectrl.ui.Ui;
import seedu.cafectrl.ui.UserOutput;

import java.text.DecimalFormat;

/**
 * Lists all dishes in the menu to the user.
 */
public class ListMenuCommand extends Command {
    public static final String COMMAND_WORD = "list_menu";

    private static final DecimalFormat dollarValue = new DecimalFormat("0.00");

    /**
     * Iterates through the menu arraylist, outputting the dish name and dish price.
     *
     * @param menu ArrayList of Dishes
     */
    @Override
    public void execute(Menu menu) {
        Ui.showToUser(UserOutput.LIST_MESSAGE.message);
        for(int i =0; i < menu.getSize(); i++) {
            String indexNum = String.valueOf(i+1);
            String dishName = menu.getDish(i).getName();
            String dishPrice = dollarValue.format(menu.getDish(i).getPrice());
            Ui.showToUser(indexNum + ". " + dishName + " $" + dishPrice);
        }
    };
}
