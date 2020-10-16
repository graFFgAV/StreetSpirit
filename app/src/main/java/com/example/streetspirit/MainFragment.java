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



public class MainFragment extends Fragment {

    String logg, pas;
    private FirebaseAuth mAuth;
    private Button regbtn;
    private Button LogIn;
    private TextView log,pass;


    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    public void signin(final String email , final String password)
    {
        final Context context = getContext();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Авторизация успешна", Toast.LENGTH_SHORT).show();
                    {new CountDownTimer(1000, 1000) {
                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish() {
                            Bundle bundle = new Bundle();
                            bundle.putString("type",log.getText().toString());

                            ChatFragment fragment = new ChatFragment();
                            fragment.setArguments(bundle);

                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragmentContainer, fragment);
                            //transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }.start();
                    }
                }else
                    Toast.makeText(context, "Aвторизация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflatedView = inflater.inflate(R.layout.fragment_main, container, false);

        log = (TextView)inflatedView.findViewById(R.id.Log);
        pass = (TextView)inflatedView.findViewById(R.id.Pass);

        LogIn = (Button)inflatedView.findViewById(R.id.buttonIn);
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logg = log.getText().toString();
                pas = pass.getText().toString();
                if(!(TextUtils.isEmpty(logg) || TextUtils.isEmpty(pas)))
                {
                    signin(log.getText().toString(),pass.getText().toString());
                }
                else
                {
                    Toast.makeText(getContext(), "Поле не должно быть пустым", Toast.LENGTH_SHORT).show();
                }
            }
        });
        regbtn = (Button) inflatedView.findViewById(R.id.buttonReg);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegFragment fragment = new RegFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return inflatedView;
    }
}