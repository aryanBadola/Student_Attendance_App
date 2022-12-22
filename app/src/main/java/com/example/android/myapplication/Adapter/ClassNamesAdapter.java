package com.example.android.myapplication.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myapplication.Model.ClassNamesModel;
import com.example.android.myapplication.R;

import java.util.ArrayList;

public class ClassNamesAdapter extends RecyclerView.Adapter<ClassNamesAdapter.ViewHolder> {

    Context context;
    ArrayList<ClassNamesModel> arrClassNames;
    public ClassNamesAdapter(Context context, ArrayList<ClassNamesModel> arrClassNames)
    {
        this.context=context;
        this.arrClassNames=arrClassNames;
    }

    @NonNull
    @Override
    public ClassNamesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_names_adapter,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassNamesAdapter.ViewHolder holder, int position) {

        holder.backgroundImageView.setImageResource(arrClassNames.get(position).image);
        holder.subjectNameTextView.setText(arrClassNames.get(position).subject_name);
        holder.semesterTextView.setText(arrClassNames.get(position).semester);
        holder.departmentTextView.setText(arrClassNames.get(position).department);
    }

    @Override
    public int getItemCount() {
        return arrClassNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView backgroundImageView;
        TextView subjectNameTextView, semesterTextView, departmentTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            backgroundImageView=itemView.findViewById(R.id.card_view_background_image);
            subjectNameTextView=itemView.findViewById(R.id.subject_name);
            semesterTextView=itemView.findViewById(R.id.semester);
            departmentTextView=itemView.findViewById(R.id.department);
        }
    }
}
