package comps311.a4.callblocker;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comps311.a4.callblocker.data.CallLogItemAdapter;
import comps311.a4.callblocker.data.CallLogViewModel;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ANSWER_PHONE_CALLS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwitchCompat blockCallsSwitch = findViewById(R.id.block_calls);
        blockCallsSwitch.setChecked(isServiceRunning(this, CallService.class));
        blockCallsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO: if switch is checked and service is not running, start service
            // TODO: if switch is unchecked and service is running, stop service
            if (isChecked && !isServiceRunning(this, CallService.class)) {
                Intent intent = new Intent(this, CallService.class);
                startService(intent);
            }
            else if (!isChecked && isServiceRunning(this, CallService.class)) {
                Intent intent = new Intent(this, CallService.class);
                stopService(intent);
            }

        });

        CallLogItemAdapter adapter = new CallLogItemAdapter();
        CallLogViewModel model = new ViewModelProvider(this)
                .get(CallLogViewModel.class);
        model.getCallLogs().observe(this,
                callLogs -> adapter.setCallLogs(callLogs));

        RecyclerView recyclerView = findViewById(R.id.call_logs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        int perm0 = ContextCompat.checkSelfPermission(this, PERMISSIONS[0]);
        int perm1 = ContextCompat.checkSelfPermission(this, PERMISSIONS[1]);
        int perm2 = ContextCompat.checkSelfPermission(this, PERMISSIONS[2]);
        int perm3 = ContextCompat.checkSelfPermission(this, PERMISSIONS[3]);
        if (perm0 != PackageManager.PERMISSION_GRANTED ||
                perm1 != PackageManager.PERMISSION_GRANTED ||
                perm2 != PackageManager.PERMISSION_GRANTED ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                        perm3 != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
            String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length < PERMISSIONS.length ||
                        grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                        grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                        grantResults[2] != PackageManager.PERMISSION_GRANTED ||
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                                grantResults[3] !=
                                        PackageManager.PERMISSION_GRANTED)) {
                    String msg = "ERROR: Required permissions are not granted";
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    private static boolean isServiceRunning(Context context,
            Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices =
                manager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo service : runningServices) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
