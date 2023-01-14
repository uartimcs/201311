package comps311.a4.callblocker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {
    private static final String CHANNEL_ID = "CHANNEL_CALL_BLOCKER";
    public static final int NOTICE_ID = 1;

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 8.0/API 26+
            CharSequence name = "call blocker channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationManagerCompat mgr =
                    NotificationManagerCompat.from(context);
            mgr.createNotificationChannel(channel);
        }
    }

    public static Notification buildNotification(Context context,
            String message) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context.getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_menu_call)
                        .setTicker("Call Blocker")
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle("Call Blocker")
                        .setContentText(message)
                        .setContentIntent(pendingIntent)
                        .build();
        return notification;
    }
}
