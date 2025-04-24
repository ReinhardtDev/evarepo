import idservice.IDService;

public class Main {
    public static void main(String[] args) {
        IDService idService = new IDService();
        Client client = new Client(idService);
        client.readUserChoice();
    }
}