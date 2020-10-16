package com.example.streetspirit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegFragment extends Fragment {

    private FirebaseAuth mAuth;


    private Button regbtn;
    private TextView log,pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    public void registration (String email , String password){
        final Context context = getContext();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                {new CountDownTimer(1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        MainFragment fragment = new MainFragment();

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentContainer, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                }.start();

                }
                }
                else
                    Toast.makeText(context, "Регистрация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflatedView = inflater.inflate(R.layout.fragment_reg, container, false);

        log = (TextView)inflatedView.findViewById(R.id.Log);
        pass = (TextView)inflatedView.findViewById(R.id.Pass);




        regbtn = (Button)inflatedView.findViewById(R.id.buttonReg);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String logg, pas;

                logg = log.getText().toString();
                pas = pass.getText().toString();

                if(!TextUtils.isEmpty(logg) && !TextUtils.isEmpty(pas))
                {
                    registration(log.getText().toString(),pass.getText().toString());
                }
                else
                {
                    Toast.makeText(getContext(), "Поле не должно быть пустым", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return inflatedView;
    }
}