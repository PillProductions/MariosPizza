import java.sql.Date;
import java.sql.Timestamp;

public class PizzaObj {
    private String recipientName;
    private String comments;
    private Timestamp pickupTime;
    private String name;
    private String ingredients;
    private int number;
    private int price;

  public PizzaObj(String pName, String pIngredients, String pRecipientName, String pComments, Timestamp pPickupTime, int pNumber, int pPrice){
      this.recipientName = pRecipientName;
      this.comments = pComments;
      this.pickupTime = pPickupTime;
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
  public String getRecipientName(){
        return recipientName;
    }
    public String getComments(){return comments;}
    public Timestamp getPickupTime(){return pickupTime;}
  public int getNumber(){
        return number;
  }
  public int getPrice(){
        return price;
  }
}
