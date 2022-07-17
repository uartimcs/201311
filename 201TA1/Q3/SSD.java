public class SSD {
  private String brand; // store the brand name
  private double price; // store the price

// Increase the price by amount
  public void increasePrice (double amount)
    {
      price = price + amount;
    }
// Whether price is cheap
  public boolean isCheap() {
    if (price < 300)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

// Determine the category of Price
  public String category() {
    int priceCategory = (int) (price / 300) ;
    switch (priceCategory)
    {
      case 0:
      return "Cheap";
      case 1:
      return "Medium";
      case 2:
      return "Medium";
      default:
      return "Expensive";
    }
  }
// generate Graph

  public void generateBar(char ch)
  {
    int starNum = (int) price / 100 ;
    if ((price % 100) >= 50.0)
    {
      starNum++;
    }
    System.out.print(price + " ");
    //since the question says input 979 and output 979 instead of 979.0, i can set format printing to .0f
    //System.out.printf("%.0f ", price);

    for (int i = 0; i < starNum; i++)
    {
      System.out.printf("%c", ch);
    }
  }

// Price Setter
  public void setPrice(double newPrice)
  {
    price = newPrice;
  }
// Price Getter
  public double getPrice()
  {
    return price;
  }

// Brand Setter
  public void setBrand(String newBrand)
  {
      brand = newBrand;
  }
// Brand Getter
  public String getBrand()
  {
    return brand;
  }

}
