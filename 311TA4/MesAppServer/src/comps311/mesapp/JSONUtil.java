package comps311.mesapp;

import org.json.JSONObject;

public class JSONUtil {
    private static final String AUTHOR = "author";
    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    private static final String TIME = "time";

    public static String fromMessage(Message message) {
        var json = new JSONObject();
        json.put(AUTHOR, message.getAuthor());
        if (message.getImage() != null) {
            json.put(IMAGE, message.getImage());
        } else {
            json.put(TEXT, message.getText());
        }
        json.put(TIME, message.getTime());
        return json.toString();
    }
    
    public static Message toMessage(String jsonString) {
        var json = new JSONObject(jsonString);
        var message = new Message();
        message.setAuthor(json.getString(AUTHOR));
        if (json.has(IMAGE)) {
            message.setImage(json.getString(IMAGE));
        } else {
            message.setText(json.getString(TEXT));
        }
        message.setTime(json.getString(TIME));
        return message;
    }
}