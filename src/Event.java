public class Event {

    public int id;
    public String bezeichnung;
    public String ort;
    public String datum;
    public int kontingent;

    public Event(int  id, String bezeichnung, String ort, String datum, int kontingent) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.ort = ort;
        this.datum = datum;
        this.kontingent = kontingent;
    }

    public int getId() {
        return id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public String getOrt() {
        return ort;
    }

    public String getDatum() {
        return datum;
    }

    public int getKontingent(){
        return kontingent;
    }

    @Override
    public String toString(){
        return bezeichnung + ":"
                + "\n Datum: " + datum
                + "\n Ort: " + ort
                + "\n Ticketkontingent: " + kontingent;
    }
}
