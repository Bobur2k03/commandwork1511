package com.example.command_work_1511;

public class Password {
    private long id;  // Уникальный идентификатор
    private String title;  // Название
    private String site;   // Сайт
    private String login;  // Логин
    private String password; // Пароль

    // Конструктор с id
    public Password(String title, String site, String login, String password) {
        this.id = id;
        this.title = title;
        this.site = site;
        this.login = login;
        this.password = password;
    }

    // Геттеры
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSite() {
        return site;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
