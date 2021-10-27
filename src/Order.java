import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Order {


    public void newOrder(PizzaObj[] orderArray, int finalprice, String recipientName, Timestamp pickupTime) {
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



}
