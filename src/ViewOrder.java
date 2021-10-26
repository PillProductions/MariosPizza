import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ViewOrder {
    public static PizzaObj[] list = new PizzaObj[1];
    public void readActiveOrders()throws Exception  {
        int i = 0;
        File myObj = new File("active_orders.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String[] parts = myReader.nextLine().split(" // ");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsed = format.parse(parts[4]);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed.getTime());
            PizzaObj output = new PizzaObj(parts[1], parts[2],parts[5],parts[6], timestamp, Integer.parseInt(parts[0]), Integer.parseInt(parts[3]));
            list[i]=output;
            list = Arrays.copyOf(list, list.length + 1); //Resize name array by one more
            i++;
        }
        myReader.close();
    }
}
