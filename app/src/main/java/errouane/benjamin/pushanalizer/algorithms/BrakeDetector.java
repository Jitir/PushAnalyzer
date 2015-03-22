package errouane.benjamin.pushanalizer.algorithms;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import errouane.benjamin.pushanalizer.activities.MainTabbedActivity;
import errouane.benjamin.pushanalizer.session.Session;
import errouane.benjamin.pushanalizer.session.SessionFunctions;

/**
 * Created by Benni on 21.02.2015.
 */
public class BrakeDetector {
    private int movingAverageSize;
    private MainTabbedActivity mainTabbedActivity;
    private final float BRAKE_THRESHOLD = -7f;
    private float startDistance;
    //private float decelerationSum = 0;
    private List<Float> decelerations = new ArrayList<Float>();
    //private int brakeLength = 0;

    public BrakeDetector(MainTabbedActivity mainTabbedActivity, int movingAverageSize) {
        this.mainTabbedActivity = mainTabbedActivity;
        this.movingAverageSize = movingAverageSize;
    }

    public void update() {
        BrakeValues values = isBraking();
        switch(values.type) {
            case START:
                decelerations.add(values.deceleration);
                startDistance = Session.getInstance().getDistance();
                break;

            case BRAKING:
                decelerations.add(values.deceleration);
                break;

            case END:
                if(decelerations.size() > 5) {
                    float distance = Session.getInstance().getDistance() - startDistance;
                    List<Float> speeds = SessionFunctions.getLastElements(Session.getInstance().getSpeeds(), decelerations.size() + 2);

                    BrakeData data = new BrakeData(distance, speeds.get(0), speeds.get(speeds.size() - 1), average(decelerations), variance(decelerations));
                    mainTabbedActivity.brakeRegistered(data);
                }
                decelerations.clear();
                break;

            case NONE:
                break;
        }
    }

    private float average(List<Float> list) {
        float sum = 0;
        for(float f : list) {
            sum += f;
        }
        return sum / (float)list.size();
    }

    private float variance(List<Float> list) {
        float average = average(list);

        List<Float> quads = new ArrayList<Float>();
        for(float f : list) {
            float value = (float) Math.pow(f - average, 2);
            quads.add(value);
        }
        return average(quads);
    }

    private boolean isBraking(float deceleration) {
        if(!decelerations.isEmpty()) {
            if(deceleration < 0) {
                return true;
            }
        } else {
            if(deceleration <= BRAKE_THRESHOLD) {
                return true;
            }
        }
        return false;
    }

    private BrakeValues isBraking() {
        List<Float> speeds = SessionFunctions.getLastElements(Session.getInstance().getSpeeds(), movingAverageSize + 2);
        List<Float> times = SessionFunctions.getLastElements(Session.getInstance().getTimes(), movingAverageSize + 2);
        List<Float> acceleration = SessionFunctions.differentiate(speeds, times);
        acceleration = SessionFunctions.clamp(acceleration, -30, 30);

        //TODO: Don't use average for the sum!!
        float deceleration = SessionFunctions.average(acceleration, movingAverageSize);

        if(isBraking(deceleration)) {
            if (!decelerations.isEmpty()) {
                return new BrakeValues(BrakeEventType.BRAKING, deceleration);
            } else {
                return new BrakeValues(BrakeEventType.START, deceleration);
            }
        } else {
            if(!decelerations.isEmpty()) {
                return new BrakeValues(BrakeEventType.END, deceleration);
            } else {
                return new BrakeValues(BrakeEventType.NONE, deceleration);
            }
        }
    }

    private void calculateBrakeResults() {

    }

    public class BrakeData {
        private float distance;
        private float startSpeed;
        private float endSpeed;
        private float averageDeceleration;
        private float brakeVariance;

        public BrakeData(float distance, float startSpeed, float endSpeed, float averageDeceleration, float brakeVariance) {
            this.distance = distance;
            this.startSpeed = startSpeed;
            this.endSpeed = endSpeed;
            this.averageDeceleration = averageDeceleration;
            this.brakeVariance = brakeVariance;
        }

        public float getBrakeVariance() {
            return brakeVariance;
        }

        public float getDistance() {
            return distance;
        }

        public float getStartSpeed() {
            return startSpeed;
        }

        public float getEndSpeed() {
            return endSpeed;
        }

        public float getAverageDeceleration() {
            return averageDeceleration;
        }

        @Override
        public String toString() {
            return String.format("Distance: %f\nStart Speed: %f\nEnd Speed: %f\nAvg. Dec: %f\n\n", distance, startSpeed, endSpeed, averageDeceleration);
        }
    }

    public class BrakeValues {
        BrakeEventType type;
        float deceleration;

        public BrakeValues(BrakeEventType type, float deceleration) {
            this.type = type;
            this.deceleration = deceleration;
        }
    }

    public enum BrakeEventType{
        START,
        BRAKING,
        END,
        NONE
    }
}
