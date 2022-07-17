public class TestSSD {
  public static void main(String[] args)
  {
    SSD SSDA = new SSD();
    System.out.println("An object SSDA of class SSD has been created.");
    System.out.println("");
    // d ii)

// part vi) Set Brand, set Price, increase Price and Print out necessary data.
    SSDA.setBrand("Samsung"); // Set Brand: Samsung
    SSDA.setPrice(335.5); // Set price: 335.5
    System.out.println("The brand you selected is " + SSDA.getBrand() + ".");
    System.out.println("");

    System.out.println("Original Price: " + SSDA.getPrice());
    SSDA.increasePrice(50); // Add 50 price
    System.out.println("Current price: " + SSDA.getPrice()); // print current price
  }
}
