package errouane.benjamin.pushanalizer.fragments.graph;

import com.androidplot.xy.XYSeries;

import errouane.benjamin.pushanalizer.session.Session;

/**
 * Created by Benni on 08.11.2014.
 */
public class PlotSeries implements XYSeries {
    private Session session;
    private ValueType valueType;

    public PlotSeries(Session session, ValueType valueType) {
        this.session = session;
        this.valueType = valueType;
    }

    @Override
    public int size() {
        switch (valueType) {
            case SPEED:
                return session.getSpeeds().size();

            case ACC_X:
                return session.getAccelerometerX().size();

            case ACC_Y:
                return session.getAccelerometerY().size();

            case ACC_Z:
                return session.getAccelerometerZ().size();

            default:
                throw new RuntimeException("Illegal ValueType: " + valueType);
        }
    }

    @Override
    public Number getX(int index) {
        return session.getTimes().get(index);
    }

    @Override
    public Number getY(int index) {
        switch (valueType) {
            case SPEED:
                return session.getSpeeds().get(index);

            case ACC_X:
                return session.getAccelerometerX().get(index);

            case ACC_Y:
                return session.getAccelerometerY().get(index);

            case ACC_Z:
                return session.getAccelerometerZ().get(index);

            default:
                throw new RuntimeException("Illegal ValueType: " + valueType);
        }
    }

    @Override
    public String getTitle() {
        switch (valueType) {
            case SPEED:
                return "Speed";

            case ACC_X:
                return "Accelerometer X";

            case ACC_Y:
                return "Accelerometer Y";

            case ACC_Z:
                return "Accelerometer Z";

            default:
                throw new RuntimeException("Illegal ValueType: " + valueType);
        }
    }

    public enum ValueType {
        SPEED,
        ACC_X,
        ACC_Y,
        ACC_Z
    }
}
