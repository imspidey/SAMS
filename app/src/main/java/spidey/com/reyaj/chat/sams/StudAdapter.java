package spidey.com.reyaj.chat.sams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import spidey.com.reyaj.chat.sams.R;
import spidey.com.reyaj.chat.sams.AppBase;

public class StudAdapter extends BaseAdapter {
    ArrayList<String> dates;
    ArrayList<Boolean> present;
    Activity activity;
    String uname;
    ArrayList<String> datesALONE;
    ArrayList<Integer> hourALONE;
    public StudAdapter(ArrayList<String> dates, ArrayList<Boolean> present,
                          Activity activity, ArrayList<String> datesALONE, ArrayList<Integer> hourALONE, String re) {
        this.dates = dates;
        this.present = present;
        this.activity = activity;
        this.datesALONE = datesALONE;
        this.hourALONE = hourALONE;
        uname = re;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.list_ele, null);
        }
        final int pos = position;
        TextView textView = (TextView) v.findViewById(R.id.attendanceName);
        textView.setText(dates.get(position));
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.attMarker);
        checkBox.setClickable(false);
        Log.d("Profile", present.get(position) + "");
        checkBox.setChecked(present.get(position));

        return v;
    }
}
