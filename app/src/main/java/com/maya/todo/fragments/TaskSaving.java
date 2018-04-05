package com.maya.todo.fragments;

import java.util.ArrayList;
import java.util.Collections;


public class TaskSaving {


    private static ArrayList<Task> tasks = null;

    private static ArrayList<String> categories = null;

    private static String currentCategory = null;

    public static String getCurrentCategory() {
        return currentCategory;
    }

    public static void setCurrentCategory(String category) {
        TaskSaving.currentCategory = category;
    }

    public static ArrayList<Task> getTasks() {
        if (tasks != null && tasks.isEmpty()) {
            Collections.sort(tasks);
            Collections.reverse(tasks);
        }
        return tasks;
    }

    public static ArrayList<Task> getTasksByCategory() {
        ArrayList<Task> tasksByCategory = null;

        if (currentCategory != null && tasks != null) {
            for (Task tmp : tasks) {
                if (tmp.getCategory().toLowerCase().equals(currentCategory.toLowerCase())) {
                    if (tasksByCategory == null) {
                        tasksByCategory = new ArrayList<>();
                    }
                    tasksByCategory.add(tmp);
                }
            }
        }
        if (tasksByCategory != null && tasksByCategory.isEmpty()) {
            Collections.sort(tasksByCategory);
            Collections.reverse(tasksByCategory);
        }
        return tasksByCategory;
    }

    public static ArrayList<String> getCategories() {
        if (categories == null) {
            categories = new ArrayList<>();
            categories.add("TO DO");
            categories.add("DONE");
        }
        return categories;
    }

    public static void setCategories(ArrayList<String> categories) {
        TaskSaving.categories = categories;
    }

    public static boolean doesCategoryExist(String category) {
        for (String tmp : getCategories()) {
            if (tmp.toLowerCase().equals(category.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static void setTasks(ArrayList<Task> tasks) {
        TaskSaving.tasks = tasks;
    }

    public static boolean addNewCategory(String category) {
        for (String tmp : categories) {
            if (tmp.toLowerCase().equals(category.toLowerCase())) {
                return false;
            }
        }
        categories.add(category);
        return true;
    }

    public static void addNewTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<Task>();
        }
        tasks.add(task);
    }

    public static void changeCategory(Task task, String category) {
        for (Task tmp : tasks) {
            if (tmp.equals(task)) {
                tmp.setCategory(category);
                break;
            }
        }
    }

    public static void removeTask(Task task) {
        if (tasks != null) {
            tasks.remove(task);
            if (tasks.isEmpty()) {
                tasks = null;
            }
        }
    }

    public static void removeCategory(String category) {
        for (String tmp : getCategories()) {
            if (tmp.toLowerCase().equals(category.toLowerCase())) {
                categories.remove(category);
                return;
            }
        }
    }

}
