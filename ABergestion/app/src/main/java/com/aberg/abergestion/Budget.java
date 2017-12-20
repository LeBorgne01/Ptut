package com.aberg.abergestion;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mael on 18/12/2017.
 */

public class Budget {
    private ArrayList<Intitule> budget;

    public Budget() {
        this.budget = new ArrayList<>();
    }

    public void addIntitule(Intitule i) {
        this.budget.add(i);
    }
}
