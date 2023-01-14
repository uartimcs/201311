package comps311.mesapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment {
    public static class AppViewModel extends ViewModel {
        public Socket socket;
        public String displayName;
        public List<Message> messages = new ArrayList<>();
        public RecyclerView recyclerView;
    }

    protected AppViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity())
                .get(AppViewModel.class);
    }

    public void toast(String string) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),
                string, Toast.LENGTH_SHORT).show());
    }
}
