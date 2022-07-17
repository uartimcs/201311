public class Pattern {
    public static void main(String[] args) {
        //Q2a (i)
        
        //Upper three lines
        for (int i = 1; i <= 3; i++) {      //1,2,3
            for (int j = 0; j < i; j++) {
                System.out.print("**");
            }
            System.out.println();
        }
        
        //Lower two lines, reversed shape
        for (int i = 2; i > 0; i--) {       //2,1
            for (int j = 0; j < i; j++) {
                System.out.print("**");
            }
            System.out.println();
        }
        
        
        System.out.println();
        
        //Q2a (ii)
        //Upper three lines
        for (int i = 1; i <= 3; i++) {      //1,2,3
            for (int j = i; j < 3; j++) {
                System.out.print(" ");
            }
                       
            for (int k = 0; k < i; k++) {
                System.out.print("*");
            }
            
            if (i == 3) {
                //prevent direct print, use for-loop to generate
                for (int a = 0; a < 5; a++){
                    System.out.print("*");
                }
            }
            System.out.println();
        }

        //Lower two lines
        for (int i = 2; i > 0; i--) {      //2,1
            for (int j = i; j < 3; j++) {
                System.out.print(" ");
            }
                       
            for (int k = 0; k < i; k++) {
                System.out.print("*");
            }
            
            System.out.println();
        }        
        
        
        
        
        
 
    } 
}
