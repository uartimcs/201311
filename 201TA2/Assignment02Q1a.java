public class Assignment02Q1a {
 public static void main(String[] args) {
 int from = 2;
 int to = 8;
 int product = 1;

 for (int i=from; i<=to; i+=3) { // (1)
 product *= i; // (2)
 }

 System.out.println("The product is "+product);
 }
}
