package spidey.com.reyaj.chat.sams.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import spidey.com.reyaj.chat.sams.AppBase;
import spidey.com.reyaj.chat.sams.R;
import spidey.com.reyaj.chat.sams.attendance.AttendanceActivity;
import spidey.com.reyaj.chat.sams.profile.ProfileActivity;

public class GridAdapter extends BaseAdapter {
    public static Activity activity;
    ArrayList names;

    public GridAdapter(Activity activity, ArrayList names) {
        this.activity = activity;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.grid_layout, null);
        }
        TextView textView = (TextView) v.findViewById(R.id.namePlacer);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageHolder);
        if (names.get(position).toString().equals("ATTENDANCE")) {
            imageView.setImageResource(R.drawable.ic_attendance);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = activity.getFragmentManager();
                    createRequest request = new createRequest();
                    request.show(fm, "Select");
                }
            });


        } else if (names.get(position).toString().equals("PROFILE")) {
            imageView.setImageResource(R.drawable.ic_profile);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent launchinIntent = new Intent(activity, ProfileActivity.class);
                    activity.startActivity(launchinIntent);
                }
            });

        }


        textView.setText(names.get(position).toString());
        return v;
    }

    public static class createRequest extends DialogFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View v = inflater.inflate(R.layout.pick_period, null);
            final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
            final EditText hour = (EditText) v.findViewById(R.id.periodID);

            builder.setView(v)
                    // Add action buttons
                    .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            int day = datePicker.getDayOfMonth();
                            int month = datePicker.getMonth() + 1;
                            int year = datePicker.getYear();
                            String date = year + "-" + month + "-" + day;

                            Cursor cursor = AppBase.handler.execQuery("SELECT * FROM ATTENDANCE WHERE datex = '" +
                                    date + "' AND hour = " + hour.getText() + ";");
                            if (cursor == null || cursor.getCount() == 0) {
                                Intent launchinIntent = new Intent(AppBase.activity, AttendanceActivity.class);
                                launchinIntent.putExtra("DATE", date);
                                launchinIntent.putExtra("PERIOD", hour.getText().toString());
                                AppBase.activity.startActivity(launchinIntent);
                            } else {
                                Toast.makeText(getActivity(), "Period Already Added", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }
    }
}
