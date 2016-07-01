package br.com.memorify.habittrackerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import br.com.memorify.habittrackerapp.model.Habit;
import br.com.memorify.habittrackerapp.util.DatabaseHelper;
import br.com.memorify.habittrackerapp.util.DatabaseManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseManager db = DatabaseManager.getInstance(getBaseContext());

        Habit fakeHabit = Habit.getFakeHabit();
        long insertedId = db.insertHabit(fakeHabit);
        fakeHabit._id = insertedId;

        String[] columns = new String[]{Habit.COLUMN__ID, Habit.COLUMN_HABIT_NAME, Habit.COLUMN_WHY_IS_IMPORTANT, Habit.COLUMN_ACCOMPLISH_TIMES};
        Habit selectedHabit = db.getHabit(insertedId, columns);

        fakeHabit.why = "Learn everyday,cof";
        db.updateHabit(fakeHabit);
        Habit updatedHabit = db.getHabit(fakeHabit._id, columns);

        db.deleteAllHabits();
    }
}
