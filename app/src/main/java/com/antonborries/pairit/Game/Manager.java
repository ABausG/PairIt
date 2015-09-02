package com.antonborries.pairit.Game;

import android.content.SharedPreferences;
import android.util.Log;

import com.antonborries.pairit.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by antonborries on 29.08.15.
 */
public class Manager {

    private List<Integer> numbers;

    private List<Integer> history;

    private int[] partners;

    private Statistics stats;

    private int rows;
    private int difficulty;

    private int[] checkColors;


    public Manager(int rows, int difficulty) {
        this.rows = rows;
        this.difficulty = difficulty;
        checkColors = new int[difficulty];
        numbers = new ArrayList<>();
        history = new ArrayList<>();
        fillWithNumbers(rows * 9, true);
        stats = new Statistics(rows*9, difficulty);
    }

    private void fillWithNumbers(int amount, boolean firstInit) {
        Random rand = new Random();
        boolean foundNumber = false;
        for (int i = 0; i < amount; i++) {
            do {
                int nmb = rand.nextInt(difficulty);
                if (checkColors[nmb] > 0 || firstInit) {
                    getNumbers().add(nmb);
                    checkColors[nmb]++;
                    foundNumber = true;
                }
            } while (!foundNumber);
            foundNumber = false;
        }
    }

    private void fillWithNumbers(int amount){
        fillWithNumbers(amount, false);
    }

    /**
     * Adds the Numbers to the List
     */
    public void addNumbers() {
        stats.addAdds(  );
        int end = getNumbers().size();
        fillWithNumbers(end);

        //Add BreakPoint and Identifier to History
        history.add(end);
        history.add(-1);
    }

    /**
     * Checks for possible Matches
     *
     * @param index Position to search at
     * @return array of 4 ints with positions of partners in NWSE direction
     */
    public int[] checkPartners(int index) {
        partners = new int[4];

        int value = getNumbers().get(index);

        //TOP Partner
        if (index - 9 >= 0 && getNumbers().get(index - 9) == value)
            partners[0] = index - 9;
        else
            partners[0] = -1;

        //RIGHT Partner
        if (index + 1 < getNumbers().size() && getNumbers().get(index + 1) == value)
            partners[01] = index + 1;
        else
            partners[1] = -1;

        //BOTTOM Partner
        if (index + 9 < getNumbers().size() && getNumbers().get(index + 9) == value)
            partners[2] = index + 9;
        else
            partners[2] = -1;

        //LEFT Partner
        if (index - 1 >= 0 && getNumbers().get(index - 1) == value)
            partners[3] = index - 1;
        else
            partners[3] = -1;


        return partners;
    }

    public void deletePair(int pos1, int pos2) {
        stats.addDeletedPairs();
        checkColors[numbers.get(pos1)] -= 2;

        //Switch so pos1 < pos2
        if (pos1 > pos2) {
            int parkingSpot = pos2;
            pos2 = pos1;
            pos1 = parkingSpot;
        }


        //Adds Position and Value to History List
        history.add(getNumbers().get(pos2));
        history.add(pos2);
        history.add(getNumbers().get(pos1));
        history.add(pos1);

        //Remove Values
        getNumbers().remove(pos2);
        getNumbers().remove(pos1);

        if(numbers.size() == 0){
            Log.v("WINMSG", Double.toString(stats.getScore()));
        }
    }

    public void undo(RecyclerAdapter recAd) {
        if (history.size() >= 2) {
            stats.addUndos();
            if (history.size() >= 4 && history.get(history.size() - 1) != -1) {
                int loc1 = history.remove(history.size() - 1);
                int val1 = history.remove(history.size() - 1);
                int loc2 = history.remove(history.size() - 1);
                int val2 = history.remove(history.size() - 1);
                getNumbers().add(loc1, val1);
                getNumbers().add(loc2, val2);

                checkColors[val1] += 2;

                recAd.addUndo(loc1, loc2);
            } else {
                //Remove -1
                history.remove(history.size() - 1);
                //Get Start Location
                int start = history.remove(history.size() - 1);
                for (int i = numbers.size() - 1; i >= start; i--) {
                    checkColors[numbers.get(i)]--;
                    numbers.remove(i);
                }
                recAd.undoAdd(start);
            }
        }
    }

    public List<Integer> getNumbers() {
        return numbers;
    }


    public void addTime(long time) {
        stats.addTime(time);
    }
}
