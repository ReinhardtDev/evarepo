import java.util.ArrayList;

public class Main { //Haupt Klasse von der der Code läuft

    public static void main(String[] args) { //Main Methode, die einen Array von Strings nimmt
        ArrayList<Event> events = new ArrayList<>();
        Event event = new Event(1,"Event 1", "Leipzig", "11.11.2026", 27);
        Event event2 = new Event(2,"Event 2", "Leipzig", "34.8.3012", 68);
        Event event3 = new Event(3,"Event 3", "Dresden", "17.05.1002", 0);
        /*System.out.println(event);
        System.out.println(event2);
        System.out.println(event3); */
        ArrayList<Event> eventss = new ArrayList<Event>();

        EventService test = new EventService(eventss);
        test.createEvent(event);
        test.readEvent(0);

        events.add(event);
        events.add(event2);
        events.add(event3);

    }
}