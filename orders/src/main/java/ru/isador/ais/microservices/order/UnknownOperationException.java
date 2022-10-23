package ru.isador.ais.microservices.order;

public class UnknownOperationException extends RuntimeException {

    private final String code;

    public UnknownOperationException(String code) {
        super(null, null, false, false);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
