package com.antonborries.pairit;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;

import com.antonborries.pairit.Game.Manager;

public class GameActivity extends AppCompatActivity {

    private GameFragment gameFragment;
    private String gameType;
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        context = this;
        gameType = getIntent().getExtras().getString("TYPE");

        if (savedInstanceState == null) {
            gameFragment = new GameFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TYPE", gameType);
            gameFragment.setArguments(bundle);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.container, gameFragment).commit();
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
        getMenuInflater().inflate(R.menu.menu_game, menu);
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


    public void addTiles(View view){
        gameFragment.addNumbers();
    }

    public void undo(View view){
        gameFragment.undo();
    }



}
