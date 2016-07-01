package br.com.memorify.habittrackerapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.memorify.habittrackerapp.model.Habit;

public class DatabaseManager {

    private static final String TAG = "DatabaseManager";

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqlite;

    private static DatabaseManager mInstance;

    public static DatabaseManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseManager(context.getApplicationContext());
        }
        return mInstance;
    }

    private DatabaseManager(Context context) {
        this.context = context;
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public SQLiteDatabase getWritableDatabase() {
        if (sqlite == null || !sqlite.isOpen()) {
            sqlite = dbHelper.getWritableDatabase();
        }
        return sqlite;
    }

    public SQLiteDatabase getReadableDatabase() {
        if (sqlite == null || !sqlite.isOpen()) {
            sqlite = dbHelper.getReadableDatabase();
        }
        return sqlite;
    }

    public long insertHabit(Habit habit) {
        long insertId = -1;
        try {
            getWritableDatabase();
            if (sqlite != null) {
                insertId = sqlite.insert(Habit.TABLE_NAME, null, habit.getContentValues());
            }
        } catch (SQLException sqlerror) {
            Log.e("Insert into table error", sqlerror.getMessage());
            return insertId;
        }
        return insertId;
    }

    public int updateHabit(Habit habit) {
        int numberRowsAffected = 0;
        try {
            getWritableDatabase();
            ContentValues contentValues = habit.getContentValues();
            if (sqlite != null) {
                String where = Habit.COLUMN__ID + " = ?";
                String[] args = {Long.toString(habit._id)};
                numberRowsAffected = sqlite.update(Habit.TABLE_NAME, contentValues, where, args);
            }
        } catch (SQLException sqlerror) {
            Log.e("Update into table error", sqlerror.getMessage());
        }

        return numberRowsAffected;
    }

    public Habit getHabit(long id, String[] columns) {
        Habit habit = null;
        try {
            getReadableDatabase();
            if (sqlite != null) {
                String selection = Habit.COLUMN__ID + " = " + id;
                Cursor cursor = sqlite.query(Habit.TABLE_NAME, columns, selection, null, null, null, null);
                cursor.moveToFirst();
                if (cursor.isAfterLast()) {
                    Log.e("NK", "DatabaseManager unable to find object having " + selection);
                } else {
                    habit = Habit.fromCursor(cursor);
                }
                cursor.close();
            }
        } catch (SQLException sqlerror) {
            Log.e("Select from table error", sqlerror.getMessage());
        }
        return habit;
    }

    public boolean deleteAllHabits() {
        try {
            getWritableDatabase();
            if (sqlite != null) {
                sqlite.delete(Habit.TABLE_NAME, null, null);
            }
        } catch (SQLException sqlerror) {
            Log.e("Delete into table error", sqlerror.getMessage());
            return false;
        }
        Log.e(TAG, "Deleted all data from DB.");
        return true;
    }
}
