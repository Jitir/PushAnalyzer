package errouane.benjamin.pushanalizer.session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benni on 17.02.2015.
 */
public class SessionFunctions {
    public static List<Float> getLastElements(List<Float> input, int count) {
        List<Float> output = new ArrayList<Float>();

        int startIndex = Math.max(0, input.size() - count);
        for(int i = startIndex; i < input.size(); i++) {
            output.add(input.get(i));
        }

        return output;
    }

    public static List<Float> clamp(List<Float> input, float minValue, float maxValue) {
        List<Float> output = new ArrayList<Float>();

        for(int i = 0; i < input.size(); i++) {
            output.add( Math.max( Math.min(input.get(i), maxValue), minValue));
        }

        return output;
    }

    public static List<Float> differentiate(List<Float> input, List<Float> time) {
        if(input.size() != time.size())
            throw new IllegalArgumentException("input.size (" + input.size() + ") != time.size (" + time.size() + ")");

        List<Float> output = new ArrayList<Float>();
        output.add(0f);
        for(int i = 1; i < input.size(); i++) {
            double deltaInput = input.get(i) - input.get(i - 1);
            double deltaTime = time.get(i) - time.get(i - 1);

            if(deltaTime == 0) {
                output.add(output.get(i - 1));
            } else {
                output.add((float)(deltaInput / deltaTime));
            }
        }

        return output;
    }

    public static float average(List<Float> input, int averageSize) {
        List<Float> trimmed = getLastElements(input, averageSize);

        float output = 0;
        for(Float f : trimmed) {
            output += f;
        }

        return output / (float)averageSize;
    }
}
