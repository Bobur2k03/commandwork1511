package com.example.command_work_1511;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddPasswordActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText titleEditText, siteEditText, loginEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);  // Убедитесь, что файл activity_add_password.xml существует

        dbHelper = new DatabaseHelper(this);

        // Получаем элементы по правильным ID
        titleEditText = findViewById(R.id.edit_title);  // Правильный ID
        siteEditText = findViewById(R.id.edit_site);  // Правильный ID
        loginEditText = findViewById(R.id.edit_login);  // Правильный ID
        passwordEditText = findViewById(R.id.edit_password);  // Правильный ID

        // Находим кнопку и устанавливаем обработчик нажатия
        Button saveButton = findViewById(R.id.btn_save);  // Правильный ID
        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String site = siteEditText.getText().toString();
            String login = loginEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Сохраняем новый пароль в базу данных
            dbHelper.addPassword(title, site, login, password);
            finish();  // Закрываем текущую активность
        });
    }
}
