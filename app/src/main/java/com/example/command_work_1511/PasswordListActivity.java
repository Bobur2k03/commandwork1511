package com.example.command_work_1511;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PasswordListActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);

        dbHelper = new DatabaseHelper(this);
        ListView passwordList = findViewById(R.id.password_list);
        Button addButton = findViewById(R.id.btn_add);

        // Переход к активности добавления пароля
        addButton.setOnClickListener(v -> {
            startActivity(new Intent(PasswordListActivity.this, AddPasswordActivity.class));
        });

        // Получаем все пароли из базы данных
        Cursor cursor = dbHelper.getAllPasswords();

        // Создаем адаптер с курсором и устанавливаем его для ListView
        PasswordAdapter adapter = new PasswordAdapter(this, cursor);
        passwordList.setAdapter(adapter);

        // Реакция на долгий клик для удаления или других действий
        passwordList.setOnItemLongClickListener((parent, view, position, id) -> {
            // В данном случае просто выводим название пароля в Toast
            cursor.moveToPosition(position);  // Переходим к нужной позиции
            String title = cursor.getString(cursor.getColumnIndex("title"));
            Toast.makeText(PasswordListActivity.this, "Долгое нажатие на: " + title, Toast.LENGTH_SHORT).show();
            return true;  // Возвращаем true, чтобы обработать долгий клик
        });
    }
}
