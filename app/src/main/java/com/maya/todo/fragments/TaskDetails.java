package com.maya.todo.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.maya.todo.R;
import com.maya.todo.activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TaskDetails extends AppCompatActivity {

    private static final String TODOLIST = "TaskManager_Kartheek kadaru";
    private static final String TAG = "FragmentPage";
    private Task task = null;
    private TextView _dateTextView;
    private TextView _statusTextView;
    private TextView _titleTextView;
    private TextView _descTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);
        Intent createdIntent = getIntent();
        String taskJson = createdIntent.getStringExtra("Task");
        if (taskJson != null) {
            task = (new Gson()).fromJson(taskJson, Task.class);
        }
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        FloatingActionButton doneButton = findViewById(R.id.doneActionButton);
        FloatingActionButton removeButton = findViewById(R.id.removeActionButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ViewPagerAdapter vpa = MainActivity.getViewPagerAdapter();
                if (task != null) {
                    TaskSaving.changeCategory(task, "DONE");
                    updateSharedPreferences();
                    if (vpa != null) {
                        vpa.notifyDataSetChanged();
                    }
                    finish();
                }
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ViewPagerAdapter vpa = MainActivity.getViewPagerAdapter();
                if (task != null) {
                    TaskSaving.removeTask(task);
                    updateSharedPreferences();
                    if (vpa != null) {
                        vpa.notifyDataSetChanged();
                    }
                    finish();
                }
            }
        });

        retrieveTextViews();
        initTextDetails();
    }

    public void retrieveTextViews() {
        _titleTextView = findViewById(R.id.titleLabel);
        _descTextView = findViewById(R.id.description_details);
        _dateTextView = findViewById(R.id.dueDate_details);
        _statusTextView = findViewById(R.id.status_details);
    }

    public void initTextDetails() {
        _titleTextView.setText(task.getTitle());
        _descTextView.setText(task.getDesc());

        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, dd MMMM - hh:mm aa");
        try {
            _dateTextView.setText(dateFormatter.format(task.getDueDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // get DueDate and CurrentDate to compare them
        Date dueDate = task.getDueDate();
        Date currentDate = new Date();
        Calendar currentCalendar = Calendar.getInstance();
        Calendar dueCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentDate);
        dueCalendar.setTime(dueDate);
        // Set the status depending of the current and the due date
        String status;
        if (currentDate.after(dueDate) && !task.getCategory().equals("DONE")) {
            status = "Task expired";
            _statusTextView.setTextColor(Color.parseColor("#dd1d1d")); // Red
        } else if (currentCalendar.get(Calendar.DAY_OF_MONTH) == dueCalendar.get(Calendar.DAY_OF_MONTH)
                && currentCalendar.get(Calendar.MONTH) == dueCalendar.get(Calendar.MONTH)
                && currentCalendar.get(Calendar.YEAR) == dueCalendar.get(Calendar.YEAR)
                && !task.getCategory().equals("DONE")) {
            status = "Task closed to the deadline !";
            _statusTextView.setTextColor(Color.parseColor("#ff9a00")); // Orange
        } else if (task.getCategory().equals("DONE")) {
            status = "Task completed";
            _statusTextView.setTextColor(Color.parseColor("#149a0b")); // Green
        } else {
            status = "Task in progress";
            _statusTextView.setTextColor(Color.parseColor("#149a0b")); // Green
        }
        _statusTextView.setText(status);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cancel_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateSharedPreferences() {
        SharedPreferences sharedPreferences;
        Gson gson = new Gson();
        sharedPreferences = getSharedPreferences(TODOLIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonFinal = gson.toJson(TaskSaving.getTasks());
        editor.putString("Tasks", jsonFinal);
        jsonFinal = gson.toJson(TaskSaving.getCategories());
        editor.putString("Categories", jsonFinal);
        editor.apply();
        Log.d(TAG, sharedPreferences.getAll().toString());
    }

}
