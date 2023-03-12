package com.example.schoolproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.schoolproject.R;
import com.example.schoolproject.databinding.ActivityMainBinding;
import com.example.schoolproject.models.User;
import com.example.schoolproject.views.CreateGameFragment;
import com.example.schoolproject.views.EnterGameFragment;
import com.example.schoolproject.views.RegistrationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private BottomNavigationView bottomNavigationView;
    public static final String DATABASE_PATH = "https://schoolproject-7f38f-default-rtdb.europe-west1.firebasedatabase.app";
    public static User currentUser;
    AppBarConfiguration barConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        bottomNavigationView = activityMainBinding.bottomNavigationView;
        bottomNavigationView.setItemIconTintList(null);
        //setSupportActionBar(activityMainBinding.appBarMain.toolbar);
        DrawerLayout drawer = activityMainBinding.drawerLayout;
        NavigationView navigationView = activityMainBinding.navView;
        barConfiguration = new AppBarConfiguration.Builder(R.id.regestration)
                .setOpenableLayout(drawer)
                .build();

        setContentView(activityMainBinding.getRoot());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.signup:
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.nav_host_fragment, new RegistrationFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        drawer.closeDrawers();
                        break;
                }

                return false;
            }
        });

        activityMainBinding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.entergame:
                    bottomNavigationView.getMenu().findItem(R.id.entergame).setChecked(true);
                    Fragment fr1 = (Fragment) (new EnterGameFragment());
                    fragmentTransaction.replace(R.id.nav_host_fragment, fr1);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
                case R.id.create_game:
                    bottomNavigationView.getMenu().findItem(R.id.create_game).setChecked(true);
                    fragmentTransaction.replace(R.id.nav_host_fragment, new CreateGameFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
            }

            return false;
        });

        DatabaseReference ref = FirebaseDatabase.getInstance(DATABASE_PATH).getReference("/users");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);

                if (user.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    currentUser = user;
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}