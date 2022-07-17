public class TestChair {
    
    public static void main(String[] args) {
        //Q3(c)
        Chair chair1 = new Chair("Manufacturer A", 200);
        System.out.println(chair1);

        //Q3(f)
        Chair chair2 = new DisplayChair("Manufacturer B", 350, 40.0);
        System.out.println(chair2);

        Chair[] chairs = new Chair[2];
        chairs[0] = chair1;
        chairs[1] = chair2;
        
        //Maybe end in decimal places , no casting to int in this case
        System.out.println("The average price is $" + Chair.averagePrice(chairs));
        
    }
    
}
