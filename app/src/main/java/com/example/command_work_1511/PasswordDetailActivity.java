package com.example.command_work_1511;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PasswordDetailActivity extends AppCompatActivity {

    private EditText passwordEditText;
    private ImageView eyeIcon;
    private TextView titleTextView;
    private TextView siteTextView;
    private TextView loginTextView;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_detail);

        passwordEditText = findViewById(R.id.edit_text_password);
        eyeIcon = findViewById(R.id.eye_icon);
        titleTextView = findViewById(R.id.text_view_title);
        siteTextView = findViewById(R.id.text_view_site);
        loginTextView = findViewById(R.id.text_view_login);

        // Получаем данные из Intent
        long passwordId = getIntent().getLongExtra("password_id", -1);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Password password = dbHelper.getPasswordById(passwordId);

        if (password != null) {
            titleTextView.setText("Название: " + password.getTitle());
            siteTextView.setText("Сайт: " + password.getSite());
            loginTextView.setText("Логин: " + password.getLogin());

            passwordEditText.setText(password.getPassword());
            passwordEditText.setSelection(password.getPassword().length()); // Устанавливаем курсор в конец текста
            passwordEditText.setInputType(129); // Скрываем пароль
        }

        eyeIcon.setOnClickListener(v -> togglePasswordVisibility());

        // Иконка для удаления пароля
        ImageView deleteIcon = findViewById(R.id.delete_icon);
        deleteIcon.setOnClickListener(v -> showPinDialog(passwordId));
    }

    private void showPinDialog(final long passwordId) {
        // Создаем диалог для ввода пин-кода
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Введите Пин-код");

        final EditText pinEditText = new EditText(this);
        pinEditText.setInputType(129); // Для ввода только чисел
        builder.setView(pinEditText);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredPin = pinEditText.getText().toString().trim();
                checkPinAndDeletePassword(enteredPin, passwordId);
            }
        });

        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

    private void checkPinAndDeletePassword(String enteredPin, long passwordId) {
        // Получаем сохраненный пин-код
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedPin = sharedPreferences.getString("PIN", null);

        // Логируем сохраненный пин-код и введенный пин-код для отладки
        Log.d("PasswordDetailActivity", "Saved PIN: " + savedPin);
        Log.d("PasswordDetailActivity", "Entered PIN: " + enteredPin);

        if (savedPin != null && savedPin.equals(enteredPin.trim())) {  // Убираем пробелы для точного сравнения
            deletePassword(passwordId);
        } else {
            Toast.makeText(this, "Неверный пин-код", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletePassword(long passwordId) {
        // Удаляем пароль из базы данных
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.deletePasswordById(passwordId);

        // Перенаправляем на главный экран с флагом "пароль удален"
        redirectToMainMenu();
    }

    private void redirectToMainMenu() {
        Intent intent = new Intent(PasswordDetailActivity.this, PasswordListActivity.class);
        intent.putExtra("password_deleted", true);  // Флаг для отображения сообщения об удалении
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // Очищаем стек
        startActivity(intent);
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordEditText.setInputType(129); // Скрываем пароль
            eyeIcon.setImageResource(R.drawable.ic_eye_off); // Иконка закрытого глаза
        } else {
            passwordEditText.setInputType(1); // Показываем пароль
            eyeIcon.setImageResource(R.drawable.ic_eye_on); // Иконка открытого глаза
        }
        isPasswordVisible = !isPasswordVisible;
    }
}
