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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        checkIsLogin();
        onClickLogin();
        onClickRegister();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    private void onClickLogin() {
        Button btn = getView().findViewById(R.id.login_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textEmail = getView().findViewById(R.id.login_email);
                EditText textPassword = getView().findViewById(R.id.login_password);

                String email = textEmail.getText().toString();
                String password = textPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Log.d("LOGIN", "username or password not exists");
                    Toast.makeText(getActivity(), "username or password not exists", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = authResult.getUser();

                            if (user.isEmailVerified()) {
                                Log.d("LOGIN", "Login passed");
                                Toast.makeText(getActivity(), "Login Complete email was verified", Toast.LENGTH_SHORT).show();

                                getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.main_view, new MenuFragment())
                                        .commit()
                                ;
                            } else {
                                Toast.makeText(getActivity(), "Please verify email please", Toast.LENGTH_SHORT).show();
                                Log.d("LOGIN", "Email not verify yet");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Email or Password invalid", Toast.LENGTH_SHORT).show();
                            Log.d("LOGIN", "Email or Password invalid");
                        }
                    });
                }

            }
        });
    }

    private void onClickRegister() {
        TextView textView = getView().findViewById(R.id.login_register);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit()
                ;
            }
        });
    }

    public void checkIsLogin() {
        if (auth.getUid() != null) {
            Log.d("LOGIN", "User was Login yet");
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new MenuFragment())
                    .commit()
            ;
        }

        Log.d("LOGIN", "User is not Login or user Signout");
    }

}