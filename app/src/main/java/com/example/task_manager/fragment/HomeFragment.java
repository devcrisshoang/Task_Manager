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
import com.example.task_manager.models.Tasks;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {
    private Button button_addTasks;
    private ListView listView;
    private MyArrayAdapter adapter;
    private ArrayList<Tasks> arrayList;
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

        // Set an OnClickListener on the button
        // Trong HomeFragment
        button_addTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddNewActivity for result
                Intent intent = new Intent(getActivity(), AddNewActivity.class);
                startActivityForResult(intent, 1); // Sử dụng requestCode = 1
            }
        });


        // Khởi tạo adapter và gán vào ListView
        adapter = new MyArrayAdapter(getActivity(), R.layout.layout_item, arrayList);
        listView.setAdapter(adapter);

        // Nhận Intent từ Activity gửi
        receiveDataFromIntent();

        return view;
    }

    private void receiveDataFromIntent() {
        if (getActivity() != null) {
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                // Trích xuất các giá trị từ Intent
                String taskName = intent.getStringExtra("TASK_NAME");
                int important = intent.getIntExtra("IMPORTANT", 0);
                int urgent = intent.getIntExtra("URGENT", 0);
                Date selectedDate = (Date) intent.getSerializableExtra("CALENDAR");

                // Kiểm tra nếu taskName không null
                if (taskName != null) {
                    // Thêm dữ liệu vào ArrayList
                    arrayList.add(new Tasks(1, taskName, important, urgent, selectedDate, false));
                    // Thông báo cho Adapter về sự thay đổi dữ liệu
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Nhận dữ liệu từ Intent
            String taskName = data.getStringExtra("TASK_NAME");
            int important = data.getIntExtra("IMPORTANT", 0);
            int urgent = data.getIntExtra("URGENT", 0);
            Date selectedDate = (Date) data.getSerializableExtra("CALENDAR");

            // Thêm dữ liệu vào ArrayList và cập nhật adapter
            if (taskName != null) {
                arrayList.add(new Tasks(1, taskName, important, urgent, selectedDate, false));
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Nhận Intent từ Activity mỗi khi Fragment được hiển thị lại
        //receiveDataFromIntent();
    }
}
