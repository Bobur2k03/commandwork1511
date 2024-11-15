package com.example.command_work_1511;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditPasswordActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText titleInput, siteInput, loginInput, passwordInput;
    private Button saveButton;
    private long passwordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        dbHelper = new DatabaseHelper(this);

        // Инициализация UI
        titleInput = findViewById(R.id.title_input);
        siteInput = findViewById(R.id.site_input);
        loginInput = findViewById(R.id.login_input);
        passwordInput = findViewById(R.id.password_input);
        saveButton = findViewById(R.id.btn_save);

        // Получаем ID пароля, который будем редактировать
        passwordId = getIntent().getLongExtra("password_id", -1);

        // Загружаем текущие данные пароля
        loadPasswordForEdit();

        // Обработчик кнопки для сохранения изменений
        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String site = siteInput.getText().toString();
            String login = loginInput.getText().toString();
            String password = passwordInput.getText().toString();

            // Обновляем пароль в базе данных
            dbHelper.updatePassword(passwordId, title, site, login, password);
            Toast.makeText(this, "Пароль обновлен", Toast.LENGTH_SHORT).show();
            finish();  // Возвращаемся назад
        });
    }

    // Метод для загрузки текущих данных пароля в поля редактирования
    private void loadPasswordForEdit() {
        Password password = dbHelper.getPasswordById(passwordId);
        if (password != null) {
            titleInput.setText(password.getTitle());
            siteInput.setText(password.getSite());
            loginInput.setText(password.getLogin());
            passwordInput.setText(password.getPassword());
        }
    }
}
