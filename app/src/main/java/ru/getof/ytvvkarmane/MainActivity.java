package ru.getof.ytvvkarmane;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.getof.ytvvkarmane.Adapters.FragmentHomeAdapter;
import ru.getof.ytvvkarmane.Components.WeatherData;
import ru.getof.ytvvkarmane.DialogFragments.GlobalFrag;
import ru.getof.ytvvkarmane.Utils.AuthUser;
import ru.getof.ytvvkarmane.Utils.RetrofitClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final int REQUEST_CODE_AUTH = 1;
    public static String loginAC = null, passAC = null;

    private int[] imgTabs = {R.drawable.ic_home_tab,R.drawable.ic_efir_tab, R.drawable.ic_messages};
    private AuthUser authUser;
    private Menu mainMenu;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FragmentHomeAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProgressBar progressBarWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*authUser = AuthUser.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null){
                authUser.setUser(true);
                mainMenu.getItem(0).setIcon(R.drawable.ic_sign_out);
            }
        };*/

        progressBarWeather = findViewById(R.id.progressBar_weather);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                initWeather();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (!isOnline(getApplicationContext())) {
            GlobalFrag newGlobalFrag = GlobalFrag.newInstance("NoInternet");
            newGlobalFrag.show(getSupportFragmentManager(), "GLOBAL");
        } else {
            initTabs();
            initWeather();
        }

        //initTabs();
    }

    private void initWeather() {
        progressBarWeather.setVisibility(View.VISIBLE);
        RetrofitClient.getInstance()
                .getApi()
                .getWeather()
                .enqueue(new Callback<WeatherData>() {
                    @Override
                    public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                        if (response.isSuccessful()){
                            progressBarWeather.setVisibility(View.GONE);
                            loadWeather(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherData> call, Throwable t) {

                    }
                });


    }

    private void loadWeather(WeatherData body) {
        TextView wTemp = findViewById(R.id.wTemp);
        TextView wPress = findViewById(R.id.wPress);
        TextView wHum = findViewById(R.id.wHum);
        wTemp.setText(body.getTemp());
        wPress.setText(body.getPress());
        wHum.setText(body.getHum());
    }

    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void initTabs() {
        viewPager = findViewById(R.id.viewpager_main);
        tabLayout = findViewById(R.id.tabLayout_main);
        adapter = new FragmentHomeAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < imgTabs.length; i++) {
            Objects.requireNonNull(tabLayout.getTabAt(i)).setIcon(imgTabs[i]);
        }
        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = adapter.getFragment(position);
                if (fragment != null) {
                    fragment.onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mainMenu = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_sign_in_out) {
            if (!authUser.getUser()) {
                Intent intent = new Intent(getApplicationContext(), AuthActivity.class);
                startActivityForResult(intent, REQUEST_CODE_AUTH);
            } else {
                authUser.setUser(false);
                item.setIcon(R.drawable.ic_sign_in);
                Fragment fragment = adapter.getFragment(2);
                if (fragment != null) {
                    fragment.onResume();
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_AUTH){
                loginAC = data.getStringExtra("login");
                passAC = data.getStringExtra("password");
                mainMenu.getItem(0).setIcon(R.drawable.ic_sign_out);*/
                /*adapter.notifyDataSetChanged();
                for (int i = 0; i < imgTabs.length; i++) {
                    tabLayout.getTabAt(i).setIcon(imgTabs[i]);
                }*/
                /*Fragment fragment = adapter.getFragment(2);
                if (fragment != null) {
                    fragment.onResume();
                }
            }

        }
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_help) {
            GlobalFrag newGlobalFrag = GlobalFrag.newInstance("PressHelp");
            newGlobalFrag.show(getSupportFragmentManager(), "GLOBAL");
            // Handle the camera action
        } //else if (id == R.id.nav_gallery) {

        //} else if (id == R.id.nav_slideshow) {

        //} else if (id == R.id.nav_tools) {

        //} else if (id == R.id.nav_share) {

       // } else if (id == R.id.nav_send) {

       // }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
