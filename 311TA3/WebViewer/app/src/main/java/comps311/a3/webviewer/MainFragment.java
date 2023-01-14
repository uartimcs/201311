package comps311.a3.webviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class MainFragment extends Fragment {
    private EditText url;
    private TextView historyView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        url = view.findViewById(R.id.url);
        historyView = view.findViewById(R.id.history_view);
        view.findViewById(R.id.go_to).setOnClickListener(v -> goToUrl());
        view.findViewById(R.id.view_history).setOnClickListener(v -> historyView.setText(((MainActivity) getActivity()).history));
        view.findViewById(R.id.view_source).setOnClickListener(v -> viewSource());
        return view;
    }

    public void addHistory(String address) {
        ((MainActivity) getActivity()).history.append(address + " " + new Date() + "\n");
    }

    private void goToUrl() {
        String address = url.getText().toString();
        try {
            Uri uri = Uri.parse(address);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            addHistory(address);
        }
        catch (Exception e) {
            Toast toast = Toast.makeText(getContext(), "Cannot access: " + address, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void viewSource() {
        String address = url.getText().toString();

        Runnable runnable = () -> {
            try {
                URL u = new URL(address);
                try (BufferedReader aReader = new BufferedReader(new InputStreamReader(u.openStream()))) {
                    ((MainActivity) getActivity()).pageSource = new StringBuilder();
                    String aLine = "";
                    while ((aLine = aReader.readLine()) != null) {
                        ((MainActivity) getActivity()).pageSource.append(aLine);
                    }
                    addHistory(address);
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new PageSourceFragment())
                            .addToBackStack(null)
                            .commit();
                }
                catch (Exception e) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Error (Reading page source):" + e.getMessage(),Toast.LENGTH_SHORT).show());
                }

            }

            catch (Exception e) {
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Error (URL):" + e.getMessage(),Toast.LENGTH_SHORT).show());
            }
        };
        new Thread(runnable).start();

    }

}
