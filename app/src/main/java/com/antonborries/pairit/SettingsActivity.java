package com.antonborries.pairit;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;

public class SettingsActivity extends AppCompatActivity {

    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            settingsFragment = new SettingsFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.container, settingsFragment).commit();
        }

        //CIRCULAR REVEAL
        final View rV = getWindow().getDecorView().findViewById(android.R.id.content);
        rV.setVisibility(View.INVISIBLE);
        ViewTreeObserver vto = rV.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onGlobalLayout() {
                // Put your code here.
                int cx = rV.getWidth() / 2;
                int cy = rV.getHeight() / 2;

                // get the final radius for the clipping circle
                int finalRadius = Math.max(rV.getWidth(), rV.getHeight());

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(rV, cx, cy, 0, finalRadius);

                // make the view visible and start the animation
                rV.setVisibility(View.VISIBLE);
                anim.start();
                rV.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
