package ru.isador.ais.microservices.client;

public class UserNotFoundException extends RuntimeException {

    private final String login;

    public UserNotFoundException(String login) {
        super(null, null, false, false);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
