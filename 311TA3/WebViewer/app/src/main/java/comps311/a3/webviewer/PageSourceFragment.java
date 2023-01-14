package comps311.a3.webviewer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PageSourceFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_source, container, false);
        TextView pageSourceView = view.findViewById(R.id.page_source_view);
        pageSourceView.setText(((MainActivity) getActivity()).pageSource.toString());
        return view;
    }


}