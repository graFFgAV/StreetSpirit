package com.example.streetspirit;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;




import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class ChatFragment extends Fragment {

    private EditText edTime, edMessage, edEmail;
    private DatabaseReference mDataBase;
    private String USER_KEY = "User";
    private Button btn;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void onClickSave(View view)
    {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");

        Bundle bundle = this.getArguments();
        String myValue = (Objects.requireNonNull(bundle).getString("type"));

        String id = mDataBase.getKey();
        String time = df.format(Calendar.getInstance().getTime());
        String text = edMessage.getText().toString();
        String email = myValue;
        Message message = new Message(id, time, text, email);
        if(!TextUtils.isEmpty(text) )
        {
            mDataBase.push().setValue(message);
        }
        else
        {
            Toast.makeText(getActivity(), "Пустое поле", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflatedView = inflater.inflate(R.layout.fragment_chat, container, false);

        btn = inflatedView.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSave(view);
                edMessage.setText("");
                readRealTime();
            }
        });
        edTime = inflatedView.findViewById(R.id.tvTime);
        edMessage = inflatedView.findViewById(R.id.editText);
        edEmail = inflatedView.findViewById(R.id.tvUser);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);

        mDataBase = FirebaseDatabase.getInstance().getReference().child("Post");


        recyclerView = inflatedView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Post"), Message.class)
                        .build();

        adapter = new RecyclerAdapter(options);
        recyclerView.setAdapter(adapter);


        return inflatedView;
    }


    private void readRealTime() {
        mDataBase.child("Post")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String post = "email : "+dataSnapshot.child("email").getValue(String.class)+"\n"
                                +"text : "+dataSnapshot.child("text").getValue(String.class)+"\n"
                                +"time : "+dataSnapshot.child("time").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}