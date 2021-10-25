public class PizzaObj {
    private String name;
    private String ingredients;
    private int number;
    private int price;

  public PizzaObj(String pName, String pIngredients, int pNumber, int pPrice){
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
  public int getNumber(){
        return number;
  }
  public int getPrice(){
        return price;
  }
}
