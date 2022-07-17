import javax.swing.JOptionPane;

public class MasterMind {
    private int[] secretCode = new int[4];
    private int guessCount = 0;
    private int maxInteger;
    
    public MasterMind(int maxInteger) {
        for (int i = 0; i < secretCode.length; i++) {
            //e.g. maxInteger = 4 , use Math.random() can only generate 0 - 3 numbers
            secretCode[i] = (int) (Math.random() * (maxInteger + 1));
        }
        this.maxInteger = maxInteger;
    }
    
    public void printSecretCode() {
        String fullCode = "";
        for (int j = 0; j < secretCode.length; j++) {
            fullCode += secretCode[j];
        }
        System.out.println(fullCode);
    }
    
    public int[] oneGuess(int guess) {
        guessCount++;
        
        //Create an array of integer with size 2 to hold results
        int[] hint = new int[2];        
        
        //Set the array size of guessDigits equals to the size of secretCode
        int[] guessDigits = new int[secretCode.length];
        
        
        //Convert guess number to corresponding int[]
        for(int i = secretCode.length - 1; i >= 0; i--) {   //index 3,2,1,0 with size = 4
            guessDigits[i] = guess % 10;
            guess = guess / 10;
        }
        // e.g. 1234 becomes [0] -> 1, [1] -> 2, [2] -> 3, [3] -> 4
        //Q2b(vi)
        int[] secretCodeClone = secretCode.clone();
        
        int sameCounter = 0;    // count same position and digits
        for(int j = 0; j < secretCode.length; j++) {
            if(guessDigits[j] == secretCode[j]) {
                sameCounter++;
                //Q2b(vi)
                guessDigits[j] = -1;
                secretCodeClone[j] = -1;
                
            }
        }
        
        hint[0] = sameCounter;
        hint[1] = findHint1(guessDigits, secretCodeClone);
        
        return hint;
    }
    
    public void play() {
        //start the game, false when the guess is correct.
        boolean gameStatus = true;
        
        int[] hint = new int[2];
        
        //Message box message
        String message = "";
        
        while(gameStatus) {
            String inputGuess = JOptionPane.showInputDialog("Please enter an integer with digits in the range 0 to " + maxInteger);
            int gameGuess = Integer.parseInt(inputGuess);
            
            hint = oneGuess(gameGuess);
                
                if(hint[0] == secretCode.length) {
                    //end the game
                    gameStatus = false;
                    JOptionPane.showMessageDialog(null, "Your guess is correct");
                    JOptionPane.showMessageDialog(null, "Total number of guess : " + guessCount);
                }
                
                else {
                    
                    //Printed message conditions
                    if (hint[0] <= 1 && hint[1] <= 1) {
                        message = hint[0] + " position match and " + hint[1] + " digit match";
                    }
                    else if (hint[0] <= 1 && hint[1] > 1) {
                        message = hint[0] + " position match and " + hint[1] + " digit matches";
                    }
                    
                    else if (hint[0] > 1 && hint[1] <= 1) {
                        message = hint[0] + " position matches and " + hint[1] + " digit match";
                    }
                    
                    else {
                        message = hint[0] + " position matches and " + hint[1] + " digit matches";
                    }
                    
                    JOptionPane.showMessageDialog(null, message);                    
                }
            
        }
        System.exit(0);
        
    }
    
    public int findHint1(int[] guess, int[] secret) {
        int counter = 0;
        for(int i = 0; i < guess.length; i++) {
            for(int j = 0; j < secret.length; j++) {
                if(guess[i] == -1) {
                    break;
                }
                else if (guess[i] == secret[j]) {
                    counter++;
                    guess[i] = -1;
                    secret[j] = -1;
                }
       
            }
        }
                
        return counter;
    }
    
}
