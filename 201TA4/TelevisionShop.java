public class TelevisionShop {
    private Television[] televisions;
    
    public TelevisionShop(Television[] televisions) {
        this.televisions = televisions;
    }
    
    //getter method
    public Television[] getTelevisions() {
        return televisions;
    }
    
    //setter method
    public void setTelevisions(Television[] theTelevisions) {
        televisions = theTelevisions;
    }
    
    //Q1b(iv)
    public void displayTelevision(int index) {
        System.out.println("Television with index " + index);
        System.out.println("  Brand : " + televisions[index].getBrand());
        System.out.println("  Screen size : " + televisions[index].getScreenSize());
    }
    
    //Q1b(v)
    public int brandCount(String aBrand) {
        int counter = 0;
        for (Television s: televisions) {
            if(s.getBrand().equals(aBrand)) {
                counter++;
            }
        }
        return counter;
    }
    
    //Q1b(vi)
    public Television[] smallTelevisions() {
        //Create an array of Television of size 2.
        Television[] result = new Television[2];
        
        //result[0] holds the smallest, result[1] holds the next smallest
        //place the first television by default
        result[0] = televisions[0];
        result[1] = televisions[0];
        
        for(Television s: televisions) {
            //first case: < the smallest one
            if(s.getScreenSize() < result[0].getScreenSize()) {
                //assign the smallest to the next smallest
                result[1] = result[0];
                //Replace the smallest with a new Television
                result[0] = s;
            }
            //second case, < the next smallest one
            else if(s.getScreenSize() < result[1].getScreenSize()) {
                result[1] = s;
            }
        }
        return result;
    }
    
}
