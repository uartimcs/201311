public class TestSSD2 {
  public static void main(String[] args)
  {
// part 3 iv) created class TestSSD2
    SSD SSDB = new SSD();
    System.out.println("An object SSDB of class SSD has been created.");
    System.out.println("");
    SSDB.setPrice(979);
    SSDB.setBrand("Plextor");
    System.out.println("Brand: " + SSDB.getBrand());
    System.out.println("Cheap Brand: " + SSDB.isCheap());
    System.out.println("Category of SSDB: " + SSDB.category());
    SSDB.generateBar('*');
    System.out.println("");
    SSD SSDC = new SSD();
    System.out.println("");
    System.out.println("An object SSDC of class SSD has been created.");
  }
}
