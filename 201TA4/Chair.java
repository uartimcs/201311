public class Chair {
    private String manufacturer;
    private double price;
    
    public Chair(String manufacturer, double price) {
        this.manufacturer = manufacturer;
        this.price = price;
    }
    
    //getter methods - Q3(b)
    public String getManufacturer() {
        return manufacturer;
    }
    public double getPrice() {
        return price;
    }
    
    //setter methods - Q3(b)
    public void setManufacturer(String theManufacturer) {
        manufacturer = theManufacturer;
    }
    public void setPrice(double thePrice) {
        price = thePrice;
    }
    
    //toString() method - Q3(b)
    public String toString() {
        return "Chair: " + manufacturer + ", $" + (int) price;      //show 200 instead of 200.0
    }
    
    public static double averagePrice(Chair[] chairs) {
        double sum = 0;
        for(Chair s: chairs) {
            sum += s.getPrice();
        }
        
        return sum/chairs.length;
    }
}
