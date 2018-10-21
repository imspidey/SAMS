package spidey.com.reyaj.chat.sams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import spidey.com.reyaj.chat.sams.database.DatabaseHandler;

public class StudActivity extends AppCompatActivity {

    Activity StudActivity = this;
    ListView listView;
    StudAdapter adapter;
    ArrayList<String> dates;
    ArrayList<String> datesALONE;
    ArrayList<Integer> hourALONE;
    ArrayList<Boolean> atts;
    private View v;
    public Cursor cursor;
    DatabaseHandler handler = AppBase.handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud);
        DatabaseHandler handler = new DatabaseHandler(this);

        dates = new ArrayList<>();
        datesALONE = new ArrayList<>();
        hourALONE = new ArrayList<>();
        atts = new ArrayList<>();

        listView = (ListView) findViewById(R.id.attendProfileView_list);

        TextView textView = (TextView) findViewById(R.id.profileContentView);

        Button findButton = (Button) findViewById(R.id.buttonFind);
        assert findButton != null;
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find(v);
            }
        });
    }

    public void find(View view) {
        dates.clear();
        atts.clear();
        EditText editText = (EditText) findViewById(R.id.editText);
        TextView textView = (TextView) findViewById(R.id.profileContentView);
        String reg = editText.getText().toString();
        String qu = "SELECT * FROM STUDENT WHERE regno = '" + reg.toUpperCase() + "'";
        String qc = "SELECT * FROM ATTENDANCE WHERE register = '" + reg.toUpperCase() + "';";
        String qd = "SELECT * FROM ATTENDANCE WHERE register = '" + reg.toUpperCase() + "' AND isPresent = 1";
        cursor = handler.execQuery(qu);
        //Start Count Here

        float att = 0f;
        Cursor cur = handler.execQuery(qc);
        Cursor cur1 = handler.execQuery(qd);
        if (cur == null) {
            Log.d("profile", "cur null");
        }
        if (cur1 == null) {
            Log.d("profile", "cur1 null");
        }
        if (cur != null && cur1 != null) {
            cur.moveToFirst();
            cur1.moveToFirst();
            try {
                att = ((float) cur1.getCount() / cur.getCount()) * 100;
                if (att <= 0)
                    att = 0f;
                Log.d("ProfileActivity", "Total = " + cur.getCount() + " avail = " + cur1.getCount() + " per " + att);
            } catch (Exception e) {
                att = -1;
            }
        }


        if (cursor == null || cursor.getCount() == 0) {
            assert textView != null;
            textView.setText("No Data Available");
        } else {
            String attendance = "";
            if (att < 0) {
                attendance = "Attendance Not Available";
            } else
                attendance = " Attendance " + att + " %";
            cursor.moveToFirst();
            String buffer = "";
            buffer += " " + cursor.getString(0) + "\n";
            buffer += " " + cursor.getString(1) + "\n";
            buffer += " " + cursor.getString(2) + "\n";
            buffer += " " + cursor.getString(3) + "\n";
            buffer += " " + cursor.getInt(4) + "\n";
            buffer += " " + attendance + "\n";
            textView.setText(buffer);

            String q = "SELECT * FROM ATTENDANCE WHERE register = '" + editText.getText().toString().toUpperCase() + "'";
            Cursor cursorx = handler.execQuery(q);
            if (cursorx == null || cursorx.getCount() == 0) {
                Toast.makeText(getBaseContext(), "No Attendance Info Available", Toast.LENGTH_LONG).show();
            } else {
                cursorx.moveToFirst();
                while (!cursorx.isAfterLast()) {
                    datesALONE.add(cursorx.getString(0));
                    hourALONE.add(cursorx.getInt(1));
                    dates.add(cursorx.getString(0) + ":" + cursorx.getInt(1) + "th Hour");
                    if (cursorx.getInt(3) == 1)
                        atts.add(true);
                    else {
                        Log.d("profile", cursorx.getString(0) + " -> " + cursorx.getInt(3));
                        atts.add(false);
                    }
                    cursorx.moveToNext();
                }
                adapter = new StudAdapter(dates, atts, StudActivity, datesALONE, hourALONE, editText.getText().toString().toUpperCase());
                listView.setAdapter(adapter);
            }
        }
    }
}
