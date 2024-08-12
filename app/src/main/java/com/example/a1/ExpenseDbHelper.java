package com.example.a1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpenseDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expenses.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_NAME = "expenses";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_MESSAGE = "message";

    // Câu lệnh SQL để tạo bảng
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT, " +
                    COLUMN_AMOUNT + " REAL, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_MESSAGE + " TEXT);";

    public ExpenseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Hàm để thêm chi phí vào database
    public void addExpense(String date, String category, double amount, String title, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_MESSAGE, message);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}
