import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {
    private static Scanner input = new Scanner(System.in);
    private static Menu myMenu = new Menu(); //Create instance of menu, in order to call methods
    private static Order myOrder = new Order("", 0, null, null); //Create instance of Order, in order to call methods
    
    public static void main(String[] args) throws Exception {
        System.out.println("*** MARIOS PIZZA ***\n");
        myMenu.readMenu(); //Menu items are initialized, could have been solved with a get-set instead
        mainMenu();
    }

    public static void mainMenu() throws Exception {
        System.out.println("1: Se aktive ordreliste\n2: Rediger ordreliste\n3: Se ordre historik\n4: Se ordre statistik\n5: Vis menukort\n6: Slut program\n\n");
        System.out.print("Indtast funktionsnummer: ");

        int select = Integer.parseInt(input.nextLine());
        if (select == 1){
            System.out.println("\n\n*** INDLÆSER, VENT VENLIGST ***\n\n");
            viewActiveOrders();
        }else if (select == 2){
            editOrder(input);
        }else if (select == 3){
            viewOrderHistory();
        }else if (select == 4){
            generateStatistics();
        }else if (select == 5){
            printPizzaMenu();
            System.out.println("\n");
        }if (select != 6){
            mainMenu();
        }
    }
    public static void editOrder(Scanner scan) throws Exception {
        System.out.println("1: Lav en ny ordre\n2: Afslut og fjern en eksisterende ordre\n3: Tilbage");
        int select1 = Integer.parseInt(input.nextLine());
        if (select1 == 1){
            createPizza(input);
        }if(select1 == 2){
            endOrder(input);
        }
    }
    public static void printPizzaMenu(){
        for (int i=0; i<=Menu.list.length-2; i++){
            System.out.printf("%-100s %10s %n", Menu.list[i].getNumber() + ". " + Menu.list[i].getName() + ": " +Menu.list[i].getIngredients(), Menu.list[i].getPrice() + ".-");
        }
    }

    public static void createPizza(Scanner scan) throws ParseException {
        Pizza[] currentOrder = new Pizza[1]; //Creates empty array of pizza objects
        boolean orderFinished = false; //Made for while loop
        int finalPrice = 0; //Keeps track of total price
        int pizzaCount = 0; //Keeps count of pizza's in current order
        String comments;
        String recipientName;
        Date date = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)); //Current time +15 min
        Timestamp pickupTime = new Timestamp(date.getTime()); //Converted to Java.sql.Timestamp (no specific reason, this is just the standard in this project)

        while (!orderFinished) {
            printPizzaMenu();
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
                currentOrder = Arrays.copyOf(currentOrder, currentOrder.length + 1); //Resize array by one more
                pizzaCount++;
            }
        }

        System.out.println("\nBestilling pris: " + finalPrice + ".-");
        System.out.println("Hvad hedder kunden?:");
        recipientName = scan.nextLine();
        System.out.println("Hvornår skal pizzaen hentes?\n1: Om et kvarter\n2: Specielt tidspunkt");
        if (Integer.parseInt(scan.nextLine()) == 2) { //Shitty code to convert string to date in case cx wants custom pickup time
                System.out.println("Indtast tidspunkt i følgende format: HH:MM (Eks. 18:30)");
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            Date today = new Date();
            String inputDate = (formatter1.format(today) + " " + scan.nextLine() + ":" + randomNum(10,59) + "." + randomNum(100,999));
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date parsed = formatter2.parse(inputDate);
            pickupTime = new java.sql.Timestamp(parsed.getTime());
        }
        myOrder.newOrder(currentOrder, finalPrice, recipientName, pickupTime);
        System.out.println("\n*** Bestilling tilføjet til " + pickupTime.toString().substring(0,16) + " ***\n");
    }

    public static void endOrder(Scanner scan) throws Exception {
        System.out.println("\nHvilken ordre vil du gerne afslutte?: \n");
        for (int i = 0; i<=myOrder.getActiveOrders().length -2; i++){
            System.out.println((i+1) + ": " + myOrder.getActiveOrders()[i].getRecipientName().toUpperCase() + " - AFHENTES: " + myOrder.getActiveOrders()[i].getPickupTime().toString().substring(0,16) + " - TOTAL PRIS: " + myOrder.getActiveOrders()[i].getFinalPrice());
        }
        System.out.print("\nIndtast nummer: ");
        int delNumber = Integer.parseInt(scan.nextLine());
        System.out.println("Vil du gemme i ordre historik?\n1: Ja\n2: Nej");
        if(Integer.parseInt(scan.nextLine())==1){
            myOrder.removeOrder(myOrder.getActiveOrders()[delNumber-1], true);
        }else {
            myOrder.removeOrder(myOrder.getActiveOrders()[delNumber - 1], false);
        }
        System.out.println("\n*** Ordre fjernet ***\n");
        editOrder(input);
    }

    private static boolean exitLoop = false;
    public static void viewActiveOrders() throws Exception {

        JFrame jf=new JFrame(""); //Creating JFrame with button
        jf.setVisible(true); //This is done so the while loop below can be broken, without needing scanner input
        jf.setSize(150,75);
        jf.setAlwaysOnTop(true); //Keep window on top to avoid confusion
        JButton b=new JButton("Afslut");
        b.setBounds(0,0,150,75);
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ //Button action if pressed
                exitLoop=true; //End loop reading file
                jf.setVisible(false); //Make JFrame invisible
                jf.dispose();
            }
        });
       jf.add(b);
        int timer = 29;
        while (!exitLoop) { //Loops until button has been pressed
            wait(500);
            timer++;
            if (timer==30) {
                Order[] activeOrders = myOrder.getActiveOrders(); //Gets active orders as Order[] array named activeOrders
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                for (int i = 0; i <= activeOrders.length - 2; i++) {
                    System.out.println(activeOrders[i].getRecipientName().toUpperCase() + " - AFHENTES: " + activeOrders[i].getPickupTime().toString().substring(0,16) + " - TOTAL PRIS: " + activeOrders[i].getFinalPrice());
                    Pizza[] pizzaInOrder = activeOrders[i].getPizzaArray();
                    for (int o = 0; o <= pizzaInOrder.length - 2; o++) {
                        System.out.printf("%-50s %100s %n", pizzaInOrder[o].getNumber() + ". " + pizzaInOrder[o].getName() + " " + pizzaInOrder[o].getComments().toUpperCase(), pizzaInOrder[o].getIngredients() + " - PRIS: " + pizzaInOrder[o].getPrice() + ".-");
                    }
                    System.out.println();
                }
                timer=0; //Resets load of ViewOrder list
                System.out.println("\n *** Tryk på AFSLUT for at returnere til hovedmenu ***");

            }
        }
        exitLoop = false;
    }

    public static void viewOrderHistory() throws Exception {
        System.out.println();
        Order[] orderHistory = myOrder.getHistoricalOrders(); //Gets active orders as Order[] array named activeOrders
        for (int i = 0; i <= orderHistory.length - 2; i++) {
            System.out.println(orderHistory[i].getRecipientName().toUpperCase() + " - AFHENTES: " + orderHistory[i].getPickupTime().toString() + " - TOTAL PRIS: " + orderHistory[i].getFinalPrice());
            Pizza[] pizzaInOrder = orderHistory[i].getPizzaArray(); //Gets array of pizzas within order
            for (int o = 0; o <= pizzaInOrder.length - 2; o++) { //Prints array of pizzas
                System.out.printf("%-50s %100s %n", pizzaInOrder[o].getNumber() + ". " + pizzaInOrder[o].getName() + " " + pizzaInOrder[o].getComments().toUpperCase(), pizzaInOrder[o].getIngredients() + " - PRIS: " + pizzaInOrder[o].getPrice() + ".-");
            }
            System.out.println();
        }
    }

    public static void generateStatistics() throws Exception {
        System.out.println("\n\n*** GENERER STATISTIK ***\n");
        Order[] HistoricalOrder = myOrder.getHistoricalOrders();
        int[] intArray = new int[Menu.list.length]; //Keeps score per sale of each pizza on menu
        int max = 0; //Keeps track of the highest score

        for (int i=0; i<=HistoricalOrder.length-2; i++){ //We start by looping through historical orders
            for (int o=0; o<=HistoricalOrder[i].getPizzaArray().length-2; o++){ //Then we loop through sold pizzas in each order
                for (int x=1; x<=Menu.list.length+1; x++){ //Then we loop through every possible pizza number on the menu
                    if (HistoricalOrder[i].getPizzaArray()[o].getNumber()==x){ //Finally, we check if the pizza number is equivalent to the current number x in our for loop
                        intArray[x-1]++; //If so, our int arrays equivalent item to the menu number gets +1
                        if(intArray[x-1]>max){max=intArray[x-1];} //Updates max score
                    }
                }
            }
        }
        //Now we print the results in a sorted order
        for (int i=max;i>=0;i--) { //Loops every number from max sales counted to 0
            for (int o = 0; o <= intArray.length - 2; o++) { //Loops every salescount
                if(intArray[o]==i) { //Checks if salescount = i (current max sale)
                    System.out.println((o + 1) + ". " + Menu.list[o].getName() + ": " + intArray[o] + " SOLGT");
                }
            }
        }
        System.out.println();
    }

    public static void wait(int ms){
        try { //Thread needs to be able to catch exception for some reason. Idk why
            Thread.sleep(ms);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static int randomNum(int min, int max){ //Generates random number between two ints
        return(ThreadLocalRandom.current().nextInt(min, max + 1));}
}
