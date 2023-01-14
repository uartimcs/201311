package comps311.a4.callblocker;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.TelephonyManager;

public class CallService extends Service {
    private CallReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: create notification channel, set service to foreground
        // TODO: create and register receiver
        NotificationHelper.createNotificationChannel(this);
        Notification notification = NotificationHelper.buildNotification(this, "Block all junk calls");
        startForeground(NotificationHelper.NOTICE_ID, notification);
        IntentFilter filter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        receiver = new CallReceiver();
        registerReceiver(receiver, filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO: unregister receiver
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }
}
