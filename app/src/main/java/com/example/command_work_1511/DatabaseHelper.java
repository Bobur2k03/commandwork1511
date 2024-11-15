package com.example.command_work_1511;
import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Имя базы данных и версия
    private static final String DATABASE_NAME = "passwords_db";
    private static final int DATABASE_VERSION = 1;

    // Название таблицы и столбцы
    public static final String TABLE_PASSWORDS = "passwords";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SITE = "site";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";

    // Конструктор
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Создание таблицы при первом запуске базы данных
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PASSWORDS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_SITE + " TEXT, " +
                COLUMN_LOGIN + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTable);
    }

    // Обновление базы данных (например, при изменении версии)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаление старой таблицы и создание новой
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORDS);
        onCreate(db);
    }

    // Получение всех паролей
    public Cursor getAllPasswords() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Запрос для получения всех данных
        return db.rawQuery("SELECT id AS _id, title, site, login, password FROM " + TABLE_PASSWORDS, null);
    }

    // Добавление нового пароля
    public long addPassword(String title, String site, String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SITE, site);
        values.put(COLUMN_LOGIN, login);
        values.put(COLUMN_PASSWORD, password);
        return db.insert(TABLE_PASSWORDS, null, values);
    }

    // Удаление всех паролей
    public void deleteAllPasswords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PASSWORDS, null, null);
    }

    // Обновление пароля по ID
    public int updatePassword(long id, String title, String site, String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SITE, site);
        values.put(COLUMN_LOGIN, login);
        values.put(COLUMN_PASSWORD, password);
        return db.update(TABLE_PASSWORDS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Получение пароля по ID
    public Password getPasswordById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PASSWORDS, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Получаем индексы столбцов с проверкой на отрицательные значения
            int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
            int siteIndex = cursor.getColumnIndex(COLUMN_SITE);
            int loginIndex = cursor.getColumnIndex(COLUMN_LOGIN);
            int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);

            // Логирование, если один из индексов равен -1, это значит, что столбец не найден
            if (titleIndex == -1 || siteIndex == -1 || loginIndex == -1 || passwordIndex == -1) {
                Log.e("DatabaseHelper", "One or more columns not found!");
                cursor.close();
                return null;
            }

            // Если индексы валидны (больше или равны 0), извлекаем данные
            String title = cursor.getString(titleIndex);
            String site = cursor.getString(siteIndex);
            String login = cursor.getString(loginIndex);
            String password = cursor.getString(passwordIndex);
            cursor.close();

            return new Password(title, site, login, password); // Возвращаем объект Password
        }
        cursor.close();
        return null; // Если курсор пуст, возвращаем null
    }
    public Cursor getAllPasswordsCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                "passwords",         // Название таблицы
                new String[]{"title", "site"},  // Только нужные столбцы
                null,                // WHERE условие (null, если не нужно)
                null,                // Аргументы для WHERE
                null,                // GROUP BY
                null,                // HAVING
                null                 // ORDER BY
        );
    }
    public boolean deletePasswordById(long passwordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_PASSWORDS, "_id = ?", new String[]{String.valueOf(passwordId)});
        db.close();
        return rowsDeleted > 0;  // Если удалено хотя бы 1 строка, возвращаем true
    }
    public boolean deletePassword(long passwordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete("passwords", "id = ?", new String[]{String.valueOf(passwordId)});
        return rowsAffected > 0;
    }

}
