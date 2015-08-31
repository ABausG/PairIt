package com.antonborries.pairit;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antonborries.pairit.Game.Manager;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter implements Parcelable {

    private String gameType = "random";
    private Manager manager;

    private int selectedTile;
    private int[] partners;


    public RecyclerAdapter(String gameType) {
        this.gameType = gameType;
        this.manager = new Manager(gameType);
    }

    public RecyclerAdapter(Parcel source) {
        gameType = source.readString();
        this.manager = new Manager("random");
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.tile, parent, false);
        RecyclerHolder holder = new RecyclerHolder(itemView, new RecyclerHolder.TileClick() {
            @Override
            public void onTileClick(int position) {

                if (position == selectedTile) {
                    //Clicked Tile is Selected
                    selectedTile = -1;
                    partners = null;
                } else if (partners != null && selectedTile != -1 && (partners[0] == position || partners[1] == position || partners[2] == position || partners[3] == position)) {
                    //Clicked Tile is Partner
                    manager.deletePair(position, selectedTile);
                    notifyItemRemoved(Math.max(position,selectedTile));
                    notifyItemRemoved(Math.min(position,selectedTile));
                    selectedTile = -1;
                    partners = null;
                } else {//Clicked Tile is empty
                    selectedTile = position;
                    partners = manager.checkPartners(position);
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            Resources res = GameActivity.getContext().getResources();

            int value = manager.getNumbers().get(position);
            switch (value){
                case 0:
                    ((RecyclerHolder) holder).getFab().setBackgroundTintList(ColorStateList.valueOf(res.getColor(R.color.zero)));
                    break;
                case 1:
                    ((RecyclerHolder) holder).getFab().setBackgroundTintList(ColorStateList.valueOf(res.getColor(R.color.one)));
                    break;
                case 2:
                    ((RecyclerHolder) holder).getFab().setBackgroundTintList(ColorStateList.valueOf(res.getColor(R.color.two)));
                    break;
                case 3:
                    ((RecyclerHolder) holder).getFab().setBackgroundTintList(ColorStateList.valueOf(res.getColor(R.color.three)));
                    break;
                case 4:
                    ((RecyclerHolder) holder).getFab().setBackgroundTintList(ColorStateList.valueOf(res.getColor(R.color.four)));
                    break;
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    // public void onViewAttachedToWindow(ViewHolder holder) { }

    public Manager getManager() {
        return manager;
    }


    public List<Integer> getNumbers() {
        return manager.getNumbers();
    }

    @Override
    public int getItemCount() {
        return manager.getNumbers().size();
    }


    //Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(getNumbers());
    }

    public static final Parcelable.Creator<RecyclerAdapter> CREATOR =
            new Parcelable.Creator<RecyclerAdapter>() {

                @Override
                public RecyclerAdapter createFromParcel(Parcel source) {
                    return new RecyclerAdapter(source);
                }

                @Override
                public RecyclerAdapter[] newArray(int size) {
                    return new RecyclerAdapter[size];
                }
            };

    public void addNumbers() {
        int start = manager.getNumbers().size();
        manager.addNumbers();
        notifyItemRangeInserted(start, start);
    }

    public void undo() {
        manager.undo(this);
    }

    public void addUndo(int loc1, int loc2) {
        notifyItemInserted(loc1);
        notifyItemInserted(loc2);
    }

    public void undoAdd(int start) {
        notifyItemRangeRemoved(start, start);
    }
}


