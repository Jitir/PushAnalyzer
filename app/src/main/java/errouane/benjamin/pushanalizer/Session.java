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

    private Session() {}

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

    public void reset() {
        duration = 0;
        distance = 0;
        pushes = 0;
        speeds.clear();
        times.clear();
    }

    private static Session instance;
    public static Session getInstance() {
        if(instance == null)
            instance = new Session();

        return instance;
    }

    public String toReadableString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Duration: ");
        sb.append(duration);
        sb.append("\nDistance: ");
        sb.append(distance);
        sb.append("\nPushes: ");
        sb.append(pushes);
        sb.append("\n\nData (Time, Speed):");

        for(int i = 0; i < speeds.size(); i++) {
            sb.append("\n");
            sb.append(times.get(i));
            sb.append("\t");
            sb.append(speeds.get(i));
        }

        return sb.toString();
    }

    public String toBinaryString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < speeds.size(); i++) {
            sb.append(times.get(i));
            sb.append("\t");
            sb.append(speeds.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }
}