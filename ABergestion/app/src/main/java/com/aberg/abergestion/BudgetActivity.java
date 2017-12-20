package com.aberg.abergestion;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by louis on 20/12/2017.
 */

public class BudgetActivity extends AppCompatActivity {

    private TextView budget_restant,textView_budget_restant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityBudget();

        //user = new User((EditText)findViewById(R.id.editText_nom),(EditText)findViewById(R.id.editText_prenom),(EditText)findViewById(R.id.passwordNum_motdepasse));
    }

    private void activityBudget() {
        setContentView(R.layout.activity_budget);

        budget_restant= findViewById(R.id.budget_restant);
        textView_budget_restant = findViewById(R.id.textView_budget_restant);

        final String[] columns = new String[] {"_id","col1","col2"};

        MatrixCursor matrixCursor = new MatrixCursor(columns);
        startManagingCursor(matrixCursor);
        matrixCursor.addRow(new Object[]{1, "col1:ligne1", "col2:ligne1"});
        matrixCursor.addRow(new Object[]{2, "col1:ligne2", "col2:ligne2"});

        String[] from = new String[] {"col1","col2"};

        int[] to = new int[] {R.id.textViewCol1,R.id.textViewCol2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.row_item, matrixCursor,from,to,0);

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);
    }

}
