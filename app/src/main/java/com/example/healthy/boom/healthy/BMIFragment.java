package com.example.healthy.boom.healthy;

import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BMIFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        calculate();
    }

    private void calculate() {
        Button btn = getView().findViewById(R.id.bmi_calculate);

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String _height = ((EditText) getView().findViewById(R.id.bmi_height)).getText().toString();
                String _weight = ((EditText) getView().findViewById(R.id.bmi_weight)).getText().toString();

                DecimalFormat df2 = new DecimalFormat(".##");

                double _intHeight = Double.parseDouble(_height);
                double _intWeight = Double.parseDouble(_weight);

                double _spuareHeight = _intHeight / 100;
                double _answer = _intWeight / (_spuareHeight * _spuareHeight);

                ((TextView) getView().findViewById(R.id.bmi_answer)).setText(df2.format(_answer).toString());


            }
        });
    }
}
