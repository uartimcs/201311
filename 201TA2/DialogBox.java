import javax.swing.JOptionPane;

    public class DialogBox {
        
    public double inputAUDAmount() {
        String inputValue = JOptionPane.showInputDialog(null, "Input the AUD Amount: ");
        
        int confirmCode = JOptionPane.showConfirmDialog(null, "The input is: " + inputValue + " , is it correct?");
        
        if (confirmCode == JOptionPane.YES_OPTION) {
            return Double.parseDouble(inputValue);
        }
        else {
            return 0.0; //return type: real number, double
        }
    }
    
    public void checkAUDAmount() {
        double audValue;
        
        do {
            audValue = inputAUDAmount();
            
            if(audValue <= 0) {
                
            JOptionPane.showMessageDialog(null, "The AUD amount should be greater than zero");
            
            } 
        
        } while(audValue <= 0);  
        
       
    }
    
    public static void main(String[] args) {
        DialogBox checker = new DialogBox();
        checker.checkAUDAmount();
        //System.exit(0); Add when in Q2
        //For Q3 I have to remove the system.exit(0);
        //Although it will show "DialogBox Launched", the Launcher program will close after complete running the program DialogBox instead of keeping the state
    }
}
