package errouane.benjamin.pushanalizer.dataListener;

/**
 * Created by Benni on 31.10.2014.
 */
public class RotationDataEvent {
    private float rotationSpeed;
    private float speed;
    private float deltaTime;
    private float distance;

    public RotationDataEvent(float deltaTime, float rotationSpeed, float speed, float distance) {
        this.deltaTime = deltaTime;
        this.rotationSpeed = rotationSpeed;
        this.speed = speed;
        this.distance = distance;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public float getSpeed() {
        return speed;
    }

    public float getDistance() {
        return distance;
    }
}
