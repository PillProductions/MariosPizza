import java.util.Scanner;

public class Order {
    public static Scanner input = new Scanner(System.in);
    private String pizzaName;
    private int pizzaNumber;

    public Order(String pizzaName, int pizzaNumber) {
        this.pizzaName = pizzaName;
        this.pizzaNumber = pizzaNumber;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public int getPizzaNumber() {
        return pizzaNumber;
    }



}
