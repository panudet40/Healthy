package com.example.healthy.boom.healthy.Sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.healthy.boom.healthy.R;

import java.util.List;

public class SleepAdapter extends ArrayAdapter {

    List<Sleep> sleeps;
    Context context;

    public SleepAdapter(Context context, int resource, List<Sleep> objects)
    {
        super(context, resource, objects);
        this.sleeps = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View sleepItem = LayoutInflater.from(context).inflate(R.layout.fragment_sleep_item, parent, false);

        TextView date = sleepItem.findViewById(R.id.fragment_sleep_item_date);
        TextView time = sleepItem.findViewById(R.id.fragment_sleep_item_bed_period);
        TextView sleepTime = sleepItem.findViewById(R.id.fragment_sleep_item_awaketime);

        Sleep sleep = sleeps.get(position);
        date.setText(sleep.getSleep_date());
        time.setText(sleep.getBedtime_period());
        sleepTime.setText(sleep.getAwaktime());
        return sleepItem;
    }
}
