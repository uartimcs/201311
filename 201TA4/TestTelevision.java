public class TestTelevision {
    public static void main(String[] args) {
        Television aTelevision = new Television("LG", 55);
        System.out.println("A Television object has been created: " +
                            "brand is " + aTelevision.getBrand() + ", screen size is " + aTelevision.getScreenSize());
   
    }
    
}
