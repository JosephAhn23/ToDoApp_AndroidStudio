package com.example.newreset;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newreset.Adapter.ToDoAdapter;
import com.example.newreset.Model.ToDoModel;
import com.example.newreset.Utils.AddNewTask;
import com.example.newreset.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerview;
    private FloatingActionButton fab;
    private DataBaseHelper myDB;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the splash screen layout
        setContentView(R.layout.activity_splash);

        // Delay for 3 seconds before switching to the main content
        new Handler().postDelayed(() -> {
            // Now, set the main content layout
            setContentView(R.layout.activity_main);

            // Initialize views
            mRecyclerview = findViewById(R.id.recyclerview);
            fab = findViewById(R.id.fab);
            myDB = new DataBaseHelper(MainActivity.this);

            // Initialize the adapter and set it to the RecyclerView
            mRecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            adapter = new ToDoAdapter(myDB.getAllTasks(), MainActivity.this);
            mRecyclerview.setAdapter(adapter);

            // Handle FAB click
            fab.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "FAB clicked", Toast.LENGTH_SHORT).show();
                showAddNewTaskDialog();
            });

        }, 3000);
    }

    private void showAddNewTaskDialog() {
        AddNewTask addNewTaskFragment = new AddNewTask(adapter);
        addNewTaskFragment.show(getSupportFragmentManager(), "addNewTask");
    }
}
