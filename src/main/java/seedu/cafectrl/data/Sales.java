package seedu.cafectrl.data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import seedu.cafectrl.ui.Ui;

/**
 * The Sales class represents sales data over a period of time, maintaining a collection of order lists.
 */
public class Sales {
    private static final DecimalFormat dollarValue = new DecimalFormat("0.00");
    private static final String HEADER_FORMAT = "%-20s %-10s %-20s\n";
    private static ArrayList<OrderList> orderLists;
    private int daysAccounted;

    public Sales() {
        this.orderLists = new ArrayList<>();
        orderLists.add(new OrderList());
        this.daysAccounted = 0;
    }

    public Sales(ArrayList<OrderList> orderLists) {
        this.orderLists = orderLists;
        this.daysAccounted = 0;
    }

    //TODO: @Zhong Heng, Remove this method if not used
    public Sales(OrderList orderList) {
        this.orderLists = new ArrayList<>();
        orderLists.add(orderList);
        this.daysAccounted = 0;
    }

    public void addOrderList(OrderList orderList) {
        orderLists.add(orderList);
    }

    //TODO: @Zhong Heng, Remove this method if not used
    public void removeOrderList(int orderListId) {
        orderLists.remove(orderListId);
    }

    public void nextDay() {
        this.daysAccounted += 1;
    }

    public int getDaysAccounted() {
        return daysAccounted;
    }

    public ArrayList<OrderList> getOrderLists() {
        return orderLists;
    }

    public OrderList getOrderList(int index) {
        return orderLists.get(index);
    }

    //@@author NaychiMin
    /**
     * Prints all sales data, organized by day, including dish names, quantities, and total cost prices.
     *
     * @param ui   The Ui object for user interface interactions.
     * @param menu The Menu object representing the cafe's menu.
     */
    public void printSales(Ui ui, Menu menu) {
        if(orderLists.isEmpty()) {
            ui.showToUser("No sales made.");
            return;
        }

        for (int day = 0; day < orderLists.size(); day++) {
            OrderList orderList = orderLists.get(day);
            ui.showToUser("Day " + (day + 1) + ":");

            if (orderList.isEmpty() || !orderList.hasCompletedOrders()) {
                ui.showToUser("No sales for this day.");
                continue;
            }

            ui.showToUser(String.format(HEADER_FORMAT, "Dish Name", "Dish Qty", "Total Cost Price"));
            orderList.printOrderList(menu, ui);
        }
    }

    /**
     * Prints sales data for a specific day, including dish names, quantities, and total cost prices.
     *
     * @param ui  The Ui object for user interface interactions.
     * @param menu The Menu object representing the cafe's menu.
     * @param day The day for which sales data is to be printed.
     */
    public void printSaleByDay(Ui ui, Menu menu, int day) {
        int orderListIndex = day - 1;
        try {
            OrderList orderList = orderLists.get(orderListIndex);
            ui.showToUser("Day " + (day) + ":");
            ui.showToUser(String.format(HEADER_FORMAT, "Dish Name", "Dish Qty", "Total Cost Price"));

            orderList.printOrderList(menu, ui);
        } catch (Exception e) {
            ui.showToUser("An error occurred while printing sales for the specified day.");
        }
    }
    //@@author
}
