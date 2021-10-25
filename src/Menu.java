import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    public static PizzaObj[] list = new PizzaObj[1];

    public void readMenu()throws FileNotFoundException {
        int i = 0;
        File myObj = new File("pizza_list.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String[] parts = myReader.nextLine().split(" // ");
            PizzaObj output = new PizzaObj(parts[1], parts[2], Integer.parseInt(parts[0]), Integer.parseInt(parts[3]));
            list[i]=output;
            list = Arrays.copyOf(list, list.length + 1); //Resize name array by one more
            i++;
        }
        myReader.close();
    }
}
