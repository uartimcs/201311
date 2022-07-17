public class TestTelevisionShop {
    public static void main(String[] args) {
    //Q1b (vii)    
    //Set an array of Television of size 3    
    Television[] televisionSets = new Television[3];
    televisionSets[0] = new Television("LG", 55);
    televisionSets[1] = new Television("Samsung", 48);
    televisionSets[2] = new Television("Sony", 65);
    
    //Place the TVsets as the constructor parameter
    TelevisionShop myTelevisionShop = new TelevisionShop(televisionSets);       
    myTelevisionShop.displayTelevision(2);
    System.out.println("Number of televisions with brand \"Sony\" : " + myTelevisionShop.brandCount("Sony"));
    System.out.println();
    
    for (int i = 0; i < myTelevisionShop.smallTelevisions().length; i++) {
        if (i == 0) {
            System.out.println("The smallest television :");
        }
        else {
            System.out.println("The next smallest television :");
        }
        System.out.println("  Brand : " + myTelevisionShop.smallTelevisions()[i].getBrand());
        System.out.println("  Screen size : " + myTelevisionShop.smallTelevisions()[i].getScreenSize());
        System.out.println();
    }
       
    }
}
