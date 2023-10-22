package seedu.cafectrl;

import seedu.cafectrl.command.Command;
import seedu.cafectrl.data.Menu;
import seedu.cafectrl.parser.Parser;
import seedu.cafectrl.ui.Ui;

/**
 * CafeCtrl application's entry point.
 * Initializes the application and starts the interaction with the user.
 */
public class CafeCtrl {
    private final Ui ui;
    private final Menu menu;
    private Command command;

    /**
     * Private constructor for the CafeCtrl class, used for initializing the user interface and menu list.
     */
    private CafeCtrl() {
        ui = new Ui();
        menu = new Menu();
    }

    private void setup() {
        Ui.showWelcome();
    }
    
    /**
     * The main loop of the CafeCtrl application.
     *
     * <p> This method consistently receives user input, parses commands, and executes the respective command
     * until the user enters a "bye" command, terminating the application.</p>
     */
    private void run() {
        Ui.printLine();
        do {
            try {
                String fullUserInput = ui.receiveUserInput();
                command = Parser.parseCommand(menu, fullUserInput);
                command.execute(menu, ui);
            } catch (Exception e) {
                Ui.showToUser(e.getMessage());
            } finally {
                Ui.printLine();
            }
        } while (!command.isExit());
    }

    public static void main(String[] args) {
        CafeCtrl cafeCtrl = new CafeCtrl();
        cafeCtrl.setup();
        cafeCtrl.run();
    }
}

