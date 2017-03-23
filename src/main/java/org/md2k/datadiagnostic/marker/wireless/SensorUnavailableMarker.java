package org.md2k.datadiagnostic.marker.wireless;

import demo.SampleData;
import org.md2k.datadiagnostic.configurations.DDT_PARAMETERS;
import org.md2k.datadiagnostic.configurations.METADATA;
import org.md2k.datadiagnostic.data.DataLoader;
import org.md2k.datadiagnostic.struct.DataPoints;
import org.md2k.datadiagnostic.struct.MarkedDataPoints;
import org.md2k.datadiagnostic.util.DataStatistics;
import org.md2k.datadiagnostic.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains algorithms that distinguish various types (e.g., sensor
 * battery down vs. wireless disconnection) of sensor unavailability causes.
 */
public class SensorUnavailableMarker {

    public final List<MarkedDataPoints> markedWindows;

    long startTime = Util.getStartDayTime(1489051974250l);
    long endTime = Util.getEndDayTime(1489404734171l);

    public SensorUnavailableMarker(long startTime, long endTime) {
        markedWindows = new ArrayList<MarkedDataPoints>();
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Load accelerometer x,y,z values from a CSV file to calculate magnitude.
     *
     * @param markedWindows {@link MarkedDataPoints}
     */
    public void autoSenseWirelessDC(List<MarkedDataPoints> markedWindows) {
        DataLoader dataLoader = new DataLoader();
        List<DataPoints> accelerometerX = new ArrayList<DataPoints>();
        List<DataPoints> accelerometerY = new ArrayList<DataPoints>();
        List<DataPoints> accelerometerZ = new ArrayList<DataPoints>();
        List<DataPoints> accelerometerMagnitude = new ArrayList<DataPoints>();
        double magnitude;
        double x, y, z;
        long timestamp = 0;
        int size;

        accelerometerX = dataLoader.loadCSV(SampleData.AUTOSENSE_ACCELEROMETER_X, this.startTime, this.endTime);
        accelerometerY = dataLoader.loadCSV(SampleData.AUTOSENSE_ACCELEROMETER_Y, this.startTime, this.endTime);
        accelerometerZ = dataLoader.loadCSV(SampleData.AUTOSENSE_ACCELEROMETER_Z, this.startTime, this.endTime);

        size = Math.max(Math.max(accelerometerX.size(), accelerometerY.size()), accelerometerZ.size());

        for (int i = 0; i < size; i++) {
            if (accelerometerX.size() - 1 < i)
                x = 0;
            else
                x = accelerometerX.get(i).getValue();
            if (accelerometerY.size() - 1 < i)
                y = 0;
            else
                y = accelerometerY.get(i).getValue();
            if (accelerometerZ.size() - 1 < i)
                z = 0;
            else
                z = accelerometerZ.get(i).getValue();

            if (accelerometerX.size() == size)
                timestamp = accelerometerX.get(i).getStartTimestamp();
            else if (accelerometerY.size() == size)
                timestamp = accelerometerY.get(i).getStartTimestamp();
            else if (accelerometerZ.size() == size)
                timestamp = accelerometerZ.get(i).getStartTimestamp();

            magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
            accelerometerMagnitude.add(new DataPoints(timestamp, magnitude));

        }

        WirelessDC((List<DataPoints>) accelerometerMagnitude, markedWindows);
    }

    /**
     * @param markedWindows {@link MarkedDataPoints}
     */
    public void motionsenseWirelessDC(List<MarkedDataPoints> markedWindows) {
        DataLoader dataLoader = new DataLoader();
        List<DataPoints> accelerometerMagnitude = new ArrayList<DataPoints>();

        accelerometerMagnitude = dataLoader.loadWristCSV(SampleData.MOTIONSENSE_ACCELEROMETER, this.startTime, this.endTime);

        WirelessDC((List<DataPoints>) accelerometerMagnitude, markedWindows);

    }

    /**
     * It checks 1 minute window of data before a disconnection occurs to determine
     * whether it was due to a physical disconnection
     *
     * @param accelerometer {@link DataPoints}
     * @param markedWindows {@link MarkedDataPoints}
     */
    public void WirelessDC(List<DataPoints> accelerometer, List<MarkedDataPoints> markedWindows) {
        long startDCTime = 0;
        double threshold = 0;
        List<Double> tmp = new ArrayList<Double>();

        // determine the which method is calling WirelessDC
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        // decide threshold based on sensor type
        if (stackTraceElements[2].getMethodName().equals("autoSenseWirelessDC")) {
            threshold = DDT_PARAMETERS.VARIANCE_THRESHOLD_AUTOSENSE_UNAVAILABLE;
        } else if (stackTraceElements[2].getMethodName().equals("motionsenseWirelessDC")) {
            threshold = DDT_PARAMETERS.VARIANCE_THRESHOLD_MOTIONSENSE_UNAVAILABLE;
        }

        for (int i = 0; i < markedWindows.size(); i++) {
            if (markedWindows.get(i).getQuality() == METADATA.SENSOR_POWERED_OFF) {
                if (markedWindows.get(i - 1).getQuality() != METADATA.SENSOR_POWERED_OFF) {
                    // start disconnection time
                    startDCTime = markedWindows.get(i).getDataPoints().get(0).getStartTimestamp();
                }
                for (int j = 0; j < accelerometer.size(); j++) {
                    // only checks 1 minutes window before a disconnection
                    // happens
                    if (accelerometer.get(j).getStartTimestamp() <= startDCTime
                            && accelerometer.get(j).getStartTimestamp() >= (startDCTime - 60000)) {
                        tmp.add(accelerometer.get(j).getValue());
                    }
                }

                DataStatistics statistics = new DataStatistics(tmp);
                if (markedWindows.get(i).getQuality() == METADATA.SENSOR_POWERED_OFF
                        && markedWindows.get(i - 1).getQuality() != METADATA.SENSOR_POWERED_OFF) {
                    System.out.println(startDCTime + " - " + tmp.size() + " - " + statistics.getVariance());
                }
                if (statistics.getVariance() > threshold) {
                    for (int k = i; k < markedWindows.size() - 1; k++) {
                        if (markedWindows.get(k).getQuality() != METADATA.SENSOR_POWERED_OFF) {
                            break;
                        } else {
                            markedWindows.get(k).setQuality(METADATA.SENSOR_UNAVAILABLE);
                        }
                    }

                }
                tmp.clear();
            }

        }
        this.markedWindows.addAll(markedWindows);
    }
}
