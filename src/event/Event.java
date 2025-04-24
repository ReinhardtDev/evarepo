package event;

import ticketservice.Ticket;

import java.time.LocalDate;
import java.util.ArrayList;

public class Event {

    public Event(long id, String title, String location, LocalDate date, int quota) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
        this.quota = quota;
        this.tickets = new ArrayList<>();
    }

    public Event(String title, String place, LocalDate date, int quota) {
        this.title = title;
        this.location = place;
        this.date = date;
        this.quota = quota;
        this.tickets = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' + ",\n" +
                "place='" + location + '\'' + ",\n" +
                "date=" + date + ",\n" +
                "quota=" + quota + ",\n" +
                "id=" + id;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    private long id;

    private String title;

    private String location;

    private LocalDate date;

    private int quota;

    private ArrayList<Ticket> tickets;

}