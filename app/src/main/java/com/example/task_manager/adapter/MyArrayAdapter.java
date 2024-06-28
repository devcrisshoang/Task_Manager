package com.example.task_manager.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.task_manager.R;
import com.example.task_manager.models.Tasks;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Tasks> {
    Activity context;
    int idLayout;
    ArrayList<Tasks> arrayList;
    public MyArrayAdapter(Activity context, int idLayout, ArrayList<Tasks> arrayList) {
        super(context, idLayout, arrayList);
        this.context = context;
        idLayout = idLayout;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myFlater = context.getLayoutInflater();
        convertView = myFlater.inflate(idLayout,null);
        Tasks tasks = arrayList.get(position);
        RadioButton radioButton_task = convertView.findViewById(R.id.radioButton_task);
        return  convertView;
    }
}
