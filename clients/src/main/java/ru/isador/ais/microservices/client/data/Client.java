package ru.isador.ais.microservices.client.data;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String roles;

    @Column
    private String login;

    @Column
    private double bonuses;

    public Client() {
        bonuses = 0.0;
    }

    public Client(String name, String password, String login, String... roles) {
        this.name = name;
        this.password = password;
        this.login = login;
        this.roles = String.join(",", roles);
        bonuses = 0.0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void addRole(String role) {
        if (role != null) {
            if (roles == null) {
                roles = role;
            } else {
                roles += "," + role;
            }
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public double getBonuses() {
        return bonuses;
    }

    public void setBonuses(double bonuses) {
        this.bonuses = bonuses;
    }

    public void incBonuses(double bonus) {
        bonuses += bonus;
    }
}
