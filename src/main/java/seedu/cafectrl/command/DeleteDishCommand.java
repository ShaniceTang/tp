package seedu.cafectrl.command;

import seedu.cafectrl.data.Menu;
import seedu.cafectrl.data.dish.Dish;
import seedu.cafectrl.ui.Messages;
import seedu.cafectrl.ui.Ui;

/**
 * Deletes a menu item identified using it's last displayed index from the menu.
 */
public class DeleteDishCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the menu item identified by the index number used in the last menu listing.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public DeleteDishCommand(int listIndex) {
        this.index = listIndex;
    }

    @Override
    public void execute(Menu menu) {
        try {
            int dishIndexToBeDeleted = index - Ui.OFFSET_LIST_INDEX;
            Dish selectedDish = menu.getMenuItemsList().get(dishIndexToBeDeleted);
            Ui.printDeleteMessage(selectedDish);
            menu.removeDish(dishIndexToBeDeleted);
        } catch (IndexOutOfBoundsException e) {
            Ui.showToUser(Messages.INVALID_DISH_INDEX);
            throw new IndexOutOfBoundsException();
        }
    }
}
