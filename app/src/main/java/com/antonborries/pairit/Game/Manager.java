package com.antonborries.pairit.Game;

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


    public Manager(String type) {
        this(type, 27);
    }

    public Manager(String type, int length) {
        numbers = new ArrayList<>();
        history = new ArrayList<>();
        switch (type) {
            case "random":
                Random rnd = new Random();
                for (int i = 0; i < length; i++) {
                    int nmb = rnd.nextInt(5) + 1;
                    numbers.add(nmb);
                }
                break;

        }
    }

    /**
     * Adds the Numbers to the List
     */
    public void addNumbers() {
        int end = numbers.size();
        for (int i = 0; i < end; i++) {
            numbers.add(numbers.get(i));
        }
    }

    /**
     * Checks for possible Matches
     *
     * @param index Position to search at
     * @return array of 4 ints with positions of partners in NWSE direction
     */
    public int[] checkPartners(int index) {
        partners = new int[4];

        int value = numbers.get(index);

        //TOP Partner
        if (index - 9 >= 0 && numbers.get(index - 9) == value)
            partners[0] = index - 9;
        else
            partners[0] = -1;

        //RIGHT Partner
        if (index + 1 < numbers.size() && numbers.get(index + 1) == value)
            partners[0] = index + 1;
        else
            partners[0] = -1;

        //BOTTOM Partner
        if (index + 9 < numbers.size() && numbers.get(index + 9) == value)
            partners[0] = index + 9;
        else
            partners[0] = -1;

        //LEFT Partner
        if (index - 1 >= 0 && numbers.get(index - 1) == value)
            partners[0] = index - 1;
        else
            partners[0] = -1;


        return partners;
    }

    public void deletePair(int pos1, int pos2) {

        //Switch so pos1 < pos2
        if(pos1 > pos2){
            int parkingSpot = pos2;
            pos2 = pos1;
            pos1 = parkingSpot;
        }


        //Adds Position and Value to History List
        history.add(numbers.get(pos2));
        history.add(pos2);
        history.add(numbers.get(pos1));
        history.add(pos1);

        //Remove Values
        numbers.remove(pos1);
        numbers.remove(pos2);
    }

    public void revert(){
        numbers.add(history.remove(history.size()-1),history.remove(history.size()-1));
        numbers.add(history.remove(history.size()-1),history.remove(history.size()-1));
    }

}
