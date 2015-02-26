package errouane.benjamin.pushanalizer.dataListener;

import errouane.benjamin.pushanalizer.algorithms.BrakeDetector;

/**
 * Created by Benni on 31.10.2014.
 */
public interface RotationDataListener {
    public void newRotationData(RotationDataEvent event);
    public void newPush();
    public void newBrake(BrakeDetector.BrakeData results);
}
