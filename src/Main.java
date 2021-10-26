import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static Scanner input = new Scanner(System.in);
    private static Menu myMenu = new Menu();
    private static ViewOrder myViewMenu = new ViewOrder();

    public static void main(String[] args) throws Exception {
        System.out.println("*** MARIOS PIZZA ***\n");
        myMenu.readMenu();
        myViewMenu.readActiveOrders();
        mainMenu();

    }

    public static void mainMenu(){

        System.out.println("1: Se aktive ordre\n2: Rediger ordre\n3: Se ordre historik\n4: Se ordre statistik\n5: Lav ny ordre\n6: Slut program\n\n");
        System.out.print("Indtast funktionsnummer: ");
        int select = Integer.parseInt(input.nextLine());
        if (select == 1){

            for (int i=0; i<=ViewOrder.list.length-2; i++){
                System.out.printf("%-100s %50s %n", ViewOrder.list[i].getNumber() + ". " + ViewOrder.list[i].getName() + ": " +ViewOrder.list[i].getIngredients(), ViewOrder.list[i].getPickupTime() + " " + ViewOrder.list[i].getPrice() + ".- , " + ViewOrder.list[i].getRecipientName() + " '" + ViewOrder.list[i].getComments() + "'");
            }

        }else if (select == 2){
            System.out.println("Tryk 1 for at lave en ny ordre\nTryk 2 for at redigere en eksisterende ordre");
            int select1 = Integer.parseInt(input.nextLine());
            if (select1 == 1){
                for (int i=0; i<=Menu.list.length-2; i++){
                System.out.printf("%-100s %10s %n", Menu.list[i].getNumber() + ". " + Menu.list[i].getName() + ": " +Menu.list[i].getIngredients(), Menu.list[i].getPrice() + ".-");
            }
                Order.createPizza(input);
            }if(select1 == 2){
                Order.editOrder(input);
            }
            //Call edit order method
        }else if (select == 3){
            //Call order history
        }else if (select == 4){
            //Call order statistics
        }else if(select == 5){
            //CreateOrder.createPizza(input);
        }
        if (select != 6){
            mainMenu();
        }
    }



}
