package ru.isador.ais.microservices.client.web;

import java.io.Serializable;

public class SystemError implements Serializable {

    private final String title;
    private final String detail;

    public SystemError(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }
}
