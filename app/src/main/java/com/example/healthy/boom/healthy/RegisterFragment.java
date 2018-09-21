package com.example.healthy.boom.healthy;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mauth = FirebaseAuth.getInstance();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onClickRegister();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void onClickRegister() {
        Button btn = getView().findViewById(R.id.register_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) getView().findViewById(R.id.register_email)).getText().toString();
                String password = ((EditText) getView().findViewById(R.id.register_password)).getText().toString();
                String rePassword = ((EditText) getView().findViewById(R.id.register_repassword)).getText().toString();

                if (email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Log.d("REGISTER", "Some attribute was missing");
                    Toast.makeText(getActivity(), "Some attribute was missing", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Log.d("REGISTER", "Password less than 6");
                    Toast.makeText(getActivity(), "Password less than 6", Toast.LENGTH_SHORT).show();
                } else if (password.equals(rePassword)) {

                    mauth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Log.d("REGISTER", "Register is successful");
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = authResult.getUser();

                            sendMail(user);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("REGISTER", e.getMessage());
                            Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d("REGISTER", "Some error was found");
                    Toast.makeText(getActivity(), "Some error was found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendMail(FirebaseUser user) {
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mauth.signOut();
                Log.d("LOGIN", "Send verify email successful");
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new LoginFragment())
                        .commit()
                ;

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("LOGIN", "Send vefiry email failure");
                Toast.makeText(getActivity(), "Send Verify error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}