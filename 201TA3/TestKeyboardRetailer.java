public class TestKeyboardRetailer {
    
    public static void main(String[] args) {
        // Create an object of KeyboardRetailer
        KeyboardRetailer keyboardRetailer = new KeyboardRetailer();
        
        // Create 2 keyboard objects
        Keyboard keyboard1 = new Keyboard("Logitech", "K580", 229.0);
        Keyboard keyboard2 = new Keyboard("Microsoft", "N9Z", 228.0);
        
        // Add 2 keyboards object to the map of retailer
        keyboardRetailer.addKeyboard(keyboard1);
        keyboardRetailer.addKeyboard(keyboard2);
        keyboardRetailer.showKeyboard();
        System.out.println(keyboardRetailer.modelNumberSet());
        System.out.println(keyboardRetailer.priceList());
   
    }
}
