package com.example.androidChallenge;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by DELL-PC on 15-10-2016.
 */

public class Details implements Comparable<Details> {

    public String name;
    public String cost_in_credits;
    public int type;

    public String filmText;

    public List<String> films;

    public Details(int type) {
        this.type = type;
    }

    @Override
    public int compareTo(@NonNull Details details) {
        return (Long.parseLong(details.cost_in_credits) < Long.parseLong(cost_in_credits)) ? -1 : 1;
    }

    public class Films {
        public String title;
    }

}
