package com.example.healthy.boom.healthy.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.healthy.boom.healthy.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class WeightAdapter extends ArrayAdapter<Weight> {

    private List<Weight> weights;
    private Context context;

    public WeightAdapter(@NonNull Context context,
                         int resource,
                         @NonNull List<Weight> objects) {
        super(context, resource, objects);
        this.context = context;
        this.weights = (ArrayList<Weight>) objects;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View weightItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_weight_item,
                parent,
                false);

        TextView dateText = (TextView) weightItem.findViewById(R.id.fragment_weight_item_date);
        TextView weightText = (TextView) weightItem.findViewById(R.id.fragment_weight_item_weight);
        TextView statusText = (TextView) weightItem.findViewById(R.id.fragment_weight_item_status);

        Weight weight = weights.get(position);

        dateText.setText(weight.getDate());
        weightText.setText(weight.getWeight());

        Log.d("ADAPTER", String.valueOf(weights.size()));
        if (position == weights.size() - 1) {
            statusText.setText("");
        } else {
            Double normal = Double.parseDouble(weights.get(position).getWeight());
            Double more = Double.parseDouble(weights.get(position + 1).getWeight());

            if (normal > more) {
                statusText.setText("UP");
            } else if (more > normal) {
                statusText.setText("DOWN");
            } else {
                statusText.setText("");
            }
        }
        return weightItem;
    }
}