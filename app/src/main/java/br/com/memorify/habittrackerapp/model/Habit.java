package br.com.memorify.habittrackerapp.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.com.memorify.habittrackerapp.util.DatabaseHelper;

public class Habit {

    public static final String TABLE_NAME = "Habit";

    public static class Meta {
        public static String CREATE_TABLE() {
            return CREATE_TABLE(TABLE_NAME);
        }
        public static String CREATE_TABLE(String tableName) {
            return String.format(DatabaseHelper.TEMPLATE_CREATE_TABLE, tableName,
                    COLUMN__ID + " integer primary key, "
                            + COLUMN_HABIT_NAME + " text, "
                            + COLUMN_ACCOMPLISH_TIMES + " integer, "
                            + COLUMN_WHY_IS_IMPORTANT + " text, "
                            + COLUMN_REMINDER_TIME + " real, "
                            + COLUMN_HOW_LONG_IT_TAKE + " real, "
                            + COLUMN_ORDER + " integer  ");
        }
    }

    public static final String COLUMN__ID = "_id";
    public static final String COLUMN_HABIT_NAME = "name";
    public static final String COLUMN_ACCOMPLISH_TIMES = "accomplishTimes";
    public static final String COLUMN_WHY_IS_IMPORTANT = "why";
    public static final String COLUMN_REMINDER_TIME = "reminder";
    public static final String COLUMN_HOW_LONG_IT_TAKE = "howLong";
    public static final String COLUMN_ORDER = "position";

    public long _id;
    public String name;
    public int accomplishTimes;
    public String why;
    public Date nextReminder;
    public long howLongItTakeInMillis;
    public int order;

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_HABIT_NAME, name);
        values.put(COLUMN_ACCOMPLISH_TIMES, accomplishTimes);
        values.put(COLUMN_WHY_IS_IMPORTANT, why);
        if (nextReminder != null) {
            values.put(COLUMN_REMINDER_TIME, nextReminder.getTime());
        }
        values.put(COLUMN_HOW_LONG_IT_TAKE, howLongItTakeInMillis);
        values.put(COLUMN_ORDER, order);
        return values;
    }

    public static Habit fromCursor(Cursor cursor) {
        Habit habit = new Habit();
        int columnIndex = cursor.getColumnIndex(COLUMN__ID);
        if (columnIndex != -1) {
            habit._id = cursor.getLong(columnIndex);
        }

        columnIndex = cursor.getColumnIndex(COLUMN_HABIT_NAME);
        if (columnIndex != -1) {
            habit.name = cursor.getString(columnIndex);
        }

        columnIndex = cursor.getColumnIndex(COLUMN_ACCOMPLISH_TIMES);
        if (columnIndex != -1) {
            habit.accomplishTimes = cursor.getInt(columnIndex);
        }

        columnIndex = cursor.getColumnIndex(COLUMN_WHY_IS_IMPORTANT);
        if (columnIndex != -1) {
            habit.why = cursor.getString(columnIndex);
        }

        columnIndex = cursor.getColumnIndex(COLUMN_REMINDER_TIME);
        if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
            habit.nextReminder = new Date(cursor.getLong(columnIndex));
        }

        columnIndex = cursor.getColumnIndex(COLUMN_HOW_LONG_IT_TAKE);
        if (columnIndex != -1) {
            habit.howLongItTakeInMillis = cursor.getLong(columnIndex);
        }

        columnIndex = cursor.getColumnIndex(COLUMN_ORDER);
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
}
