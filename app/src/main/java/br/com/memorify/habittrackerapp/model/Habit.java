package br.com.memorify.habittrackerapp.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.com.memorify.habittrackerapp.model.HabitContract.HabitEntry;

public class Habit {

    public long _id;
    public String name;
    public int accomplishTimes;
    public String why;
    public Date nextReminder;
    public long howLongItTakeInMillis;
    public int order;

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, name);
        values.put(HabitEntry.COLUMN_ACCOMPLISH_TIMES, accomplishTimes);
        values.put(HabitEntry.COLUMN_WHY_IS_IMPORTANT, why);
        if (nextReminder != null) {
            values.put(HabitEntry.COLUMN_REMINDER_TIME, nextReminder.getTime());
        }
        values.put(HabitEntry.COLUMN_HOW_LONG_IT_TAKE, howLongItTakeInMillis);
        values.put(HabitEntry.COLUMN_ORDER, order);
        return values;
    }

    public static Habit fromCursor(Cursor cursor) {
        Habit habit = new Habit();
        int columnIndex = cursor.getColumnIndex(HabitEntry.COLUMN__ID);
        if (columnIndex != -1) {
            habit._id = cursor.getLong(columnIndex);
        }

        columnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
        if (columnIndex != -1) {
            habit.name = cursor.getString(columnIndex);
        }

        columnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_ACCOMPLISH_TIMES);
        if (columnIndex != -1) {
            habit.accomplishTimes = cursor.getInt(columnIndex);
        }

        columnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_WHY_IS_IMPORTANT);
        if (columnIndex != -1) {
            habit.why = cursor.getString(columnIndex);
        }

        columnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_REMINDER_TIME);
        if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
            habit.nextReminder = new Date(cursor.getLong(columnIndex));
        }

        columnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HOW_LONG_IT_TAKE);
        if (columnIndex != -1) {
            habit.howLongItTakeInMillis = cursor.getLong(columnIndex);
        }

        columnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_ORDER);
        if (columnIndex != -1) {
            habit.order = cursor.getInt(columnIndex);
        }
        return habit;
    }

    public static Habit getFakeHabit() {
        Habit habit = new Habit();
        habit.name = "Check StackOverFlow everyday";
        habit.accomplishTimes = 3;
        habit.why = "Get some badges";
        long tomorrowInMillis = TimeUnit.DAYS.toMillis(1) + new Date().getTime();
        habit.nextReminder = new Date(tomorrowInMillis);
        habit.howLongItTakeInMillis = TimeUnit.MINUTES.toMillis(2);
        habit.order = 44;
        return habit;
    }

    /**
     * Returns a brief description of this Habit. The exact details of the representation are
     * unspecified and subject to change but the following may be regarded as typical:
     * Habit #0: name=Check StackOverFlow everyday, accomplishTimes=3, why=Get some badges, nextReminder=Sun Jul 03 19:15:02 GMT+00:00 2016, howLongItTakeInMillis=120000, order=44
     */
    @Override
    public String toString() {
        return "Habit #" + _id + ": name=" + name + ", accomplishTimes=" + accomplishTimes +
               ", why=" + why + ", nextReminder=" + nextReminder + ", howLongItTakeInMillis=" + howLongItTakeInMillis + ", order=" + order;
    }
}
