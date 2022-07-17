public class Score {
    private double value[] = {28.5, 25, 46, 78.5, 90};
    
    public String toString() {
        String msg = "";
        for (int i = 0; i < value.length; i++) {
        // link each array value to a string by concatenation
            msg += value[i] +"\n";
        }
        return msg;
    }
    public void adjustScore(int index, double scoreChange) {
        value[index] += scoreChange;
    }
    
    public double getValue (int index) {
        return value[index];
    }
    
    public int countPass(double threshold) {
        int counter = 0;
        // Load each array element 
        for(int i = 0; i <value.length; i++)
        {
            // if the value is greater than threshold, counter + 1
            if (value[i] > threshold) {
                counter++;
            }
        }
        return counter;
    }
    public int maximumIndex() {
        //assume maximum value is at value[0]

        int maximum = 0;
        // loop each array element, if greater, update maximum with j
        // j starts at 1 because we assume maximum at j = 0 already.
        for(int j = 1; j <value.length; j++) {
            if (value[j] > value[maximum]) {
                maximum = j;
            }
        }
        return maximum;
    }
    public double trimmedMean() {
        //maximum value is value[maximumIndex()];
        // get the maxValue using the method
        int maxIndex = maximumIndex();
        double maxValue = value[maxIndex];
        double average;
        // assume min value is at index 0, update it when searching the array elements.
        double minValue = value[0];       
        
        // counter to sum all numbers in an array
        double sum = 0;


        for (int j = 0; j < value.length; j++) {
            sum += value[j];
            // Update the min value when array element is smaller.
            if(minValue > value[j]) {
                minValue = value[j];
            }
        }
        
        // after loop we have min, max and total sum
        int trimmedItems = value.length - 2;
        double trimmedSum = sum - minValue - maxValue;
        
        average = trimmedSum/trimmedItems;
        
        return average;
    }
}    