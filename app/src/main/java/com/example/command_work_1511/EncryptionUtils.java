package com.example.command_work_1511;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {

    // Хэширование пароля (PIN)
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();  // Преобразуем в строку шестнадцатиричных символов
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка при хэшировании", e);
        }
    }
}
