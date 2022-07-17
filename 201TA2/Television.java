public class Television {
    //attributes in Q1 b(i)
    private String brand;
    private double size;
    
    // b(iii) set size
    public void setSize(double inputSize) {
        size = inputSize;
    }
    
    //getter method of size
    public double getSize() {
        return size;
    }
    
    //setter method of brand
    public void setBrand(String inputBrand) {
        brand = inputBrand;
    }
    
    // b(iv) get Brand
    public String getBrand() {
        return brand;
    }
    
    // b (v)
    public String category() {
        String returnCategory;
       
        if (size >= 55) {
            returnCategory = "Big";
        }
        
        else if ((size >= 40) && (size <55)) {
            returnCategory = "Medium";
        }
        
        else {
            returnCategory = "Small"; // de Morgan's Law , i.e. size < 40
        }
        
        return returnCategory;
    }
}
