import java.sql.Date;
import java.sql.Timestamp;

public class PizzaObj {
    private String comments;
    private String name;
    private String ingredients;
    private int number;
    private int price;

  public PizzaObj(String pName, String pIngredients, String pComments, int pNumber, int pPrice){
      this.comments = pComments;
      this.name = pName;
      this.ingredients = pIngredients;
      this.number = pNumber;
      this.price = pPrice;
  }
  public String getName(){
      return name;
  }
  public String getIngredients(){
        return ingredients;
  }
  public String getComments(){return comments;}
  public int getNumber(){
        return number;
  }
  public int getPrice(){
        return price;
  }
}
