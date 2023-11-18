package com.qualle.truegain.client.api;

public class NewRegistration {

    private String name;
    private String birthday;
    private String gender;
    private String login;
    private String email;
    private String password;

    public NewRegistration() {
    }

    public NewRegistration(String name, String birthday, String gender, String login, String email, String password) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
