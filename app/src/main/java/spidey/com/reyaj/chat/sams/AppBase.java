package spidey.com.reyaj.chat.sams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;

import spidey.com.reyaj.chat.sams.components.About;
import spidey.com.reyaj.chat.sams.components.SettingsActivity;
import spidey.com.reyaj.chat.sams.components.GridAdapter;
import spidey.com.reyaj.chat.sams.database.DatabaseHandler;

public class AppBase extends AppCompatActivity {

    public static ArrayList<String> divisions;
    public static DatabaseHandler handler;
    public static Activity activity;
    ArrayList<String> basicFields;
    GridAdapter adapter;
    GridView gridView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mai_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        basicFields = new ArrayList<>();
        handler = new DatabaseHandler(this);
        activity = this;

        getSupportActionBar().show();
        divisions = new ArrayList<>();
        divisions.add("BE");
        divisions.add("TE");
        divisions.add("SE");
        divisions.add("FE");
        gridView = (GridView) findViewById(R.id.grid);
        basicFields.add("ATTENDANCE");
        basicFields.add("PROFILE");
        adapter = new GridAdapter(this, basicFields);
        gridView.setAdapter(adapter);
    }

    public void loadSettings(MenuItem item) {
        startActivity(new Intent(AppBase.this,SettingsActivity.class));
    }

    public void loadAbout(MenuItem item) {
        startActivity(new Intent(AppBase.this,About.class));
    }

}
