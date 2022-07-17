public class Recursion {
    public static void recursivePrint(int count) {
        if (count <= 0) {
            return;
        }
        else {
            for(int i = count; i > 0; i--) {
                System.out.print(2*i + " ");
            }
            System.out.println();
        }
        recursivePrint(count-1);
    }
    
}
