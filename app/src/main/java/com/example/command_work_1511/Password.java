package com.example.command_work_1511;
public class Password {
    private String title;
    private String site;
    private String login;
    private String password;

    // Конструктор
    public Password(String title, String site, String login, String password) {
        this.title = title;
        this.site = site;
        this.login = login;
        this.password = password;
    }

    // Геттеры
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
