package com.example.task_manager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.task_manager.fragment.FeedbackFragment;
import com.example.task_manager.fragment.HomeFragment;
import com.example.task_manager.fragment.ShareFragment;
import com.example.task_manager.fragment.UpgradeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ListView listView;
    //private TextView emptyView;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_FEEDBACK = 1;
    private static final int FRAGMENT_SHARE = 2;
    private static final int FRAGMENT_UPGRADE = 3;
    private int currentFragment = FRAGMENT_HOME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Khởi tạo các thành phần giao diện người dùng
        setWidget();
        setSupportActionBar(toolbar);

        // Gán sự kiện mở/đóng DrawerLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Thiết lập sự kiện khi chọn mục trong NavigationView
        navigationView.setNavigationItemSelectedListener(this);

        // Hiển thị fragment Home ban đầu
        replaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        // Xử lý sự kiện click cho button
    }
    public void setWidget(){
        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        listView = findViewById(R.id.listView_Actived);

        //emptyView = findViewById(R.id.empty_view);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.nav_home){
            if(currentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                currentFragment = FRAGMENT_HOME;
            }
        } else if (id == R.id.nav_feedback) {
            if(currentFragment != FRAGMENT_FEEDBACK){
                replaceFragment(new FeedbackFragment());
                currentFragment = FRAGMENT_FEEDBACK;
            }
        } else if (id == R.id.nav_share) {
            if(currentFragment != FRAGMENT_SHARE){
                replaceFragment(new ShareFragment());
                currentFragment = FRAGMENT_SHARE;
            }
        } else if (id == R.id.nav_upgrade){
            if(currentFragment != FRAGMENT_UPGRADE){
                replaceFragment(new UpgradeFragment());
                currentFragment = FRAGMENT_UPGRADE;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }
}