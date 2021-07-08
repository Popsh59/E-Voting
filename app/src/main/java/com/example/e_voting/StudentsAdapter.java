package com.example.e_voting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.backendless.BackendlessUser;

import java.util.List;


public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder>
{
    private List<BackendlessUser> users;

    BackendlessUser user;

    ItemClicked activity;

    Context context;

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }

    public StudentsAdapter (Context context, List<BackendlessUser> studentsList)
    {
        users = studentsList;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder
    {
        TextView tvChar, tvName, tvSurname, tvStudNo,tvEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvChar = itemView.findViewById(R.id.tvChar);
            tvName = itemView.findViewById(R.id.tvName);
            tvSurname = itemView.findViewById(R.id.tvSurname);
            tvStudNo = itemView.findViewById(R.id.tvStudNo);
            tvEmail = itemView.findViewById(R.id.tvEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                  activity.onItemClicked(users.indexOf((BackendlessUser) view.getTag()));

                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(users.get(position));


        holder.tvName.setText("" +users.get(position).getProperty("name"));
        holder.tvSurname.setText("" +users.get(position).getProperty("surname"));
        holder.tvStudNo.setText("" +users.get(position).getProperty("studentNo"));
        holder.tvEmail.setText("" +users.get(position).getEmail());
        holder.tvChar.setText("" +users.get(position).getProperty("name"));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
