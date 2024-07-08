package com.example.task_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.task_manager.database.TaskDbHelper;
import com.example.task_manager.models.Tasks;

import java.util.Calendar;
import java.util.Date;

public class AddNewActivity extends AppCompatActivity {
    private ImageButton button_calender;
    private SeekBar seekBar_important;
    private EditText editText_write;
    private SeekBar seekBar_urgent;
    private Button button_add_task;
    private Calendar calendar;
    private TextView status_reminder;
    private boolean statusReminder = false;
    private Toolbar toolbar;
    private TaskDbHelper dbHelper;

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
        setSupportActionBar(toolbar);
        // Kích hoạt nút home như nút back
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        button_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statusReminder == false){
                    showDateTimeDialog();
                }
                else {
                    cancelReminder();
                }
            }
        });

        seekBar_important.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Được gọi khi giá trị của SeekBar thay đổi
                // 'progress' là giá trị hiện tại của SeekBar
                // 'fromUser' là true nếu sự thay đổi là do người dùng thực hiện

                // Xử lý hành động khi giá trị thay đổi
                // Ví dụ: cập nhật TextView để hiển thị giá trị SeekBar
                Toast.makeText(AddNewActivity.this, "Progress: " + progress, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Được gọi khi người dùng bắt đầu chạm vào SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Được gọi khi người dùng dừng chạm vào SeekBar
            }
        });
        button_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    public void sendData() {
        // Đặt các giá trị cần truyền vào Intent
        String taskName = editText_write.getText().toString();
        int important = seekBar_important.getProgress();
        int urgent = seekBar_urgent.getProgress();
        Date selectedDate = calendar.getTime();

        Tasks task = new Tasks( taskName, important, urgent, selectedDate, false);
        long taskId = dbHelper.addTask(task);

        if (taskId != -1) {
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show();
        }
        setResult(RESULT_OK);

//        Intent resultIntent = new Intent();
//        resultIntent.putExtra("TASK_NAME", taskName);
//        resultIntent.putExtra("IMPORTANT", important);
//        resultIntent.putExtra("URGENT", urgent);
//        resultIntent.putExtra("CALENDAR", selectedDate);

        // Trả kết quả về HomeFragment
        //setResult(RESULT_OK, resultIntent);
        finish(); // Kết thúc AddNewActivity
    }


    public void setWidget(){
        button_calender = findViewById(R.id.button_calender);
        seekBar_important = findViewById(R.id.seekBar_important);
        editText_write = findViewById(R.id.editText_write);
        seekBar_urgent = findViewById(R.id.seekBar_urgent);
        button_add_task = findViewById(R.id.button_add_task);
        calendar = Calendar.getInstance();
        status_reminder = findViewById(R.id.status_reminder);
        toolbar = findViewById(R.id.toolbarAddNewActivity);
        dbHelper = new TaskDbHelper(this);
    }

    private void showDateTimeDialog() {
        statusReminder = true;
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
                calendar.set(year, month, day, hour, minute);
                button_calender.setImageResource(R.drawable.clock);
                status_reminder.setText("Remove reminder");

                String selectedDateTime = "Selected Date and Time: " + day + "/" + (month + 1) + "/" + year + " " + hour + ":" + minute;
                Toast.makeText(AddNewActivity.this, selectedDateTime, Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        });
    }
    private void cancelReminder(){
        statusReminder = false;
        button_calender.setImageResource(R.drawable.calendar);
        status_reminder.setText("Add reminder");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Xử lý sự kiện nhấn nút back
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
