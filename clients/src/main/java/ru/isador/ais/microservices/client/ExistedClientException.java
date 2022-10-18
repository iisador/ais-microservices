package ru.isador.ais.microservices.client;

public class ExistedClientException extends RuntimeException {

    private final String login;

    public ExistedClientException(String login) {
        super(null, null, false, false);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
