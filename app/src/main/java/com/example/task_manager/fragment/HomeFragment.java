package com.example.task_manager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.task_manager.AddNewActivity;
import com.example.task_manager.R;

public class HomeFragment extends Fragment {
    private Button button_addTasks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the button
        button_addTasks = view.findViewById(R.id.button_addTasks);

        // Set an OnClickListener on the button
        button_addTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddNewActivity when the button is clicked
                Intent intent = new Intent(getActivity(), AddNewActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
