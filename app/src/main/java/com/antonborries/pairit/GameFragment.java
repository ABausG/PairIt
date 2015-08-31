package com.antonborries.pairit;


import android.animation.Animator;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private RecyclerView recView;
    private View rootView;

    public GameFragment() {
        // Required empty public constructor
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        Constants.tileSize = size.x / 9;

        recView = (RecyclerView) rootView.findViewById(R.id.recyclerTiles);
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 9);
        recView.setLayoutManager(glm);
        recView.setItemAnimator(new DefaultItemAnimator());


        recView.setAdapter(new RecyclerAdapter("random"));



        return rootView;
    }


    public View getRootView() {
        return rootView;
    }

    public void addNumbers() {
        ((RecyclerAdapter)recView.getAdapter()).addNumbers();
    }

    public void undo() {
        ((RecyclerAdapter)recView.getAdapter()).undo();
    }
}
