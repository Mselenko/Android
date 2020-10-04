package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.todolist.domain.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ToDoListManager {

    private DatabaseHelper dbHelper;

    public ToDoListManager(Context context)
    {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public List<ToDoItem> getItems()
    {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DatabaseHelper.TABLE_NAME,
                null
        );

        List<ToDoItem> items = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ToDoItem item = new ToDoItem(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COMPLETED)) != 0,
                        cursor.getLong(cursor.getColumnIndex(DatabaseHelper.ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.TIMESTAMP))

                );
                items.add(item);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return items;
    }

    public void addItem(ToDoItem item)
    {

        ContentValues newItem = new ContentValues();
        newItem.put(DatabaseHelper.DESCRIPTION, item.getDescription());
        newItem.put(DatabaseHelper.COMPLETED, item.isComplete());
        newItem.put(DatabaseHelper.TIMESTAMP, item.getTimestamp());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(DatabaseHelper.TABLE_NAME, null, newItem);
    }

    public void deleteItem(ToDoItem item)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = new String[]{String.valueOf(item.getId())};

        db.delete(
                DatabaseHelper.TABLE_NAME,
                DatabaseHelper.ID +"=?",
                args
        );
    }


    public void updateItem(ToDoItem item)
    {
        ContentValues updateItem = new ContentValues();
        updateItem.put(DatabaseHelper.DESCRIPTION, item.getDescription());
        updateItem.put(DatabaseHelper.COMPLETED, item.isComplete());
        updateItem.put(DatabaseHelper.TIMESTAMP, item.getTimestamp());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] args = new String[]{String.valueOf(item.getId())};

        db.update(
                DatabaseHelper.TABLE_NAME,
                updateItem,
                DatabaseHelper.ID +"=?",
                args
        );
    }
}