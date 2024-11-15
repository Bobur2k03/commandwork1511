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
    private PasswordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);

        dbHelper = new DatabaseHelper(this);
        ListView passwordList = findViewById(R.id.password_list);
        Button addButton = findViewById(R.id.btn_add);

        // Инициализация адаптера
        adapter = new PasswordAdapter(this, null);
        passwordList.setAdapter(adapter);

        // Обновление списка паролей
        updatePasswordList();

        // Обработка кнопки добавления
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(PasswordListActivity.this, AddPasswordActivity.class);
            startActivity(intent);
        });

        // Обработка клика по элементу списка
        passwordList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(PasswordListActivity.this, PasswordDetailActivity.class);
            intent.putExtra("password_id", id); // Передаем ID пароля в новую активность
            startActivity(intent);
        });
    }

    // Метод для обновления списка паролей
    private void updatePasswordList() {
        Cursor cursor = dbHelper.getAllPasswords();

        if (cursor != null && cursor.getCount() > 0) {
            adapter.changeCursor(cursor); // Обновляем адаптер с новым курсором
        } else {
            adapter.changeCursor(null); // Очищаем адаптер, если данных нет
            Toast.makeText(this, "Нет сохраненных паролей", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        updatePasswordList();
        // Проверка, если нужно показать Toast
        if (getIntent().getBooleanExtra("password_deleted", false)) {
            Toast.makeText(this, "Пароль удален", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.changeCursor(null); // Закрываем курсор в адаптере
        }
    }


}
