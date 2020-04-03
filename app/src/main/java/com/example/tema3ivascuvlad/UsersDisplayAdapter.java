package com.example.tema3ivascuvlad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import Models.User;

public class UsersDisplayAdapter extends RecyclerView.Adapter<UsersDisplayAdapter.ViewHolder> {
    private List<User> mDataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RelativeLayout parentLayout;

        public ViewHolder(View v) {
            super(v);

            textView = v.findViewById(R.id.userTextView);
            parentLayout = v.findViewById(R.id.parent_layout);

        }
    }

    public UsersDisplayAdapter(Context pContext, List<User> myDataset) {
        System.out.println("LENGTH:" + myDataset.size());
        context = pContext;
        mDataset = myDataset;
    }

    @Override
    public UsersDisplayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_user, parent, false);

        final ViewHolder holder = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    User user = mDataset.get(position);
                    System.out.println(user.name);
                    startToDosActivity(user.id);
                }
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.textView.setText(mDataset.get(position).username + " " +  mDataset.get(position).email);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void startToDosActivity(Integer id){
        Intent i = new Intent(context, ToDosActivity.class);
        i.putExtra("id", id);
        context.startActivity(i);
    }


}
