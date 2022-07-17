//d i)
public class SSD {
  private String brand; // store the brand name, access control private
  private double price; // store the price, access control private

// Increase the price by amount d v)
  public void increasePrice (double amount)
    {
      price = price + amount;
    }

// Price Setter d iii)
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
// Brand Getter d iv)
  public String getBrand()
  {
    return brand;
  }

}
