package com.aberg.abergestion;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mael on 18/12/2017.
 */

public class Budget implements Serializable {
    private ArrayList<donneesBudget> datas;
    private ArrayList<String> montantData;

    public Budget(ArrayList<donneesBudget> datas){
        this.datas = datas;
        this.montantData = new ArrayList<String>();
        if(datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                if(datas.get(i).getMontant() < 0)
                    montantData.add(String.valueOf(datas.get(i).getMontant()));
                else montantData.add("+ "+String.valueOf(datas.get(i).getMontant()));
            }
        }
    }

    public ArrayList<donneesBudget> getDatas(){
        return datas;
    }

    public ArrayList<String> getMontantData(){
        return montantData;
    }
}
