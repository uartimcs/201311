import java.util.*;

public class KeyboardRetailer {
    // Set a map to hold the (key,value)
    private Map<String, Keyboard> keyboardMap = new TreeMap<>();
    
    // method to add a new keyboard
    public void addKeyboard(Keyboard keyboard) {
        String key = keyboard.getBrand() + ": " + keyboard.getModelNum();
        Keyboard value = keyboard;
        keyboardMap.put(key, value);
    }
    
    public void showKeyboard() {
        for (String key : keyboardMap.keySet()) {
            // for each key obtained, get the corresponding object from the value.
            Keyboard obj = keyboardMap.get(key);
            System.out.println(obj);
        }
    }

    public Set<String> modelNumberSet() {
        Set<String> extractSet = new HashSet<>();
    // for each keyboard object obtained, get the corresponding model number from the getter method.
        for(Keyboard obj : keyboardMap.values()) {
            extractSet.add(obj.getModelNum());
        }
        return extractSet;
    }
    
    public List<Double> priceList() {
        List<Double> aList = new ArrayList<>();
        // for each keyboard object obtained, get the corresponding price from the getter method.
        for(Keyboard obj: keyboardMap.values()) {
            aList.add(obj.getPrice());
        }
        return aList;
    }   
}