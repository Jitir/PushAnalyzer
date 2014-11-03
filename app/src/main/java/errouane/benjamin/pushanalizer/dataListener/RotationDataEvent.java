package errouane.benjamin.pushanalizer.dataListener;

/**
 * Created by Benni on 31.10.2014.
 */
public class RotationDataEvent {
    private float rotationSpeed;
    private float deltaTime;

    public RotationDataEvent(float deltaTime, float rotationSpeed) {
        this.deltaTime = deltaTime;
        this.rotationSpeed = rotationSpeed;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public float getDeltaTime() {
        return deltaTime;
    }
}
