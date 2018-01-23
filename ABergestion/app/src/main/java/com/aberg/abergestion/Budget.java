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
    private ArrayList<donneesBudget> datas;
    private ArrayList<String> intituleData;

    public Budget(ArrayList<donneesBudget> datas){
        this.datas = datas;
        this.intituleData = new ArrayList<String>();
        for(int i = 0; i < datas.size(); i++ ){
            intituleData.add(datas.get(i).getIntitule());
        }
    }

    public ArrayList<donneesBudget> getDatas(){
        return datas;
    }

    public ArrayList<String> getIntituleData(){
        return intituleData;
    }
}
