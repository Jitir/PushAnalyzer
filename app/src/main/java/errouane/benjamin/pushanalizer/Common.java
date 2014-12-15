package errouane.benjamin.pushanalizer;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

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

    public static boolean isExternalStorageAvailable(Context context) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, R.string.sd_card_unavailable, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
