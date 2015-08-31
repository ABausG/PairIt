package com.antonborries.pairit;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Anton on 30/08/2015.
 */
public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private FloatingActionButton fab;
    public TileClick mClickListener;

    public RecyclerHolder(View itemView, TileClick clickListener) {
        super(itemView);

        fab = (FloatingActionButton) itemView.findViewById(R.id.fab);
        this.mClickListener = clickListener;
        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mClickListener.onTileClick(this.getAdapterPosition());
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public static interface TileClick{
        public void onTileClick(int position);
    }
}
