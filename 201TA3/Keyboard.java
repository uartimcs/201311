public class Keyboard {
    //attributes 
    private String brand;
    private String modelNum;
    private double price;
    
    //Constructor
    public Keyboard(String brand, String modelNum, double price) {
        this.brand = brand;
        this.modelNum = modelNum;
        this.price = price;
    }
    
    // Getter methods clusters
    public String getBrand() {
        return brand;
    }    
    public String getModelNum() {
        return modelNum;
    }
    public double getPrice() {
        return price;
    }
    
    //toString() method
    public String toString() {
        return "Brand: " + getBrand() + ", model number: " + getModelNum() + ", price: " + getPrice();
    }
}
