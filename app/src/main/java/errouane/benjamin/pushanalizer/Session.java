package errouane.benjamin.pushanalizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benni on 07.11.2014.
 */
public class Session {
    private float duration;
    private float distance;
    private int pushes;
    private List<Float> speeds = new ArrayList<Float>();
    private List<Float> times = new ArrayList<Float>();


    public List<Float> getTimes() {
        return times;
    }

    public List<Float> getSpeeds() {
        return speeds;
    }

    public float distancePerPush() {
        return distance / (float)pushes;
    }

    public float averageSpeed() {
        return distance / duration;
    }

    public float getDuration() {
        return duration;
    }

    public float getDistance() {
        return distance;
    }

    public int getPushes() {
        return pushes;
    }

    public void addDistance(float value) {
        distance += value;
    }

    public void addDuration(float value) {
        duration += value;
    }

    public void addPush() {
        pushes++;
    }
}