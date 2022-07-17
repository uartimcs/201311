public class Television {
    private String brand;
    private int screenSize;
    
    public Television(String brand, int screenSize) {
        this.brand = brand;
        this.screenSize = screenSize;
    }
    
    //getter methods
    public String getBrand() {
        return brand;
    }
    public int getScreenSize() {
        return screenSize;
    }
    
    //setter methods
    public void setBrand(String theBrand) {
        brand = theBrand;
    }
    public void setScreenSize(int theScreenSize) {
        screenSize = theScreenSize;
    }
    
}
