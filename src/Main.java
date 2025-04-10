public class Main { //Haupt Klasse von der der Code läuft

    public static void main(String[] args) { //Main Methode, die einen Array von Strings nimmt
        Events event = new Events("Event 1", "Leipzig", "11.11.2026", 27);
        Events event2 = new Events("Event 2", "Leipzig", "34.8.3012", 68);
        Events event3 = new Events("Event 3", "Dresden", "17.05.1002", 0);
        System.out.println(event.Bezeichnung + ":" + "\n Datum: " + event.Datum + "\n Ort: " + event.Ort + "\n Ticketkontingent: " + event.Kontingent);
        System.out.println(event2.Bezeichnung + ":" + "\n Datum: " + event2.Datum + "\n Ort: " + event2.Ort + "\n Ticketkontingent: " + event2.Kontingent);
        System.out.println(event3.Bezeichnung + ":" + "\n Datum: " + event3.Datum + "\n Ort: " + event3.Ort + "\n Ticketkontingent: " + event3.Kontingent);

    }
}