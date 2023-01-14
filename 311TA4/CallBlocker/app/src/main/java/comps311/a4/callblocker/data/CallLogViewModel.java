package comps311.a4.callblocker.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CallLogViewModel extends AndroidViewModel {
    private LiveData<List<CallLog>> callLogs = null;

    public CallLogViewModel(Application application) {
        super(application);
    }

    public LiveData<List<CallLog>> getCallLogs() {
        if (callLogs == null) {
            CallLogDatabase db = CallLogDatabase.getInstance(getApplication());
            callLogs = db.callLogDao().getAll();
        }
        return callLogs;
    }
}