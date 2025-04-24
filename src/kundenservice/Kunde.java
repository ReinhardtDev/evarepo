package kundenservice;

import java.time.LocalDate;

public class Kunde {

    private int id;
    private String nutzername;
    private String email;
    private LocalDate geburtsdatum;

    public Kunde(int id, String nutzername, String email, LocalDate geburtsdatum){
        this.id = id;
        this.nutzername = nutzername;
        this.email = email;
        this.geburtsdatum = geburtsdatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNutzername() {
        return nutzername;
    }

    public void setNutzername(String nutzername) {
        this.nutzername = nutzername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }
}
