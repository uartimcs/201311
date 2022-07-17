public class TestMasterMind {
    public static void main(String[] args) {
        //Q2b(iii)
        MasterMind game1 = new MasterMind(4);
        game1.printSecretCode();
        
        //Q2b(v)
        game1.play();
        System.exit(0);
    }
    
}
