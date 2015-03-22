package errouane.benjamin.pushanalizer.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import errouane.benjamin.pushanalizer.algorithms.BrakeDetector;

/**
 * Created by Benni on 07.11.2014.
 */
public class Session extends Observable {
    private float duration = 0;
    private float distance = 0;
    private float topSpeed = 0;
    private float distancePerPush = 0;
    private List<Float> speeds = new ArrayList<Float>();
    private List<Float> times = new ArrayList<Float>();
    private List<Float> pushes = new ArrayList<Float>();
    private List<Float> accelerometerX = new ArrayList<Float>();
    private List<Float> accelerometerY = new ArrayList<Float>();
    private List<Float> accelerometerZ = new ArrayList<Float>();
    private List<BrakeDetector.BrakeData> brakes = new ArrayList<BrakeDetector.BrakeData>();

    private Session() {}

    public void addValues(float deltaTime, float speed, int[] acc, float distance) {
        duration += deltaTime;
        times.add(duration);
        speeds.add(speed);
        this.distance += distance;
        if(speed > topSpeed) {
            topSpeed = speed;
        }
        if(!pushes.isEmpty())
            distancePerPush = this.distance * 1000f / (float)pushes.size();

        accelerometerX.add((float) ((acc[0] / Math.pow(2,15))) * 15);
        accelerometerY.add((float) ((acc[1] / Math.pow(2,15))) * 15);
        accelerometerZ.add((float) ((acc[2] / Math.pow(2,15))) * 15);
    }

    public List<Float> getPushes() {
        return pushes;
    }

    public float getTopSpeed() {
        return topSpeed;
    }

    public float getDistancePerPush() {
        return distancePerPush;
    }

    public float getDistance() {
        return distance;
    }

    public float getDuration() {
        return duration;
    }

    public List<Float> getTimes() {
        return times;
    }

    public List<Float> getSpeeds() {
        return speeds;
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

    public void addPush() {
        pushes.add(times.get(times.size() - 1));
    }

    public List<BrakeDetector.BrakeData> getBrakes() {
        return brakes;
    }

    public void reset() {
        duration = 0;
        distance = 0;
        topSpeed = 0;
        distancePerPush = 0;
        pushes.clear();
        speeds.clear();
        times.clear();
        accelerometerX.clear();
        accelerometerY.clear();
        accelerometerZ.clear();
        brakes.clear();
        hasChanged();
        notifyObservers();
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