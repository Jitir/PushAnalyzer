package errouane.benjamin.pushanalizer;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import errouane.benjamin.pushanalizer.activities.MainTabbedActivity;

/**
 * Created by Benni on 15.12.2014.
 */
public class Simulator implements Runnable {
    private List<Float> times = new LinkedList<Float>();
    private List<Float> speeds = new LinkedList<Float>();
    private List<Integer> accelerometerX = new LinkedList<Integer>();
    private List<Integer> accelerometerY = new LinkedList<Integer>();
    private List<Integer> accelerometerZ = new LinkedList<Integer>();
    private MainTabbedActivity caller;
    private boolean running;
    private long startingTime;

    public Simulator(MainTabbedActivity caller, File inputFile) {
        this.caller = caller;
        parseFile(inputFile);
    }

    private void parseFile(File f) {
        int i = 0;
        try {
            Scanner sc = new Scanner(f);
            sc.useLocale(Locale.ENGLISH);

            while(sc.hasNext()) {
                times.add(sc.nextFloat());
                speeds.add(sc.nextFloat());
                accelerometerX.add(sc.nextInt());
                accelerometerY.add(sc.nextInt());
                accelerometerZ.add(sc.nextInt());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        Log.e("String", "Stopping thread");
        running = false;
    }

    @Override
    public void run() {
        startingTime = System.nanoTime();
        float currentTime;
        running = true;
        while(running) {
            if(times.isEmpty())
                break;

            currentTime = (float)(System.nanoTime() - startingTime) / 1000000000f;
            if(times.get(0) < currentTime) {
                int[] acc = {accelerometerX.get(0), accelerometerY.get(0), accelerometerZ.get(0)};
                caller.updateRealSpeed(speeds.get(0), 0, acc);
                times.remove(0);
                speeds.remove(0);
                accelerometerX.remove(0);
                accelerometerY.remove(0);
                accelerometerZ.remove(0);
            }
        }
    }
}
