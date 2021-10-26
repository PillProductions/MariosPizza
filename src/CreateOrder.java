import java.util.Scanner;

public class CreateOrder {
    public static Scanner input = new Scanner(System.in);
    private String pizzaName;
    private int pizzaNumber;

    public CreateOrder(String pizzaName, int pizzaNumber) {
        this.pizzaName = pizzaName;
        this.pizzaNumber = pizzaNumber;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public int getPizzaNumber() {
        return pizzaNumber;
    }


    public static void createPizza(Scanner scan) {

        System.out.println("Hvilken pizza ønsker du? ");
        int input = scan.nextInt();
        System.out.println("Du har valgt pizza nr " + input);
        System.out.println("Vil du tilføje særlige ønsker? ");
        scan.nextLine();
        String input2 = scan.nextLine();
        if (input2.equalsIgnoreCase("Ja")) {
            System.out.println("Tilføj særlige ønsker");
            String input3 = scan.nextLine();
            System.out.println("Har du yderligere kommentarer?");
            String input4 = scan.nextLine();
            if (input4.equalsIgnoreCase("Ja")) {
                System.out.println("Tilføj kommentarer");
                scan.nextLine();
                System.out.println("Gemmer og afslutter ordre ");
            } else {
                input4.equalsIgnoreCase("Nej");
                System.out.println("Gemmer og afslutter ordre ");


            }

        }
    }


}


