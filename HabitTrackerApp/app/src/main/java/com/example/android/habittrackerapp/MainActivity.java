package com.example.android.habittrackerapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.habittrackerapp.data.HabitContract.HabitEntry;
import com.example.android.habittrackerapp.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * Database helper that will provide us access to the database
     */
    private HabitDbHelper mDbHelper;

    private final static long RETURN_ALL_ROWS = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new HabitDbHelper(this);

        insertHabit();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the Log about the state of
     * the habits database.
     */
    private void displayDatabaseInfo() {

        // Get cursor from readHabit()
        Cursor cursor = readHabit();

        try {

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int streakColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_STREAK);
            int commentsColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_COMMENTS);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentStreak = cursor.getInt(streakColumnIndex);
                String currentComment = cursor.getString(commentsColumnIndex);
                // Display the values from each column of the current row in the cursor in the Log
                Log.i(LOG_TAG, "\n" + currentID + " - " +
                        currentName + " - " +
                        currentStreak + " - " +
                        currentComment);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded habit data into the database.
     */
    private void insertHabit() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "Learning Android Development from Udacity");
        values.put(HabitEntry.COLUMN_HABIT_STREAK, 13);
        values.put(HabitEntry.COLUMN_HABIT_COMMENTS, "Loving each and every moment");

        // Insert a new row for the Habit in the database, returning the ID of that new row.
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    /**
     * Helper method to read habit data from the database.
     */
    private Cursor readHabit() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_STREAK,
                HabitEntry.COLUMN_HABIT_COMMENTS};

        // Perform a query on the habits table
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        return cursor;
    }
}
