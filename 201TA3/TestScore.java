public class TestScore {
    public static void main(String[] args) {
        // Create an object of Score called score.
        Score score = new Score();
        //System.out.println(score) is also ok. it calls toString() method automatically.
        System.out.println(score.toString());
        
        //Part (vii) I add some description to make it more readable.
        System.out.println("The third value is: " + score.getValue(2));
        score.adjustScore(2, -9);
        System.out.println("Adjusted Score after decreased by 9: "+ score.getValue(2));
        
        int counter = score.countPass(39.5);
        System.out.println("Number of values larger than 39.5: " + counter);
        
        System.out.println("Maximum index: " + score.maximumIndex());
        System.out.println("Trimmed mean: " + score.trimmedMean());        
       
    }    
}
