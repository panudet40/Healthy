package com.example.healthy.boom.healthy.Sleep;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.healthy.boom.healthy.R;

import java.util.ArrayList;
import java.util.PriorityQueue;

import static android.content.Context.MODE_PRIVATE;

public class SleepHistoryFragment extends Fragment {

    private SQLiteDatabase myDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAddBtn();
        getDBSQLlite();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = getActivity().openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        String sqlStatement =
                "CREATE TABLE IF NOT EXISTS sleep (_id INTEGER PRIMARY KEY AUTOINCREMENT, sleep_date VARCHAR(12), bedtime_period VARCHAR(14), awaketime VARCHAR(6))";
        myDB.execSQL(sqlStatement);
    }

    private void getDBSQLlite() {
        Cursor cursor = myDB.rawQuery("select _id, sleep_date, bedtime_period, awaketime from sleep", null);
        final ArrayList<Sleep> sleepList = new ArrayList<>();

        while(cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String date = cursor.getString(1);
            String toBedTime = cursor.getString(2);
            String awakeTime = cursor.getString(3);

            Sleep sleep = new Sleep();

            sleep.setId(id);
            sleep.setSleep_date(date);
            sleep.setBedtime_period(toBedTime);
            sleep.setAwaktime(awakeTime);

            sleepList.add(sleep);
        }
        cursor.close();

        ListView sleepListView = getView().findViewById(R.id.fragment_sleep_history_list);
        SleepAdapter sleepAdapter = new SleepAdapter(getActivity(), R.layout.fragment_sleep_item, sleepList);
        sleepListView.setAdapter(sleepAdapter);
        sleepListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("sleep object", sleepList.get(position));
                Fragment addSleepFragment = new SleepFormFragment();
                addSleepFragment.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.main_view, addSleepFragment).addToBackStack(null).commit();
            }
        });
    }

    private void initAddBtn() {
        Button addBtn = getView().findViewById(R.id.fragment_sleep_history_add_sleep_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFormFragment())
                        .addToBackStack(null)
                        .commit()
                ;
            }
        });
    }
}
