package com.example.newreset.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newreset.Model.ToDoModel;
import com.example.newreset.R;
import com.example.newreset.Utils.DataBaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {
    private List<ToDoModel> taskList;
    private final DataBaseHelper myDB;
    private final Context context;

    public ToDoAdapter(List<ToDoModel> taskList, Context context) {
        this.taskList = taskList;
        this.context = context;
        myDB = new DataBaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ToDoModel task = taskList.get(position);

        // Set the text and status
        holder.taskText.setText(task.getTask());
        holder.taskCheckBox.setOnCheckedChangeListener(null); // Reset listener to avoid re-binding issues
        holder.taskCheckBox.setChecked(task.getStatus() == 1);

        // Handle checkbox state changes
        holder.taskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                myDB.deleteTask(task.getId());
                taskList.remove(position);

                // Notify the adapter about item removal and update the list
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, taskList.size());

                Toast.makeText(context, "Task deleted", Toast.LENGTH_SHORT).show();
            } else {
                // If task is unchecked, we could mark it as incomplete or perform another action
                task.setStatus(0);
                myDB.updateStatus(task.getId(), 0);  // Mark as incomplete in DB if needed
            }
        });
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setTasks(List<ToDoModel> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox taskCheckBox;
        TextView taskText;

        ViewHolder(View view) {
            super(view);
            taskCheckBox = view.findViewById(R.id.checkbox_task);
            taskText = view.findViewById(R.id.task_text);
        }
    }
}
