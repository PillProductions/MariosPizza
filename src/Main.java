import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {
    public static Scanner input = new Scanner(System.in);
    private static Menu myMenu = new Menu();
    private static Order myOrder = new Order("", 0, null, null);
    
    public static void main(String[] args) throws Exception {
        System.out.println("*** MARIOS PIZZA ***\n");
        myMenu.readMenu();
        myOrder.readActiveOrders();
        mainMenu();

    }

    public static void mainMenu() throws Exception {
        System.out.println("1: Se aktive ordreliste\n2: Rediger ordreliste\n3: Se ordre historik\n4: Se ordre statistik\n5: Slut program\n\n");
        System.out.print("Indtast funktionsnummer: ");
        int select = Integer.parseInt(input.nextLine());
        if (select == 1){
            viewActiveOrders();
        }else if (select == 2){
            System.out.println("Tryk 1 for at lave en ny ordre\nTryk 2 for at redigere en eksisterende ordre");
            int select1 = Integer.parseInt(input.nextLine());
            if (select1 == 1){

                createPizza(input);
            }if(select1 == 2){
                editOrder(input);
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

    public static void createPizza(Scanner scan) {
        Pizza[] currentOrder = new Pizza[1];
        boolean orderFinished = false;
        int finalPrice = 0;
        int pizzaCount = 0; //Keeps count of pizza's in current order
        String comments = "";
        String recipientName = "";
        Date date = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15));
        Timestamp pickupTime = new Timestamp(date.getTime());

        while (orderFinished == false) {
            for (int i=0; i<=Menu.list.length-2; i++){
                System.out.printf("%-100s %10s %n", Menu.list[i].getNumber() + ". " + Menu.list[i].getName() + ": " +Menu.list[i].getIngredients(), Menu.list[i].getPrice() + ".-");
            }
            System.out.println("\nHvilken pizza ønsker du? (Indtast nummer)");
            int pizzaNumber = scan.nextInt();
            scan.nextLine();
            System.out.println("Du har valgt følgende pizza: " + pizzaNumber + ": " + Menu.list[pizzaNumber-1].getName() + ". - " + Menu.list[pizzaNumber-1].getIngredients() + " - " + Menu.list[pizzaNumber-1].getPrice() + ".-");
            finalPrice += Menu.list[pizzaNumber-1].getPrice();
            System.out.println("Skal der tilføjes nogle kommentarer? (ja/nej)");
            if (scan.nextLine().equalsIgnoreCase("ja")) {
                System.out.print("Tilføj kommentarer: ");
                comments = scan.nextLine();
            } else {
                comments = "Ingen kommentar";
            }
            currentOrder[pizzaCount] = new Pizza(Menu.list[pizzaNumber-1].getName(), Menu.list[pizzaNumber-1].getIngredients(), comments, pizzaNumber, Menu.list[pizzaNumber-1].getPrice());
            System.out.println("Vil du tilføje flere pizzaer? (Ja/Nej)");
            if (scan.nextLine().equalsIgnoreCase("nej")) {
                orderFinished = true;
            } else {
                currentOrder = Arrays.copyOf(currentOrder, currentOrder.length + 1); //Resize name array by one more
                pizzaCount++;
            }
        }

        System.out.println("\nBestilling pris: " + finalPrice + ".-");
        System.out.println("Hvad hedder kunden?:");
        recipientName = scan.nextLine();
        System.out.println("Hvornår skal pizzaen hentes?\n1: Om et kvarter\n2: Specielt tidspunkt");
        if (scan.nextInt() == 2) {
            Date dateCurrent = new Date();
            pickupTime = new Timestamp(dateCurrent.getTime());
        }
        myOrder.newOrder(currentOrder, finalPrice, recipientName, pickupTime);
        System.out.println("\n*** Bestilling tilføjet til " + pickupTime.toString() + " ***\n");
        recipientName = scan.nextLine();
    }
    public static void editOrder(Scanner scan){
        System.out.println("Hvilken ordre vil du gerne redigere?");
    }

    public static void viewActiveOrders() throws Exception {
        KeyEvent.exitLoop = false;
        JFrame jf=new JFrame("Key listener");
        jf.setVisible(true);
        jf.setSize(0,0);
        jf.addKeyListener(new KeyEvent());
        int timer = 29;
        while (!KeyEvent.exitLoop) {
            wait(500);
            timer++;
            if (timer==30) {
                //First we read active orders
                myOrder.readActiveOrders();

                //Then we print
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                for (int i = 0; i <= Order.outputObj.length - 2; i++) {
                    System.out.println(Order.outputObj[i].getRecipientName().toUpperCase() + " - AFHENTES: " + Order.outputObj[i].getPickupTime().toString() + " - TOTAL PRIS: " + Order.outputObj[i].getFinalPrice());
                    Pizza[] pizzaInOrder = Order.outputObj[i].getPizzaArray();
                    for (int o = 0; o <= pizzaInOrder.length - 2; o++) {
                        System.out.printf("%-50s %100s %n", pizzaInOrder[o].getNumber() + ". " + pizzaInOrder[o].getName() + " " + pizzaInOrder[o].getComments().toUpperCase(), pizzaInOrder[o].getIngredients() + " - PRIS: " + pizzaInOrder[o].getPrice() + ".-");
                    }
                    System.out.println();
                }
                timer=0; //Resets load of ViewOrder list
                System.out.println("\n *** Tryk på en vilkårlig tast for at gå tilbage til hovedmenu ***");

            }
        }
        jf.setVisible(false); //you can't see me!
        jf.dispose(); //Destroy the JFrame object
        jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
    }
    public static void wait(int ms){ //Pauses program briefly to make it seem more natural
        try { //Thread needs to be able to catch exeption for some reason. Idk why
            Thread.sleep(ms);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


}
