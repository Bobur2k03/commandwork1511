package com.example.command_work_1511;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CursorAdapter;

public class PasswordAdapter extends CursorAdapter {

    public PasswordAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);  // передаем контекст, курсор и флаг
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Создаем новый элемент списка
        return LayoutInflater.from(context).inflate(R.layout.item_password, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Привязываем данные из курсора к элементам списка
        TextView titleView = view.findViewById(R.id.item_title);
        TextView siteView = view.findViewById(R.id.item_site);
        TextView loginView = view.findViewById(R.id.item_login);
        TextView passwordView = view.findViewById(R.id.item_password);

        // Извлекаем данные из курсора, проверяя столбцы
        // Замените '_id' на 'id', который у вас есть в базе данных
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String site = cursor.getString(cursor.getColumnIndexOrThrow("site"));
        String login = cursor.getString(cursor.getColumnIndexOrThrow("login"));
        String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));

        // Привязываем данные к полям
        titleView.setText(title);
        siteView.setText(site);
        loginView.setText(login);
        passwordView.setText(password);  // В реальном приложении лучше маскировать пароли!
    }
}
