import javax.swing.JOptionPane;

public class TestTelevision {
    public static void main(String[] args) {
        // Q1b (ii) create an object televisionA
       Television televisionA = new Television();
        
        System.out.println("An object televisionA of class Television has been created");
        
        // Q1b (vi)
        
        int confirmStatus = 1; //Initial entrance of while loop
        
        while (confirmStatus != 0) {
            String sizeConfirm = JOptionPane.showInputDialog("Input a value for size (>0)");
            double receiveValue = Double.parseDouble(sizeConfirm);
            
            if(receiveValue <= 0) {
                String output = "Size must be > 0";
                JOptionPane.showMessageDialog(null, output); 
            }
            else {
                confirmStatus = 0;  // end the while loop
                televisionA.setSize(receiveValue);
                System.out.println("The category is "+ televisionA.category());
            }
        }
       
       //System.exit(0); 
        //Add when in Q1

        //For Q3 I have to remove the system.exit(0);
        //Although it will show "TestTelevision Launched", the Launcher program will close after complete running the program TestTelevision instead of keeping the state
    }
}
