package com.example.command_work_1511;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class PasswordAdapter extends CursorAdapter {

    public PasswordAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Получаем элементы из разметки элемента списка (название и сайт)
        TextView titleView = view.findViewById(R.id.text_view_title); // Для названия
        TextView siteView = view.findViewById(R.id.text_view_site);   // Для сайта

        // Получаем данные из курсора
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String site = cursor.getString(cursor.getColumnIndex("site"));

        // Устанавливаем данные в соответствующие TextView
        titleView.setText(title);  // Название
        siteView.setText(site);    // Сайт
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Создаем новый элемент списка, используя разметку item_password.xml
        return LayoutInflater.from(context).inflate(R.layout.item_password, parent, false);
    }
}
