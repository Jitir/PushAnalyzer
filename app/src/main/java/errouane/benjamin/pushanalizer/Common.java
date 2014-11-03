package errouane.benjamin.pushanalizer;

/**
 * Created by Benni on 27.10.2014.
 */
public class Common {
    public static float lerp(float a, float b, float f)
    {
        return a + f * (b - a);
    }

    public static double rotationalSpeedToSpeed(float rotationalSpeed, float wheelDiameter) {
        return (double)rotationalSpeed * Math.PI * (double)wheelDiameter * 6.0 / 100000.0;
    }
}
