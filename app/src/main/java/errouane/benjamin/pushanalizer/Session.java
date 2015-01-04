package errouane.benjamin.pushanalizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benni on 07.11.2014.
 */
public class Session {
    private float duration = 0;
    private float distance = 0;
    private List<Float> speeds = new ArrayList<Float>();
    private List<Float> times = new ArrayList<Float>();
    private List<Float> pushes = new ArrayList<Float>();
    private List<Integer> accelerometerX = new ArrayList<Integer>();
    private List<Integer> accelerometerY = new ArrayList<Integer>();
    private List<Integer> accelerometerZ = new ArrayList<Integer>();

    private Session() {}

    public void addValues(float deltaTime, float speed, int[] acc) {
        duration += deltaTime;
        times.add(duration);
        speeds.add(speed);
        accelerometerX.add(acc[0]);
        accelerometerY.add(acc[1]);
        accelerometerZ.add(acc[2]);
    }

    public List<Float> getTimes() {
        return times;
    }

    public List<Float> getSpeeds() {
        return speeds;
    }
/*
    public List<Float> getPushes() {
        return pushes;
    }

    public List<Float> getAccelerometerX() {
        return accelerometerX;
    }

    public List<Float> getAccelerometerY() {
        return accelerometerY;
    }

    public List<Float> getAccelerometerZ() {
        return accelerometerZ;
    }

    public float distancePerPush() {
        return distance / (float)pushes.size();
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

    public int getPushesCount() {
        return pushes.size();
    }
*/
    public void addDistance(float value) {
        distance += value;
    }

    public void addPush() {
        pushes.add(times.get(times.size() - 1));
    }

    public void reset() {
        duration = 0;
        distance = 0;
        pushes.clear();
        speeds.clear();
        times.clear();
        accelerometerX.clear();
        accelerometerY.clear();
        accelerometerZ.clear();
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
        sb.append("\n\nData (Time, Speed, Acc[x], Acc[y], Acc[z]):");

        for(int i = 0; i < speeds.size(); i++) {
            sb.append("\n");
            sb.append(times.get(i));
            sb.append("\t");
            sb.append(speeds.get(i));
            sb.append("\t");
            sb.append(accelerometerX.get(i));
            sb.append("\t");
            sb.append(accelerometerY.get(i));
            sb.append("\t");
            sb.append(accelerometerZ.get(i));
        }

        sb.append("\n\nPushes: ");
        sb.append(pushes.size());

        for(int i = 0; i < pushes.size(); i++) {
            sb.append("\n");
            sb.append(pushes.get(i));
        }

        return sb.toString();
    }

    public String toSimpleString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < speeds.size(); i++) {
            sb.append(times.get(i));
            sb.append("\t");
            sb.append(speeds.get(i));
            sb.append("\t");
            sb.append(accelerometerX.get(i));
            sb.append("\t");
            sb.append(accelerometerY.get(i));
            sb.append("\t");
            sb.append(accelerometerZ.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }
}