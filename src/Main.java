public class Main { //Haupt Klasse von der der Code läuft

    public static void main(String[] args) { //Main Methode, die einen Array von Strings nimmt
        Events event = new Events("Event 1", "Leipzig", "11.11.2026", 27);
        Events event2 = new Events("Event 2", "Leipzig", "34.8.3012", 68);
        Events event3 = new Events("Event 3", "Dresden", "17.05.1002", 0);
        System.out.println(event.bezeichnung + ":" + "\n Datum: " + event.datum + "\n Ort: " + event.ort + "\n Ticketkontingent: " + event.kontingent);
        System.out.println(event2.bezeichnung + ":" + "\n Datum: " + event2.datum + "\n Ort: " + event2.ort + "\n Ticketkontingent: " + event2.kontingent);
        System.out.println(event3.bezeichnung + ":" + "\n Datum: " + event3.datum + "\n Ort: " + event3.ort + "\n Ticketkontingent: " + event3.kontingent);

    }
}