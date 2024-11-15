package com.example.command_work_1511;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText pinInput;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Проверяем, установлен ли PIN-код
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        if (!prefs.contains("hashed_pin")) {
            // Если PIN не установлен, переходим на экран установки PIN
            Intent intent = new Intent(this, SetPinActivity.class);
            startActivity(intent);
            finish();  // Закрываем MainActivity, чтобы не возвращаться на него
            return;
        }

        setContentView(R.layout.activity_main);  // Используем ресурс activity_main из вашего проекта

        pinInput = findViewById(R.id.pin_input);  // Используем правильный ID для EditText
        loginButton = findViewById(R.id.btn_login);  // Используем правильный ID для кнопки

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPin = pinInput.getText().toString();
                String hashedPin = EncryptionUtils.hashPassword(enteredPin);
                if (checkPin(hashedPin)) {
                    // Если PIN правильный, переходим на основной экран
                    Intent intent = new Intent(MainActivity.this, PasswordListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Неверный ПИН-код", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Проверка введенного ПИН-кода
    private boolean checkPin(String hashedPin) {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String storedPin = prefs.getString("hashed_pin", null);
        return storedPin != null && storedPin.equals(hashedPin);
    }
}
