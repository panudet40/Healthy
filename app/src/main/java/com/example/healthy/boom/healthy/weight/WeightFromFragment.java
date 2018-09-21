package com.example.healthy.boom.healthy.weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import com.example.healthy.boom.healthy.date.DatePickerFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class WeightFromFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public WeightFromFragment() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onClickBack();
        onClickDate();
        onClickAdd();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_from, container, false);
    }

    public void onClickAdd() {
        Button btn = getView().findViewById(R.id.weight_from_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _uid = firebaseAuth.getCurrentUser().getUid();

                // EditText
                EditText _weight = getView().findViewById(R.id.weight_from_weight);
                EditText _date = getView().findViewById(R.id.weight_from_date);

                // String
                String _weightStringTemp = _weight.getText().toString();
                String _weightString = String.format("%.2f", Double.parseDouble(_weightStringTemp));
                String _dateString = _date.getText().toString();

                // Weight object
                Weight _data = new Weight(_dateString, _weightString);

                // Check empty String
                if (_weightString.isEmpty() || _dateString.isEmpty()) {
                    Log.d("WEIGHTFROM", "weight or date is empty");
                    Toast.makeText(getActivity(), "Weight or Date is empty", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseFirestore.collection("myfitness")
                            .document(_uid)
                            .collection("weight")
                            .document(_dateString)
                            .set(_data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("WEIGHTFROM", "Data Saved!!");
                                    Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                                    clearText();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("WEIGHTFROM", "Data save failure :(");
                            Toast.makeText(getActivity(), "Save data failure", Toast.LENGTH_SHORT).show();
                            clearText();
                        }
                    });
                }
            }
        });
    }

    public void onClickDate() {
        EditText date = getView().findViewById(R.id.weight_from_date);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "Data Picker");
            }
        });
    }

    public void onClickBack() {
        Button btn = getView().findViewById(R.id.weight_from_back);

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
        EditText data = getView().findViewById(R.id.weight_from_date);
        EditText weight = getView().findViewById(R.id.weight_from_weight);

        data.getText().clear();
        weight.getText().clear();
    }
}
