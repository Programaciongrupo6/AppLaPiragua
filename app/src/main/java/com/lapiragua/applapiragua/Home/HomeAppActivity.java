package com.lapiragua.applapiragua.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lapiragua.applapiragua.R;
import com.lapiragua.applapiragua.databinding.ActivityHomeAppBinding;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

public class HomeAppActivity extends AppCompatActivity {
    private ActivityHomeAppBinding binding;

    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        binding = ActivityHomeAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.botton_navigation);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_registrar, R.id.navigation_buscar, R.id.navigation_qr, R.id.navigation_usuario)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_app);
        // esta linea da error al ocultar el Bar superior, por que intenta setTitle
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottonNavigation, navController);


        /*
        setContentView(R.layout.activity_home_app);
        navigationView = findViewById(R.id.botton_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout_home,new FragmentHome()).commit();
        navigationView.setSelectedItemId(R.id.fragmentHome);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment =null;
                switch (item.getItemId()){
                    case R.id.fragmentHome:
                        fragment = new FragmentHome();
                        break;
                    case R.id.fragmentRegistrar:
                        fragment = new FragmentRegistrar();
                        break;
                    case R.id.fragmentBuscar:
                        fragment = new FragmentBuscar();
                        break;
                    case R.id.fragmentQR:
                        fragment = new FragmentQR();
                        break;
                    case R.id.fragmentUsuario:
                        fragment = new FragmentUsuario();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout_home, fragment).commit();
                return false;
            }
        });

         */
    }
}