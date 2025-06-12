package ticketservice;

import java.time.LocalDate;

public class Ticket {
    private long id;
    private LocalDate purchaseDate;
    private long customerId;
    private long eventId;

    public Ticket(long id, LocalDate purchaseDate, long customerId, long eventId) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.customerId = customerId;
        this.eventId = eventId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "ticket id='" + this.id + '\'' +
                ", purchase date='" + this.purchaseDate + '\'' +
                ", customer id=" + this.customerId +
                ", event id=" + id;     }
}
