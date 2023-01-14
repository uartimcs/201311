package comps311.a4.animation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: use the navigation graph
        // TODO: navigation in the action bar

        // The fragment is named nav_host
       NavController navController = Navigation.findNavController(this, R.id.nav_host);
       NavGraph navGraph = navController.getGraph();
       appBarSetting = new AppBarConfiguration.Builder(navGraph).build();
       NavigationUI.setupActionBarWithNavController(this, navController, appBarSetting);


    }

    // TODO: use the navigation graph
    // TODO: navigation in the action bar
    // p.34 of Study note 9
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        return NavigationUI.navigateUp(navController, appBarSetting) || super.onSupportNavigateUp();
    }
}
