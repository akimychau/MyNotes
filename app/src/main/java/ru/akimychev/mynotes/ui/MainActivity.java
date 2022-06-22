package ru.akimychev.mynotes.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import ru.akimychev.mynotes.R;

public class MainActivity extends AppCompatActivity implements ToolbarHolder {
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);

        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.notes:
                        getSupportFragmentManager().popBackStack();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new NotesListFragment())
                                .commit();

                        drawerLayout.close();

                        return true;

                    case R.id.settings:
                        getSupportFragmentManager().popBackStack();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new SettingsFragment())
                                .commit();

                        drawerLayout.close();

                        return true;

                    case R.id.about:
                        getSupportFragmentManager().popBackStack();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new AboutFragment())
                                .commit();

                        drawerLayout.close();

                        return true;

                    case R.id.exit:
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(R.string.exit_title)
                                .setMessage(R.string.exit_message)
                                .setCancelable(false)
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                        Toast.makeText(MainActivity.this, R.string.exit_toast, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                }
                return false;
            }
        });

    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

}