package ru.isador.ais.microservices.client;

public class ClientNotFoundException extends RuntimeException {

    private final String login;

    public ClientNotFoundException(String login) {
        super(null, null, false, false);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
