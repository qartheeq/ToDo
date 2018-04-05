package com.maya.todo.fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static eu.epitech.todolist.R.id.dueDate;
import static eu.epitech.todolist.R.id.status;

/**
 * Created by noboud_n on 11/01/2017.
 */
public class CustomBaseAdapter extends BaseAdapter {
    private static ArrayList<Task> searchArrayList = TaskSaving.getTasksByCategory();

    private LayoutInflater mInflater;

    public CustomBaseAdapter(Context context, ArrayList<Task> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_view, null);
            holder = new ViewHolder();
            holder.txtTitle = convertView.findViewById(R.id.title);
            holder.txtDescription = convertView.findViewById(R.id.description);
            holder.txtDueDate = convertView.findViewById(R.id.dueDate);
            holder.txtStatus = convertView.findViewById(R.id.Status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitle.setText(searchArrayList.get(position).getTitle());
        holder.txtDescription.setText(searchArrayList.get(position).getDesc());
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd EE MMMM yyyy");
            String date = df.format(searchArrayList.get(position).getDueDate());
            df = new SimpleDateFormat("hh:mm aaaa");
            String time = df.format(searchArrayList.get(position).getDueDate());
            holder.txtDueDate.setText("Due date : " + date + " at " + time);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // get DueDate and CurrentDate to compare them
        Date dueDate = searchArrayList.get(position).getDueDate();
        Date currentDate = new Date();
        Calendar currentCalendar = Calendar.getInstance();
        Calendar dueCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentDate);
        dueCalendar.setTime(dueDate);
        // Set the status depending of the current and the due date
        String status;
        if (currentDate.after(dueDate) && !searchArrayList.get(position).getCategory().equals("DONE")) {
            status = "Task expired";
            holder.txtStatus.setTextColor(Color.parseColor("#dd1d1d")); // Red
        } else if (currentCalendar.get(Calendar.DAY_OF_MONTH) == dueCalendar.get(Calendar.DAY_OF_MONTH)
                    && currentCalendar.get(Calendar.MONTH) == dueCalendar.get(Calendar.MONTH)
                    && currentCalendar.get(Calendar.YEAR) == dueCalendar.get(Calendar.YEAR)
                    && !searchArrayList.get(position).getCategory().equals("DONE")) {
            status = "Task closed to the deadline !";
            holder.txtStatus.setTextColor(Color.parseColor("#ff9a00")); // Orange
        } else if (searchArrayList.get(position).getCategory().equals("DONE")) {
            status = "Task completed";
            holder.txtStatus.setTextColor(Color.parseColor("#149a0b")); // Green
        } else {
            status = "Task in progress";
            holder.txtStatus.setTextColor(Color.parseColor("#149a0b")); // Green
        }
        holder.txtStatus.setText(status);

        return convertView;
    }

    static class ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        TextView txtDueDate;
        TextView txtStatus;
    }
}
