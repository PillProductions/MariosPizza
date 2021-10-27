import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ViewOrder {
    public static OrderObj[] outputObj = new OrderObj[1];

    public void readActiveOrders()throws Exception  {
        String output = "";
        int currentline = 0;
        File myObj = new File("active_orders.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            PizzaObj[] list = new PizzaObj[1];
            String[] commentparts = null;
            String[] fullparts = myReader.nextLine().split(" // ");
            String[] pizzaparts = fullparts[1].split(" , ");
            for(int i=0; i<=pizzaparts.length-1; i++){
                if(pizzaparts[i].contains(" & ")){
                    commentparts = pizzaparts[i].split(" & ");
                    list[i] = new PizzaObj(Menu.list[Integer.parseInt(commentparts[0])-1].getName(), Menu.list[Integer.parseInt(commentparts[0])-1].getIngredients(), commentparts[1], Integer.parseInt(commentparts[0]), Menu.list[Integer.parseInt(commentparts[0])-1].getPrice());
                } else {
                    list[i] = new PizzaObj(Menu.list[Integer.parseInt(pizzaparts[i])-1].getName(), Menu.list[Integer.parseInt(pizzaparts[i])-1].getIngredients(), "", Integer.parseInt(pizzaparts[i]), Menu.list[Integer.parseInt(pizzaparts[i])-1].getPrice());
                }
                list = Arrays.copyOf(list, list.length + 1); //Resize name array by one more
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date parsed = format.parse(fullparts[3]);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(parsed.getTime());
            outputObj[currentline] = new OrderObj(fullparts[0], Integer.parseInt(fullparts[2]), timestamp, list);
            outputObj = Arrays.copyOf(outputObj, outputObj.length + 1); //Resize name array by one more
            currentline++;
        }
        myReader.close();
    }

}
