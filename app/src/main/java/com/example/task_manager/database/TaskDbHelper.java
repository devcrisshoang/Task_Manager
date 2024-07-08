package com.example.task_manager.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.task_manager.models.Tasks;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "taskManager.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TASK_NAME = "task_name";
    public static final String COLUMN_TASK_IMPORTANT = "task_important";
    public static final String COLUMN_TASK_URGENT = "task_urgent";
    public static final String COLUMN_REMINDER = "reminder";
    public static final String COLUMN_STATUS_TASK = "status_task";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TASK_NAME + " TEXT, " +
                    COLUMN_TASK_IMPORTANT + " INTEGER, " +
                    COLUMN_TASK_URGENT + " INTEGER, " +
                    COLUMN_REMINDER + " INTEGER, " +
                    COLUMN_STATUS_TASK + " INTEGER" +
                    ");";

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // Chèn dữ liệu
    public long addTask(Tasks task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, task.getTask_name());
        values.put(COLUMN_TASK_IMPORTANT, task.getTask_important());
        values.put(COLUMN_TASK_URGENT, task.getTask_urgent());
        values.put(COLUMN_REMINDER, task.getReminder().getTime());
        values.put(COLUMN_STATUS_TASK, task.isStatus_task() ? 1 : 0);

        long id = db.insert(TABLE_TASKS, null, values);
        db.close();
        return id;
    }

    // Đọc dữ liệu
    @SuppressLint("Range")
    public List<Tasks> getAllTasks() {
        List<Tasks> tasksList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Tasks task = new Tasks(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_IMPORTANT)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_URGENT)),
                        new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_REMINDER))),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS_TASK)) == 1
                );
                tasksList.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tasksList;
    }

    // Cập nhật dữ liệu
    public int updateTask(Tasks task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, task.getTask_name());
        values.put(COLUMN_TASK_IMPORTANT, task.getTask_important());
        values.put(COLUMN_TASK_URGENT, task.getTask_urgent());
        values.put(COLUMN_REMINDER, task.getReminder().getTime());
        values.put(COLUMN_STATUS_TASK, task.isStatus_task() ? 1 : 0);

        int rowsUpdated = db.update(TABLE_TASKS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(task.getId())});
        db.close();
        return rowsUpdated;
    }

    // Xóa dữ liệu
    public void deleteTask(int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + " = ?", new String[]{String.valueOf(taskId)});
        db.close();
    }
}
