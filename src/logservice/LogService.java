package logservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogService implements LogServiceInterface {
    private static LogService instance;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String LOG_FILE = "log.txt";
    private static final FileWriter writer;
    private static final BufferedWriter bufferedWriter;

    static {
        try {
            writer = new FileWriter(LOG_FILE, true);
            bufferedWriter = new BufferedWriter(writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private LogService() {

    }

    public static synchronized LogService getInstance() {
        if (instance == null) {
            instance = new LogService();
        }
        return instance;
    }

    public void logEvent(String eventType, Long eventId) {
        String logMessage = formatLogMessage(eventType, eventId);
        try {
            bufferedWriter.write(logMessage + System.lineSeparator());
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private String formatLogMessage(String eventType, Long eventId) {
        return String.format("[%s] Thread-ID: %d | Event: %s | ID: %s",
                LocalDateTime.now().format(formatter),
                Thread.currentThread().threadId(),
                eventType,
                eventId != null ? eventId : "N/A");
    }
}