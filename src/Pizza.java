//This is pizza class, used to initialize pizza objects

public class Pizza {
    private String comments;
    private String name;
    private String ingredients;
    private int number;
    private int price;

  public Pizza(String pName, String pIngredients, String pComments, int pNumber, int pPrice){ //Constructor
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
