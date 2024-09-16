package com.example.newreset.Utils;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newreset.Adapter.ToDoAdapter;
import com.example.newreset.Model.ToDoModel;
import com.example.newreset.R;
import com.example.newreset.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    private static final String TAG = "AddNewTask";
    private EditText mEditText;
    private Button mSaveButton;
    private ToDoAdapter adapter;
    private DataBaseHelper myDb;

    // Constructor to pass the adapter
    public AddNewTask(ToDoAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the add_newtask layout
        return inflater.inflate(R.layout.add_newtask, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        mEditText = view.findViewById(R.id.edittext);
        mSaveButton = view.findViewById(R.id.button_save);
        myDb = new DataBaseHelper(getActivity());

        mSaveButton.setOnClickListener(v -> {
            String taskText = mEditText.getText().toString();
            Log.d(TAG, "Task Text: " + taskText);

            if (taskText.isEmpty()) {
                Toast.makeText(getActivity(), "Task cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                // Create a new task object
                ToDoModel task = new ToDoModel();
                task.setTask(taskText);
                task.setStatus(0);  // Set task status as incomplete

                // Add the task to the database and update the adapter
                boolean isInserted = myDb.insertTask(task);
                if (isInserted) {
                    Log.d(TAG, "Task inserted successfully");
                    adapter.setTasks(myDb.getAllTasks());
                    Toast.makeText(getActivity(), "Task added", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Log.d(TAG, "Failed to insert task");
                    Toast.makeText(getActivity(), "Failed to add task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
