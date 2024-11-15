package com.example.command_work_1511;

import android.content.Context;
import android.content.ContentValues; // Добавьте этот импорт
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Имя базы данных и версия
    private static final String DATABASE_NAME = "passwords_db";
    private static final int DATABASE_VERSION = 1;

    // Название таблицы и столбцы
    private static final String TABLE_PASSWORDS = "passwords";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SITE = "site";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "password";

    // SQL-запрос для создания таблицы
    private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_PASSWORDS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_SITE + " TEXT, " +
            COLUMN_LOGIN + " TEXT, " +
            COLUMN_PASSWORD + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создаем таблицу
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Если база данных обновляется, удаляем старую таблицу и создаем новую
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORDS);
        onCreate(db);
    }

    // Метод для получения всех паролей
    public Cursor getAllPasswords() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PASSWORDS, null);  // Возвращаем курсор с данными
    }

    // Добавление нового пароля в базу
    public long addPassword(String title, String site, String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Создаем ContentValues для вставки данных
        ContentValues values = new ContentValues();  // Теперь ContentValues доступен
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SITE, site);
        values.put(COLUMN_LOGIN, login);
        values.put(COLUMN_PASSWORD, password);

        // Вставляем строку в таблицу и возвращаем ее ID
        return db.insert(TABLE_PASSWORDS, null, values);
    }
}
