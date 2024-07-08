package com.example.task_manager.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.task_manager.AddNewActivity;
import com.example.task_manager.R;
import com.example.task_manager.adapter.MyArrayAdapter;
import com.example.task_manager.database.TaskDbHelper;
import com.example.task_manager.models.Tasks;

import java.sql.Date;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private Button button_addTasks;
    private ListView listView;
    private MyArrayAdapter adapter;
    private ArrayList<Tasks> arrayList;
    private TaskDbHelper dbHelper;

    private int RESULT_OK = -1;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the button
        button_addTasks = view.findViewById(R.id.button_addTasks);
        listView = view.findViewById(R.id.listView_Actived);
        arrayList = new ArrayList<>();
        dbHelper = new TaskDbHelper(getActivity());

        // Set an OnClickListener on the button
        button_addTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddNewActivity for result
                Intent intent = new Intent(getActivity(), AddNewActivity.class);
                startActivityForResult(intent, 1); // Sử dụng requestCode = 1
            }
        });

        // Khởi tạo adapter và gán vào ListView
        adapter = new MyArrayAdapter(getActivity(), R.layout.activity_item, arrayList);
        listView.setAdapter(adapter);

        // Lấy dữ liệu từ cơ sở dữ liệu và hiển thị
        loadTasksFromDatabase();

        return view;
    }

    private void loadTasksFromDatabase() {
        // Lấy tất cả các task từ cơ sở dữ liệu
        arrayList.clear();
        arrayList.addAll(dbHelper.getAllTasks());
        adapter.notifyDataSetChanged();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            // Nhận dữ liệu từ Intent
//            String taskName = data.getStringExtra("TASK_NAME");
//            int important = data.getIntExtra("IMPORTANT", 0);
//            int urgent = data.getIntExtra("URGENT", 0);
//            Date selectedDate = (Date) data.getSerializableExtra("CALENDAR");
//
//            // Thêm dữ liệu vào cơ sở dữ liệu
//            Tasks newTask = new Tasks(taskName, important, urgent, selectedDate, false);
//            dbHelper.addTask(newTask);
//
//            // Cập nhật ListView
//            loadTasksFromDatabase();
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        // Lấy dữ liệu từ cơ sở dữ liệu mỗi khi Fragment được hiển thị lại
        loadTasksFromDatabase();
    }
}
