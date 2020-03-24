package baohg.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import baohg.taskmanager.baohg.constants.RoleConstant;
import baohg.taskmanager.baohg.daos.UserDAO;
import baohg.taskmanager.baohg.dtos.UserDTO;
import baohg.taskmanager.baohg.request.GetUserRequest;
import baohg.taskmanager.baohg.responses.GetUserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActionBar toolBar;
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener;
    String userRole;
    Integer groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("baohg.taskmanager_preferences", MODE_PRIVATE);
        userRole = sharedPreferences.getString("userRole", "");
        int idGroup = sharedPreferences.getInt("groupId", 0);
        groupId = idGroup != 0 ? idGroup : null;
        toolBar = getSupportActionBar();
        toolBar.setTitle("Tasks");
        loadFragment(new TaskFragment());
        navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_tasks: {
                        setActionBarTitle("Tasks");
                        fragment = new TaskFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.navigation_group: {
                        setActionBarTitle("Group");
                        fragment = new GroupFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.navigation_user: {
                        setActionBarTitle("Users");
                        fragment = new UserFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.navigation_profile: {
                        setActionBarTitle("Profile");
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        return true;
                    }
                }
                return false;
            }
        };
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        if (!RoleConstant.ADMIN.equalsIgnoreCase(userRole)) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    String txtUserId = result.getContents();
                    int userId = Integer.parseInt(txtUserId);
                    getUserDetail(userId);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Invalid UserId", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getUserDetail(final int userId) {
        UserDAO userDAO = new UserDAO();
        GetUserRequest request = new GetUserRequest();
        if (RoleConstant.MANAGER.equalsIgnoreCase(userRole)) {
            request.setGroupId(groupId);
        }
        userDAO.getAllUser(request, new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<UserDTO> userList = response.body().getData();
                        boolean check = false;
                        for (UserDTO user : userList) {
                            if (user.getUserId() == userId) {
                                check = true;
                            }
                        }
                        if (check) {
                            UserDetailFragment userDetailFragment = new UserDetailFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("userId", userId);
                            userDetailFragment.setArguments(bundle);
                            getSupportFragmentManager()
                                    .beginTransaction().replace(R.id.fragmentContainer, userDetailFragment).commitAllowingStateLoss();
                        } else {
                            Toast.makeText(MainActivity.this, "User Not found. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setActionBarTitle(String title){
        toolBar.setTitle(title);
    }
}
