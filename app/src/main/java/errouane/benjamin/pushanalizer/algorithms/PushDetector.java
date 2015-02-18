package errouane.benjamin.pushanalizer.algorithms;

import java.util.List;

import errouane.benjamin.pushanalizer.activities.MainTabbedActivity;
import errouane.benjamin.pushanalizer.session.Session;
import errouane.benjamin.pushanalizer.session.SessionFunctions;

/**
 * Created by Benni on 17.02.2015.
 */
public class PushDetector {
    private int movingAverageSize;
    private MainTabbedActivity mainTabbedActivity;
    private final float PUSH_THRESHOLD = 8.5f;
    private boolean wasPushing = false;

    public PushDetector(MainTabbedActivity mainTabbedActivity, int movingAverageSize) {
        this.mainTabbedActivity = mainTabbedActivity;
        this.movingAverageSize = movingAverageSize;
    }

    public void update() {
        if(isPushing()) {
            mainTabbedActivity.pushRegistered();
        }
    }

    private boolean isPushing() {
        List<Float> speeds = SessionFunctions.getLastElements(Session.getInstance().getSpeeds(), 5);
        List<Float> times = SessionFunctions.getLastElements(Session.getInstance().getTimes(), 5);
        List<Float> acceleration = SessionFunctions.differentiate(speeds, times);
        acceleration = SessionFunctions.clamp(acceleration, -30, 30);

        float value = SessionFunctions.average(acceleration, movingAverageSize);
        if(value > PUSH_THRESHOLD) {
            if(!wasPushing) {
                wasPushing = true;
                return true;
            }
        } else {
            wasPushing = false;
        }
        return false;
    }
}
