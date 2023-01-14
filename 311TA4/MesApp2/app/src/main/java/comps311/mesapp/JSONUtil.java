package comps311.mesapp;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JSONUtil {
    private static final String AUTHOR = "author";
    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    private static final String TIME = "time";

    private static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String fromMessage(Message message) {
        try {
            JSONObject json = new JSONObject();
            json.put(AUTHOR, message.getAuthor());

            if (message.getImage() != null) {
                String imageEncoded = Base64.encodeToString(
                        message.getImage(), Base64.DEFAULT);
                json.put(IMAGE, imageEncoded);
            } else {
                json.put(TEXT, message.getText());
            }
            json.put(TIME, formatDate.format(message.getTime()));
            return json.toString();
        } catch (JSONException e) {
            return null;
        }
    }

    public static Message toMessage(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            Message message = new Message();
            message.setAuthor(json.getString(AUTHOR));
            try {
                message.setTime(formatDate.parse(json.getString(TIME)));
            }
            catch (ParseException pe) {

            }
            if (json.has(IMAGE)) {
                String image = json.getString(IMAGE);
                byte[] imageDecoded = Base64.decode(image, Base64.DEFAULT);
                message.setImage(imageDecoded);
            } else {
                message.setText(json.getString(TEXT));
            }
            return message;
        } catch (JSONException e) {
            return null;
        }
    }
}