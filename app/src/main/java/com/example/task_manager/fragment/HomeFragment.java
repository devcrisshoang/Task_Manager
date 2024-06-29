package com.example.task_manager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.task_manager.AddNewActivity;
import com.example.task_manager.MainActivity;
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
        button_addTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddNewActivity when the button is clicked
                Intent intent = new Intent(getActivity(), AddNewActivity.class);
                startActivity(intent);
            }
        });

        // Nhận Intent từ Activity gửi
        if (getActivity() != null) {
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                // Trích xuất các giá trị từ Intent
                String taskName = intent.getStringExtra("TASK_NAME");
                int important = intent.getIntExtra("IMPORTANT", 0);
                int urgent = intent.getIntExtra("URGENT", 0);
                Date selectedDate = (Date) intent.getSerializableExtra("CALENDAR");

                // Thêm dữ liệu vào ArrayList
                arrayList.add(new Tasks(1, taskName, important, urgent, selectedDate, false));

                // Khởi tạo adapter và gán vào ListView
                adapter = new MyArrayAdapter(getActivity(), R.layout.layout_item, arrayList);
                listView.setAdapter(adapter);

                // Bây giờ bạn có thể làm bất kỳ điều gì với dữ liệu đã nhận được
            }
        }

        return view;
    }
}

