public class Events {

    public String bezeichnung;
    public String ort;
    public String datum;
    public int kontingent;

    public Events(String bezeichnung, String ort, String datum, int kontingent) {
        this.bezeichnung = bezeichnung;
        this.ort = ort;
        this.datum = datum;
        this.kontingent = kontingent;
    }

    @Override
    public String toString(){
        return bezeichnung + ":"
                + "\n Datum: " + datum
                + "\n Ort: " + ort
                + "\n Ticketkontingent: " + kontingent;
    }
}
