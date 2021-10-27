import java.sql.Timestamp;

public class OrderObj {
    private String recipientName;
    private int finalPrice;
    private Timestamp pickupTime;
    private PizzaObj[] pizzaArray = new PizzaObj[1];

    public OrderObj(String pRecipientName, int pFinalPrice, Timestamp pPickupTime, PizzaObj[] PizzaArray) {
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
  public PizzaObj[] getPizzaArray(){
        return pizzaArray;
  }
}
