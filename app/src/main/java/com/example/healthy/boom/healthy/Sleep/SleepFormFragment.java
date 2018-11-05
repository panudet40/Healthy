package com.example.healthy.boom.healthy.Sleep;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthy.boom.healthy.MenuFragment;
import com.example.healthy.boom.healthy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class SleepFormFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private SQLiteDatabase myDB;
    private ContentValues row;
    private String status;
    private Sleep sleep;

    public SleepFormFragment() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = getActivity().openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        String sqlStatement =
                "CREATE TABLE IF NOT EXISTS sleep (_id INTEGER PRIMARY KEY AUTOINCREMENT, sleep_date VARCHAR(12), bedtime_period VARCHAR(14), awaketime VARCHAR(6))";
        myDB.execSQL(sqlStatement);

        Bundle bundle = getArguments();

        try
        {
            sleep = (Sleep) bundle.getSerializable("sleep object");
            status = "edit";
        }
        catch (NullPointerException e)
        {
            if (sleep == null)
            {
                status = "new";
            }
            else
            {
                Log.d("test", "null pointer : " + e.getMessage());
            }
        }
        Log.d("test", "status : " + status);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (status.equals("edit"))
        {
            setValue();
        }
        initBackBtn();
        initSaveBtn();


    }

    private void setValue() {

        EditText sleepDate = getView().findViewById(R.id.fragment_sleep_form_date);
        EditText bedPeriod_1 = getView().findViewById(R.id.fragment_sleep_form_bed_period_first);
        EditText bedPeriod_2 = getView().findViewById(R.id.fragment_sleep_form_bed_period_second);
        EditText awakeTime = getView().findViewById(R.id.fragment_sleep_form_awake_time);

        //Split String
        String[] bedPeriodStr = sleep.getBedtime_period().split(" - ");

        //Change Date format


        sleepDate.setText(changeDateFormat(sleep.getSleep_date(), true));
        bedPeriod_1.setText(bedPeriodStr[0]);
        bedPeriod_2.setText(bedPeriodStr[1]);
        awakeTime.setText(sleep.getAwaktime());

        Button addButton = getView().findViewById(R.id.fragment_sleep_save_btn);
        addButton.setText("EDIT");
    }

    private void initSaveBtn() {
        Button btn = getView().findViewById(R.id.fragment_sleep_save_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText sleepDate = getView().findViewById(R.id.fragment_sleep_form_date);
                EditText bedPeriod_1 = getView().findViewById(R.id.fragment_sleep_form_bed_period_first);
                EditText bedPeriod_2 = getView().findViewById(R.id.fragment_sleep_form_bed_period_second);
                EditText awakeTime = getView().findViewById(R.id.fragment_sleep_form_awake_time);

                String sleepDateStr = sleepDate.getText().toString();
                String bedPeriodStr = bedPeriod_1.getText().toString() + " - " + bedPeriod_2.getText().toString();
                String awakeTimeStr = awakeTime.getText().toString();

                // Check empty String
                if (sleepDateStr.isEmpty() || bedPeriodStr.isEmpty() || awakeTimeStr.isEmpty()) {
                    Log.d("SLEEPFORM", "Some fields is empty");
                    Toast.makeText(getActivity(), "Some fields is empty", Toast.LENGTH_SHORT).show();
                } else {
                    row = new ContentValues();
                    row.put("sleep_date", changeDateFormat(sleepDateStr, false));
                    row.put("bedtime_period", bedPeriodStr);
                    row.put("awaketime", awakeTimeStr);
                    try {
                        if (status.equals("new"))
                        {
                            myDB.insert("sleep", null, row);
                        }
                        else if (status.equals("edit"))
                        {
                            myDB.update("sleep", row, "_id="+sleep.getId(), null);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    row.clear();
                }
                //Go to SleepHistoryFragment
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepHistoryFragment())
                        .addToBackStack(null)
                        .commit()
                ;
            }
        });
    }

    private void initBackBtn() {
        Button btn = getView().findViewById(R.id.fragment_sleep_back_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit()
                ;
            }
        });
    }

    public void clearText() {
        EditText sleepDate = getView().findViewById(R.id.fragment_sleep_form_date);
        EditText bedPeriod_1 = getView().findViewById(R.id.fragment_sleep_form_bed_period_first);
        EditText bedPeriod_2 = getView().findViewById(R.id.fragment_sleep_form_bed_period_second);
        EditText awakeTime = getView().findViewById(R.id.fragment_sleep_form_awake_time);

        sleepDate.getText().clear();
        bedPeriod_1.getText().clear();
        bedPeriod_2.getText().clear();
        awakeTime.getText().clear();

    }

    public String changeDateFormat(String oldDateString, Boolean reversed) {
        final String OLD_FORMAT = "dd/MM/yyyy";
        final String NEW_FORMAT = "dd MMMM yyyy";
        if (!reversed) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(OLD_FORMAT);
            Date sourceDate = null;
            String newDateString;
            newDateString = "1-Jan-1997";
            try {
                sourceDate = dateFormat.parse(oldDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat targetFormat = new SimpleDateFormat(NEW_FORMAT);
            newDateString = targetFormat.format(sourceDate);

            return newDateString;
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(NEW_FORMAT);
            Date sourceDate = null;
            String newDateString;
            newDateString = "01/01/1997";
            try {
                sourceDate = dateFormat.parse(oldDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat targetFormat = new SimpleDateFormat(OLD_FORMAT);
            newDateString = targetFormat.format(sourceDate);

            return newDateString;
        }
    }
}
