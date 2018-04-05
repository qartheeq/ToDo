package com.maya.todo.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.maya.todo.R;
import com.maya.todo.activities.MainActivity;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllTaskFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TODOLIST = "TaskManager_Noboud-Inpeng";
    private static final String TAG = "FragmentPage";


    public AllTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllTaskFragment newInstance(String param1, String param2) {
        AllTaskFragment fragment = new AllTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_all_task, container, false);

        if (TaskSaving.getTasksByCategory() != null) {
            final ListView lv = (ListView) view;
            lv.setAdapter(new CustomBaseAdapter(this.getContext(), TaskSaving.getTasksByCategory()));
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> a, View v, int position, long id) {
                    Object object = lv.getItemAtPosition(position);
                    Task task = (Task) object;
                    displayTaskOptions(task);
                    return true;
                }
            });
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    Object object = lv.getItemAtPosition(position);
                    Task task = (Task) object;
                    Intent intent = new Intent(getContext(), TaskDetails.class);
                    intent.putExtra("Task", (new Gson()).toJson(task));
                    startActivity(intent);
                }
            });
        }

    return view;
    }

    public void displayTaskOptions(final Task task) {
        CharSequence colors[] = new CharSequence[] {"Done", "Remove", "Change category"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select an option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ViewPagerAdapter vpa = MainActivity.getViewPagerAdapter();
                switch (which) {
                    case 0: // Done
                        TaskSaving.changeCategory(task, "DONE");
                        updateSharedPreferences();
                        // Update View
                        if (vpa != null) {
                            vpa.notifyDataSetChanged();
                        }
                        break;
                    case 1: // Remove
                        TaskSaving.removeTask(task);
                        updateSharedPreferences();
                        // Update View
                        if (vpa != null) {
                            vpa.notifyDataSetChanged();
                        }
                        break;
                    case 2: // Change category
                        changeCategory(task);
//                        updateSharedPreferences();
                        // View is updated in changeCategory as AlertBuilder is asynchronous
                        break;
                }
            }
        });

        builder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.show();
    }

    public void changeCategory(final Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select a category");

        ArrayList<String> categories = new ArrayList<>(TaskSaving.getCategories());


        Iterator<String> tmp = categories.iterator();
        while (tmp.hasNext()) {
            String s = tmp.next();
            if (s.equals(task.getCategory())) {
                tmp.remove();
                break;
            }
        }

        final String[] arrayCategories = categories.toArray(new String[categories.size()]);

        builder.setItems(arrayCategories, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task.setCategory(arrayCategories[which]);
                updateSharedPreferences();
                // Update View
                if (MainActivity.getViewPagerAdapter() != null) {
                    MainActivity.getViewPagerAdapter().notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public void updateSharedPreferences() {
        SharedPreferences sharedPreferences;
        Gson gson = new Gson();
        sharedPreferences = this.getActivity().getSharedPreferences(TODOLIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonFinal = gson.toJson(TaskSaving.getTasks());
        editor.putString("Tasks", jsonFinal);
        jsonFinal = gson.toJson(TaskSaving.getCategories());
        editor.putString("Categories", jsonFinal);
        editor.apply();
        Log.d(TAG, sharedPreferences.getAll().toString());
    }

}




