package model;

// Represents a printer for printing event log on console
public class EventLogPrinter {
    public void printLog(EventLog eventLog) {
        for (Event event : eventLog) {
            System.out.println(event.toString() + "\n\n");
        }
    }
}
