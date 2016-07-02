package br.com.memorify.habittrackerapp.model;

import br.com.memorify.habittrackerapp.util.DatabaseHelper;

public class HabitContract {

    public static final class HabitEntry {

        public static final String TABLE_NAME = "habit";

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
    }

}
