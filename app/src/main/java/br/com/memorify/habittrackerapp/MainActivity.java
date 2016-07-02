package br.com.memorify.habittrackerapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import br.com.memorify.habittrackerapp.model.Habit;
import br.com.memorify.habittrackerapp.model.HabitContract;
import br.com.memorify.habittrackerapp.util.DatabaseHelper;
import br.com.memorify.habittrackerapp.util.DatabaseManager;

import static br.com.memorify.habittrackerapp.model.HabitContract.*;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseManager db = DatabaseManager.getInstance(getBaseContext());

        /* Create a fake habit */
        Habit fakeHabit = Habit.getFakeHabit();
        Log.e(TAG, "fakeHabit: " + fakeHabit.toString());

        /* Insert it in database and retrive it ID */
        long insertedId = db.insertHabit(fakeHabit);
        fakeHabit._id = insertedId;

        /* Retrive it fake habit from database */
        String[] columns = new String[]{HabitEntry.COLUMN__ID, HabitEntry.COLUMN_HABIT_NAME, HabitEntry.COLUMN_WHY_IS_IMPORTANT, HabitEntry.COLUMN_ACCOMPLISH_TIMES};
        Cursor selectedHabitCursor = db.getHabit(insertedId, columns);
        selectedHabitCursor.moveToFirst();
        if (!selectedHabitCursor.isAfterLast()) {
            Habit retriveHabit = Habit.fromCursor(selectedHabitCursor);
            Log.e(TAG, "retriveHabit: " + retriveHabit.toString());
        }

        /* Change fake habit 'why' field and update it on db */
        fakeHabit.why = "Learn everyday,cof";
        db.updateHabit(fakeHabit);

        /* Retrive it fake habit from database */
        Cursor updatedHabitCursor = db.getHabit(fakeHabit._id, columns);
        updatedHabitCursor.moveToFirst();
        if (!updatedHabitCursor.isAfterLast()) {
            Habit updatedHabit = Habit.fromCursor(updatedHabitCursor);
            Log.e(TAG, "updatedHabit: " + updatedHabit.toString());
        }

        db.deleteAllHabits();
    }
}
