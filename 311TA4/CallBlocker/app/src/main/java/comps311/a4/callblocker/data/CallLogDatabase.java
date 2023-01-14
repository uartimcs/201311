package comps311.a4.callblocker.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CallLog.class}, version = 1, exportSchema = false)
public abstract class CallLogDatabase extends RoomDatabase {
    public abstract CallLogDao callLogDao();

    public static ExecutorService executor = Executors.newFixedThreadPool(4);
    private static CallLogDatabase INSTANCE = null;

    public static CallLogDatabase getInstance(Context app) {
        if (INSTANCE == null) {
            synchronized (CallLogDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            app, CallLogDatabase.class, "CallLogDB").build();
                }
            }
        }
        return INSTANCE;
    }

    public void insert(Context context, CallLog callLog) {
        if (callLog.number != null) {
            executor.execute(
                    () -> getInstance(context).callLogDao().insert(callLog));
        }
    }
}