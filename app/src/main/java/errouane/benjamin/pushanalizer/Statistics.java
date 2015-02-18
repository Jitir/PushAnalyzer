package errouane.benjamin.pushanalizer;

import java.util.ArrayList;
import java.util.List;

import errouane.benjamin.pushanalizer.session.Session;

/**
 * Created by Benni on 07.11.2014.
 */
public class Statistics {
    // Global vs Session

    // Duration
    // Distance
    // Speed
    // #Pushes
    // Meter/Push

    // Max Distance/Session
    // Avg Distance/Session
    // Max Speed
    // Avg Speed

    private List<Session> sessions = new ArrayList<Session>();

    public List<Session> getSessions() {
        return sessions;
    }
}
