package com.example.task_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.task_manager.R;
import com.example.task_manager.models.Tasks;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<Tasks> {
    Context context;
    int idLayout;
    ArrayList<Tasks> arrayList;

    public MyArrayAdapter(Context context, int idLayout, ArrayList<Tasks> arrayList) {
        super(context, idLayout, arrayList);
        this.context = context;
        this.idLayout = idLayout;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(idLayout, parent, false);
        }

        Tasks tasks = arrayList.get(position);

        RadioButton radioButton_task = convertView.findViewById(R.id.radioButton_task);
        SeekBar seekBar_important = convertView.findViewById(R.id.seekBar_important_item);
        SeekBar seekBar_urgent = convertView.findViewById(R.id.seekBar_urgent_item);

        // Kiểm tra null
        if (radioButton_task != null) {
            radioButton_task.setChecked(false);
            radioButton_task.setText(tasks.getTask_name());
        }

        if (seekBar_important != null) {
            seekBar_important.setProgress(tasks.getTask_important());
        }

        if (seekBar_urgent != null) {
            seekBar_urgent.setProgress(tasks.getTask_urgent());
        }

        return convertView;
    }
}
