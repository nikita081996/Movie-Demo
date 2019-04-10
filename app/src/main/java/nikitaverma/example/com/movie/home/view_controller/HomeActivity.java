package nikitaverma.example.com.movie.home.view_controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nikitaverma.example.com.movie.Constants;
import nikitaverma.example.com.movie.R;
import nikitaverma.example.com.movie.home.model_controller.ItemClickListener;
import nikitaverma.example.com.movie.utils.MyFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ItemClickListener {
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private HomeFragment mHomeFragment;
    private TopRatedFragment mTopRatedFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindAllView();
        if (mToolbar != null)
            setSupportActionBar(mToolbar);
        registerListener();
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        myFragment = new MyFragment();
        addFragmentToView(Constants.HOME_FRAGMENT);

    }

    void bindAllView() {
        mToolbar = findViewById(R.id.toolbar);
        mFab = findViewById(R.id.fab);

        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

    }

    void registerListener() {
        if (mFab != null)
            mFab.setOnClickListener(this);

        if (mNavigationView != null)
            mNavigationView.setNavigationItemSelectedListener(this);
    }

    void addFragmentToView(String fragmentName) {

        FragmentManager fm = getSupportFragmentManager();
        switch (fragmentName) {
            case Constants.HOME_FRAGMENT:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment(this);
                    myFragment.getFragment(fm, mHomeFragment, R.id.fragment_movie_data,Constants.HOME_FRAGMENT);
                }
                break;
            case Constants.TOP_RATED_FRAGMENT:
                if (mTopRatedFragment == null) {
                    mTopRatedFragment = new TopRatedFragment();
                    myFragment.getFragment(fm, mTopRatedFragment, R.id.fragment_movie_data, Constants.TOP_RATED_FRAGMENT);
                }
                break;

            default:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment(this);
                    myFragment.getFragment(fm, mHomeFragment, R.id.fragment_movie_data, Constants.HOME_FRAGMENT);
                }
                break;
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mFab.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_favorite_24dp));


        }
    }


    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.my_favorite) {
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onClick(String apiName) {
        switch (apiName) {
            case Constants.TOP_RATED_API:
                addFragmentToView(Constants.TOP_RATED_FRAGMENT);
                break;
            case Constants.UPCOMING_API:
                addFragmentToView(Constants.UPCOMING_FRAGMENT);
                break;
            case Constants.NOW_PLAYING_API:
                addFragmentToView(Constants.NOW_PLAYING_FRAGMENT);
                break;
            case Constants.POPULAR_API:
                addFragmentToView(Constants.POPULAR_FRAGMENT);
                break;
        }
    }
}
