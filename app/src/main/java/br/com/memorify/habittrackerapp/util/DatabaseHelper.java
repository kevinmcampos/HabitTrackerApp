package br.com.memorify.habittrackerapp.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.com.memorify.habittrackerapp.model.Habit;
import br.com.memorify.habittrackerapp.model.HabitContract;
import br.com.memorify.habittrackerapp.model.HabitContract.HabitEntry;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TEMPLATE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %1s (%2s);";
    private static final String DATABASE_NAME = "habits.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLES[] = {
            HabitEntry.Meta.CREATE_TABLE(),
    };

    private static DatabaseHelper mInstance;
    private Context context;

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String CREATE_TABLE : CREATE_TABLES) {
            db.execSQL(CREATE_TABLE);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ". Destroying all old data.");
        deleteDatabase();
        onCreate(db);
    }

    public void deleteDatabase() {
        context.deleteDatabase(DATABASE_NAME);
    }
}
