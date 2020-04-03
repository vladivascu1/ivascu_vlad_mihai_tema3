package com.example.tema3ivascuvlad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import Models.ToDo;

public class ToDosDisplayAdapter extends RecyclerView.Adapter<ToDosDisplayAdapter.ViewHolder> {
    private List<ToDo> mDataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        RelativeLayout parentLayout;

        public ViewHolder(View v) {
            super(v);

            textView = v.findViewById(R.id.toDoTextView);
            parentLayout = v.findViewById(R.id.parent_layout);

        }
    }

    public ToDosDisplayAdapter(Context pContext, List<ToDo> myDataset) {
        System.out.println("LENGTH:" + myDataset.size());
        context = pContext;
        mDataset = myDataset;
    }

    @Override
    public ToDosDisplayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.to_do, parent, false);

        final ViewHolder holder = new ViewHolder(v);

        v.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ToDo toDo = mDataset.get(position);
                    ToDosActivity toDosActivity = (ToDosActivity)context;
                    toDosActivity.setCurrentTitle(toDo.title);
                    toDosActivity.showFragment();
                    System.out.println(toDo.title);
                }
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(mDataset.get(position).title + " " +  mDataset.get(position).completed);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
