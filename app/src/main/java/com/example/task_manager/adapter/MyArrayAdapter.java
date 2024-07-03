package com.example.task_manager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.task_manager.AddNewActivity;
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

        CheckBox checkBox_taskName = convertView.findViewById(R.id.checkBox_taskName);
        SeekBar seekBar_important = convertView.findViewById(R.id.seekBar_important_item);
        SeekBar seekBar_urgent = convertView.findViewById(R.id.seekBar_urgent_item);

        // Kiểm tra null và thiết lập dữ liệu
        if (checkBox_taskName != null) {
            checkBox_taskName.setChecked(tasks.isStatus_task());
            if (tasks.isStatus_task()) {
                SpannableString spannableString = new SpannableString(tasks.getTask_name());
                spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), 0);
                checkBox_taskName.setText(spannableString);

            } else {
                checkBox_taskName.setText(tasks.getTask_name());

            }
            //Toast.makeText(context, "Task Important: " + task_important, Toast.LENGTH_SHORT).show();
            checkBox_taskName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_taskName.isChecked()) {
                        SpannableString spannableString = new SpannableString(tasks.getTask_name());
                        spannableString.setSpan(new StrikethroughSpan(), 0, spannableString.length(), 0);
                        checkBox_taskName.setText(spannableString);
                        tasks.setStatus_task(true);
                        seekBar_important.setProgress(0);
                        seekBar_urgent.setProgress(0);
                    } else {
                        checkBox_taskName.setText(tasks.getTask_name());
                        tasks.setStatus_task(false);
                        seekBar_important.setProgress(tasks.getTask_important());
                        seekBar_urgent.setProgress(tasks.getTask_urgent());
                    }
                }
            });
        }
        Drawable greenColor = new ColorDrawable(Color.GREEN);
        Drawable yellowColor = new ColorDrawable(Color.YELLOW);
        Drawable redColor = new ColorDrawable(Color.RED);
        if (seekBar_important != null) {
            seekBar_important.setProgress(tasks.getTask_important());
            seekBar_important.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (seekBar_important.getProgress() <= 33){
                        seekBar_important.setProgressDrawable(greenColor);
                    }
                    else if(seekBar_important.getProgress() > 33 && seekBar_important.getProgress() <=66){
                        seekBar_important.setProgressDrawable(yellowColor);
                    }
                    else{
                        seekBar_important.setProgressDrawable(redColor);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        if (seekBar_urgent != null) {
            seekBar_urgent.setProgress(tasks.getTask_urgent());
            seekBar_urgent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (seekBar_urgent.getProgress() <= 33){
                        seekBar_urgent.setProgressDrawable(greenColor);
                    }
                    else if(seekBar_urgent.getProgress() > 33 && seekBar_urgent.getProgress() <=66){
                        seekBar_urgent.setProgressDrawable(yellowColor);
                    }
                    else{
                        seekBar_urgent.setProgressDrawable(redColor);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        return convertView;
    }
}
