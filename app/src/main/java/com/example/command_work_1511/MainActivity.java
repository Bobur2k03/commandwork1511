package com.example.command_work_1511;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText pinInput;
    private Button loginButton;
    private Button forgotPinButton; // Кнопка для восстановления PIN
    private int failedAttempts = 0; // Счётчик неудачных попыток
    private DatabaseHelper dbHelper; // Для работы с базой данных

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pinInput = findViewById(R.id.pin_input);
        loginButton = findViewById(R.id.btn_login);
        forgotPinButton = findViewById(R.id.btn_forgot_pin);

        // Инициализируем helper для работы с базой данных
        dbHelper = new DatabaseHelper(this);

        // Проверяем, установлен ли ПИН-код
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        if (prefs.getString("hashed_pin", null) == null) {
            // Если ПИН-код не установлен, перенаправляем пользователя на установку ПИН-кода
            Intent intent = new Intent(MainActivity.this, SetPinActivity.class);
            startActivity(intent);
            finish(); // Закрываем текущую активность
        }

        loginButton.setOnClickListener(v -> {
            String enteredPin = pinInput.getText().toString();
            if (checkPin(enteredPin)) {
                // Переходим к списку паролей
                Intent intent = new Intent(MainActivity.this, PasswordListActivity.class);
                startActivity(intent);
                finish(); // Закрываем текущую активность
            } else {
                failedAttempts++; // Увеличиваем счетчик неудачных попыток
                Toast.makeText(this, "Неверный ПИН-код", Toast.LENGTH_SHORT).show();

                // Если попытки ввода неверные, показываем кнопку "Забыли пароль?"
                if (failedAttempts >= 3) {
                    forgotPinButton.setVisibility(Button.VISIBLE);
                }
            }
        });

        forgotPinButton.setOnClickListener(v -> {
            // Показываем диалоговое окно с подтверждением удаления PIN-кода и паролей
            new AlertDialog.Builder(this)
                    .setTitle("Вы уверены?")
                    .setMessage("Внимание! Все сохраненные пароли будут удалены. Вы уверены, что хотите продолжить?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Очистим PIN-код и все сохраненные пароли
                            clearPin();
                            clearPasswords();

                            // Перенаправим пользователя на установку нового PIN-кода
                            Intent intent = new Intent(MainActivity.this, SetPinActivity.class);
                            startActivity(intent);
                            finish(); // Закрываем текущую активность
                        }
                    })
                    .setNegativeButton("Отмена", null)
                    .show();
        });
    }

    // Метод для сохранения ПИН-кода
    private void savePin(String pin) {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String hashedPin = EncryptionUtils.hashPassword(pin);
        editor.putString("hashed_pin", hashedPin);
        editor.apply();
        Log.d("PIN", "Сохраненный ПИН-код: " + hashedPin);
    }

    // Метод для проверки ПИН-кода
    private boolean checkPin(String enteredPin) {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String savedHashedPin = prefs.getString("hashed_pin", null);
        if (savedHashedPin == null) {
            Toast.makeText(this, "ПИН-код не установлен", Toast.LENGTH_SHORT).show();
            return false;
        }
        String enteredHashedPin = EncryptionUtils.hashPassword(enteredPin);
        Log.d("PIN", "Введенный ПИН-код (хэш): " + enteredHashedPin);
        return savedHashedPin.equals(enteredHashedPin);
    }

    // Метод для очистки сохраненного ПИН-кода
    private void clearPin() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("hashed_pin");
        editor.apply();
    }

    // Метод для удаления всех сохраненных паролей
    private void clearPasswords() {
        dbHelper.deleteAllPasswords();
    }
}
