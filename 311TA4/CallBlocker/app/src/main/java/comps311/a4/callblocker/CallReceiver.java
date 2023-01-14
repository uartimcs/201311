package comps311.a4.callblocker;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

import comps311.a4.callblocker.data.CallLog;
import comps311.a4.callblocker.data.CallLogDatabase;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isIncomingCall(intent)) {
            return;
        }
        String number = getCallNumber(intent);
        // TODO: create CallLog object
        CallLog callLogger = new CallLog(number, false);

        if (isJunkCall(number)) {
            String message = "Junk call from " + number;
            // TODO: set CallLog's blocked field to true
            callLogger.blocked = true;
            // TODO: block call, show toast and notification
            blockCall(context);
            String messageShown = "Junk call from " + number;
            Toast.makeText(context, messageShown,Toast.LENGTH_LONG).show();
            Notification notification = NotificationHelper.buildNotification(context, messageShown);
            NotificationManagerCompat.from(context).notify(NotificationHelper.NOTICE_ID, notification);

        }
        // TODO: insert CallLog to database
        CallLogDatabase db = CallLogDatabase.getInstance(context);
        db.insert(context, callLogger);
    }

    private static String getCallNumber(Intent intent) {
        String number = intent.getStringExtra(
                TelephonyManager.EXTRA_INCOMING_NUMBER);
        return number;
    }

    private static boolean isIncomingCall(Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        boolean incoming = state.equals(TelephonyManager.EXTRA_STATE_RINGING);
        return incoming;
    }

    private static boolean isJunkCall(String number) {

        // Tutor suggest that for API level 32, The system will add +852 before the telephone number
        // Further Check the number starting
        //if (number != null && number.startsWith("+852")) {
            //number = number.substring(4);
        //}

        if (number != null && number.length() > 0) {
            char firstDigit = number.charAt(0);
            if ("24680".indexOf(firstDigit) >= 0) {
                return true;
            }
        }
        return false;
    }

    private static void blockCall(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { // 9.0/API 28+
            TelecomManager tm = (TelecomManager) context.getSystemService(
                    Context.TELECOM_SERVICE);
            if (tm != null) {
                tm.endCall();
            }
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 8.0/API 26+
            String message = "Cannot terminate call for API level " +
                    Build.VERSION.SDK_INT;
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            return; // Cannot modify call
        }
        TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class<?> c = Class.forName(telephony.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            ITelephony telephonyService = (ITelephony) m.invoke(telephony);
            // telephonyService.silenceRinger();
            telephonyService.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
