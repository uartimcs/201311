package comps311.mesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.IOException;
import java.net.Socket;

public class MainFragment extends BaseFragment {
    private static final String PREF_HOST = "pref_host";
    private static final String PREF_PORT = "pref_port";
    private static final String PREF_NAME = "pref_name";
    private static final String DEFAULT_PORT = "3311";

    private EditText hostView;
    private EditText portView;
    private EditText nameView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        hostView = view.findViewById(R.id.server_host);
        portView = view.findViewById(R.id.server_port);
        nameView = view.findViewById(R.id.display_name);
        SharedPreferences prefs
                = getActivity().getPreferences(Context.MODE_PRIVATE);
        hostView.setText(prefs.getString(PREF_HOST, ""));
        hostView.setSelection(hostView.getText().length());
        portView.setText(prefs.getString(PREF_PORT, DEFAULT_PORT));
        nameView.setText(prefs.getString(PREF_NAME, ""));
        view.findViewById(R.id.ok).setOnClickListener(v -> handleOkButton());
        return view;
    }

    private void handleOkButton() {
        String host = hostView.getText().toString().trim();
        String port = portView.getText().toString().trim();
        String name = nameView.getText().toString().trim();
        if (!checkUserInput(host, port, name)) {
            return;
        }
        getActivity().getPreferences(Context.MODE_PRIVATE).edit()
                .putString(PREF_HOST, host)
                .putString(PREF_PORT, port)
                .putString(PREF_NAME, name)
                .apply();
        new Thread(() -> startMessaging(host, port, name)).start();
    }

    private void startMessaging(String host, String port, String name) {
        try {
            Socket socket = new Socket(host, Integer.parseInt(port));
            viewModel.socket = socket;
            viewModel.displayName = name;
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MessageFragment())
                    .commit();
        } catch (IOException e) {
            toast(e.getMessage());
        }
    }

    private boolean checkUserInput(String host, String port, String name) {
        StringBuilder errors = new StringBuilder();
        if (host.isEmpty()) {
            errors.append("Please enter the server host.\n");
        }
        if (port.isEmpty()) {
            errors.append("Please enter the server port number.\n");
        } else {
            try {
                int portInt = Integer.parseInt(port);
                if (portInt <= 0 || portInt > 65535) {
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                errors.append(port).append(" is not a valid port number.\n");
            }
        }
        if (name.isEmpty()) {
            errors.append("Please enter your display name.\n");
        }
        if (!errors.toString().isEmpty()) {
            toast(errors.toString().trim());
            return false;
        }
        return true;
    }
}
