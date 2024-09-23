package OmisFax.OmiStories.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Objects;

@Entity
public class Utente {
    //ID: username, non possono esistere due utenti con lo stesso username
    @Id
    private String username;
    private String password;
    private boolean premium; //variabile di stato


    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
        this.premium = false;
    }

    public Utente(String username, String password, boolean premium) {
        this.username = username;
        this.password = password;
        this.premium = premium;
    }

    public Utente() {
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPremium() {
        return premium;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return premium == utente.premium && Objects.equals(username, utente.username) && Objects.equals(password, utente.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, premium);
    }

    @Override
    public String toString() {
        return "{" +
                "\"username\":"+ username + '\"' +
                ", password:\"" + password + '\"' +
                ", premium:\"" + premium +
                "\"}";
    }

    public void toPremium(){
        premium = true;
    }

    public String getId() {
        return username;
    }
}
