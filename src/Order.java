import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Order {

    private String recipientName;
    private int finalPrice;
    private Timestamp pickupTime;
    private Pizza[] pizzaArray = new Pizza[1];

    public Order(String pRecipientName, int pFinalPrice, Timestamp pPickupTime, Pizza[] PizzaArray) {
        this.recipientName = pRecipientName;
        this.finalPrice = pFinalPrice;
        this.pickupTime = pPickupTime;
        this.pizzaArray = PizzaArray;
    }
    public String getRecipientName(){
        return recipientName;
    }
    public int getFinalPrice(){
        return finalPrice;
    }
    public Timestamp getPickupTime(){return pickupTime;}
    public Pizza[] getPizzaArray(){
        return pizzaArray;
    }

    public void newOrder(Pizza[] orderArray, int finalprice, String recipientName, Timestamp pickupTime) {
        String output = "";
        try {
            File myObj = new File("active_orders.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                output += myReader.nextLine() + "\n";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("En fejl har opstået (Fejl 1).");
            e.printStackTrace();
        }
        output += recipientName + " // ";
        try {
            FileWriter myWriter = new FileWriter("active_orders.txt");

            for (int i=0; i<=orderArray.length-1; i++){
                if(orderArray[i].getComments()!="Ingen kommentar") {
                    output += orderArray[i].getNumber() + " & " + orderArray[i].getComments();
                } else{
                    output += orderArray[i].getNumber();
                }
                if(i!=orderArray.length-1){
                    output += " , ";
                }
            }
            output += " // " + finalprice + " // " + pickupTime;
            myWriter.write(output);
            myWriter.close();

        } catch (IOException e) {
            System.out.println("En fejl er opstået (Fejl 2).");
            e.printStackTrace();
        }
    }

    public static Order[] outputObj = new Order[1];

    public void readActiveOrders()throws Exception  {
        String output = "";
        int currentline = 0;
        File myObj = new File("active_orders.txt");
        Scanner myReader = new Scanner(myObj);
        outputObj = null;
        outputObj = new Order[1];
        while (myReader.hasNextLine()) {
            Pizza[] list = new Pizza[1];
            String[] commentparts = null;
            String[] fullparts = myReader.nextLine().split(" // ");
            String[] pizzaparts = fullparts[1].split(" , ");
            for(int i=0; i<=pizzaparts.length-1; i++){
                if(pizzaparts[i].contains(" & ")){
                    commentparts = pizzaparts[i].split(" & ");
                    list[i] = new Pizza(Menu.list[Integer.parseInt(commentparts[0])-1].getName(), Menu.list[Integer.parseInt(commentparts[0])-1].getIngredients(), commentparts[1], Integer.parseInt(commentparts[0]), Menu.list[Integer.parseInt(commentparts[0])-1].getPrice());
                } else {
                    list[i] = new Pizza(Menu.list[Integer.parseInt(pizzaparts[i])-1].getName(), Menu.list[Integer.parseInt(pizzaparts[i])-1].getIngredients(), "", Integer.parseInt(pizzaparts[i]), Menu.list[Integer.parseInt(pizzaparts[i])-1].getPrice());
                }
                list = Arrays.copyOf(list, list.length + 1); //Resize name array by one more
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsed = format.parse(fullparts[3]);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed.getTime());
            outputObj[currentline] = new Order(fullparts[0], Integer.parseInt(fullparts[2]), timestamp, list);
            outputObj = Arrays.copyOf(outputObj, outputObj.length + 1); //Resize name array by one more
            currentline++;
        }
        myReader.close();

        //Sorts array
       for (int i = 0; i <= outputObj.length - 2; i++) {
            Order placeholder = outputObj[i];
            for (int o = i; o <= outputObj.length - 2; o++) {
                if (outputObj[o].getPickupTime().before(outputObj[i].getPickupTime())) {
                    placeholder = outputObj[i];
                    outputObj[i] = outputObj[o];
                    outputObj[o] = placeholder;
                }
            }
        }


    }

}
