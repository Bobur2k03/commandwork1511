package com.example.command_work_1511;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SetPinActivity extends AppCompatActivity {

    private EditText pinInput;
    private EditText confirmPinInput;
    private Button setPinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);  // Используем ресурс activity_set_pin из вашего проекта

        pinInput = findViewById(R.id.pin_input);  // Используем правильный ID для EditText
        confirmPinInput = findViewById(R.id.pin_confirm_input);  // Для второго ввода PIN
        setPinButton = findViewById(R.id.btn_set_pin);  // Кнопка для установки PIN

        setPinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pin = pinInput.getText().toString();
                String confirmPin = confirmPinInput.getText().toString();

                // Проверяем, что PIN совпадает при вводе
                if (!pin.isEmpty() && !confirmPin.isEmpty()) {
                    if (pin.equals(confirmPin)) {
                        // Хэшируем PIN-код перед сохранением
                        String hashedPin = EncryptionUtils.hashPassword(pin);

                        // Сохраняем PIN в SharedPreferences
                        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("hashed_pin", hashedPin);
                        editor.apply();

                        // Переходим на экран ввода PIN-кода
                        Toast.makeText(SetPinActivity.this, "PIN установлен", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SetPinActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SetPinActivity.this, "ПИН-коды не совпадают", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SetPinActivity.this, "Пожалуйста, введите ПИН-код", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
