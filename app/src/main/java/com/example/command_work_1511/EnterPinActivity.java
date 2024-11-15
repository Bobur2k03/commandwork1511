package com.example.command_work_1511;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnterPinActivity extends AppCompatActivity {

    private EditText pinEditText;
    private Button confirmButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);

        pinEditText = findViewById(R.id.pinEditText);
        confirmButton = findViewById(R.id.confirmButton);
        sharedPreferences = getSharedPreferences("appPrefs", Context.MODE_PRIVATE);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPin = pinEditText.getText().toString();
                String savedPin = sharedPreferences.getString("userPin", "");

                if (enteredPin.equals(savedPin)) {
                    // Если PIN правильный, переходим в основную активность
                    startMainActivity();
                } else {
                    // Если PIN неверный, показываем сообщение
                    Toast.makeText(EnterPinActivity.this, "Неверный PIN", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startMainActivity() {
        Intent intent = new Intent(EnterPinActivity.this, MainActivity.class);
        startActivity(intent);
        finish();  // Закрываем EnterPinActivity
    }
}
