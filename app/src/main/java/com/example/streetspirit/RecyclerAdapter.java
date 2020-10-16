package com.example.streetspirit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerAdapter extends FirebaseRecyclerAdapter<Message, RecyclerAdapter.PastViewHolder> {

    public RecyclerAdapter(@NonNull FirebaseRecyclerOptions<Message> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, int i, @NonNull Message post) {
        holder.title.setText(post.getEmail());
        holder.description.setText(post.getText());
        holder.author.setText(post.getTime());
    }

    @NonNull
    @Override
    public PastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new PastViewHolder(view);
    }

    class PastViewHolder extends RecyclerView.ViewHolder{

        TextView title,description,author;

        public PastViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvUser);
            description = itemView.findViewById(R.id.tvMessage);
            author = itemView.findViewById(R.id.tvTime);
        }
    }
}
