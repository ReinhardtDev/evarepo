public class Main { //Haupt Klasse von der der Code läuft

    public static void main(String[] args) { //Main Methode, die einen Array von Strings nimmt
        Event event1 = new Event(1, "Event 1", "Leipzig", "11.11.2026", 27);
        Event event2 = new Event(2, "Event 2", "Leipzig", "34.8.3012", 68);
        Event event3 = new Event(3, "Event 3", "Dresden", "17.05.1002", 0);
        System.out.println(event1.bezeichnung + ":" + "\n ID: " + event1.id + "\n Datum: " + event1.datum + "\n Ort: " + event1.ort + "\n Ticketkontingent: " + event1.kontingent);
        System.out.println(event2.bezeichnung + ":" + "\n ID: " + event2.id + "\n Datum: " + event2.datum + "\n Ort: " + event2.ort + "\n Ticketkontingent: " + event2.kontingent);
        System.out.println(event3.bezeichnung + ":" + "\n ID: " + event3.id + "\n Datum: " + event3.datum + "\n Ort: " + event3.ort + "\n Ticketkontingent: " + event3.kontingent);

    }
}