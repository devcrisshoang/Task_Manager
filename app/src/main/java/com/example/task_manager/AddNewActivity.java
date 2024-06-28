package com.example.task_manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNewActivity extends AppCompatActivity {
    private ImageButton button_calender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setWidget();
        button_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog();
            }
        });
    }
    public void setWidget(){
        button_calender = findViewById(R.id.button_calender);
    }

    private void showDateTimeDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_date_time_picker, null);
        dialogBuilder.setView(dialogView);

        final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        final TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        Button buttonCancel = dialogView.findViewById(R.id.button_cancel);
        Button buttonAdd = dialogView.findViewById(R.id.button_add);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                String selectedDateTime = "Selected Date and Time: " + day + "/" + (month + 1) + "/" + year + " " + hour + ":" + minute;
                Toast.makeText(AddNewActivity.this, selectedDateTime, Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        });
    }
}