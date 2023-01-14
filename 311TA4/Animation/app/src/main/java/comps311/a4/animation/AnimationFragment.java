package comps311.a4.animation;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

public class AnimationFragment extends Fragment {
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.animation_fragment, null);
        textView = view.findViewById(R.id.textview);

        // TODO: create an Animation object that referenced by the created animation XML resource
        // TODO: start the animation effect that performs on the TextView attribute
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_scale);
        textView.startAnimation(animation);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        // TODO: read the shared preferences data and set up the TextView
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String textSet = sharedpreferences.getString("text_size","30");
        textView.setTextSize(Integer.parseInt(textSet));

        String textColorSet = sharedpreferences.getString("text_color", "Black");
        textView.setTextColor(Color.parseColor(textColorSet));

        String bgColorSet = sharedpreferences.getString("background_color", "White");
        textView.setBackgroundColor(Color.parseColor(bgColorSet));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.animation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO: implement navigation using menus
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host);

        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
}
