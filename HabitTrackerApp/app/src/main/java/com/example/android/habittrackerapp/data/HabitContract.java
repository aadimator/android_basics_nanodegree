package com.example.android.habittrackerapp.data;

import android.provider.BaseColumns;

/**
 * Created by Aadam on 10/4/2016.
 */

public final class HabitContract {

    // To prevent form accidental initialization
    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {

        /**
         * Name of database table for habits
         */
        public final static String TABLE_NAME = "habits";

        /**
         * Unique ID number for the habit (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the habit.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_HABIT_NAME = "name";

        /**
         * Streak of the habit.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_HABIT_STREAK = "streak";

        /**
         * Any Comments.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_HABIT_COMMENTS = "comments";
    }
}
