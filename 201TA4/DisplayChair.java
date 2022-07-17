public class DisplayChair extends Chair {
    private double discountPercent;
    
    public DisplayChair(String manufacturer, double price, double discountPercent) {
        super(manufacturer, price);
        this.discountPercent = discountPercent;
    }
    
    //getter method
    public double getDiscountPercent() {
        return discountPercent;
    }
    
    //setter method
    public void setDiscountPercent(double theDiscountPercent) {
        discountPercent = theDiscountPercent;
    }
    
    public String toString() {
        return super.toString() + " and " + discountPercent + "%";
    }
    //Q3(g)
    public double getPrice() {
        return super.getPrice() * (1-discountPercent/100);
    }
}
