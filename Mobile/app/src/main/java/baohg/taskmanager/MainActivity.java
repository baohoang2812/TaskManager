package baohg.taskmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.constants.RoleConstant;

public class MainActivity extends AppCompatActivity {
    private ActionBar toolBar;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("baohg.taskmanager_preferences", MODE_PRIVATE);
        String userRole = sharedPreferences.getString("userRole", "");
        toolBar = getSupportActionBar();
        toolBar.setTitle("Tasks");
        loadFragment(new TaskFragment());
        navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_tasks: {
                        toolBar.setTitle("Tasks");
                        fragment = new TaskFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.navigation_group: {
                        toolBar.setTitle("Group");
                        fragment = new GroupFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.navigation_user:{
                        toolBar.setTitle("Users");
                        fragment = new UserFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.navigation_profile: {
                        toolBar.setTitle("Profile");
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        return true;
                    }
                }
                return false;
            }
        };
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        if (!RoleConstant.ADMIN.equalsIgnoreCase(userRole)){
            bottomNavigationView.getMenu().findItem(R.id.navigation_user).setVisible(false);
        }
            bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }
}
