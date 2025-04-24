package kundenservice;

import ticketservice.Ticket;

import java.time.LocalDate;
import java.util.ArrayList;

public class Kunde {

    private long id;
    private String nutzername;
    private String email;
    private LocalDate geburtsdatum;
    private ArrayList<Ticket> tickets;

    public Kunde(long id, String nutzername, String email, LocalDate geburtsdatum){
        this.id = id;
        this.nutzername = nutzername;
        this.email = email;
        this.geburtsdatum = geburtsdatum;
        this.tickets = new ArrayList<>();
    }

    public long getId() {
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

    public boolean isMaxTicketAmount(long eventId) {
        int count = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getEventId() == eventId) {
                count++;
            }
        }
        return count >= 5;
    }

    @Override
    public String toString(){
        return "username='" + this.nutzername + '\'' + ",\n" +
                "email='" + this.email + '\'' + ",\n" +
                "birthdate=" + this.geburtsdatum + ",\n" +
                "id=" + id;    }
}
