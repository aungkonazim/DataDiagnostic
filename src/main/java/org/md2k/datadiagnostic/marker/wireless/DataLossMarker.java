package org.md2k.datadiagnostic.marker.wireless;

import org.md2k.datadiagnostic.configurations.Config;
import org.md2k.datadiagnostic.struct.MarkedDataPoints;

import java.util.ArrayList;
import java.util.List;

public class DataLossMarker {
    public final List<MarkedDataPoints> markedWindows;

    public DataLossMarker() {
        markedWindows = new ArrayList<MarkedDataPoints>();
    }

    /**
     * Marks a window as data loss if packets contained in a window is less than a threshold.
     *
     * @param blankWindows {@link MarkedDataPoints}
     * @param windowSize   double
     * @param samplingRate double
     */
    public void packetLoss(List<MarkedDataPoints> blankWindows, double windowSize, double samplingRate) {
        long expectedSamples = 0;
        int size;

        expectedSamples = (long) ((windowSize / 1000) * samplingRate);

        for (int i = 0; i < blankWindows.size(); i++) {
            if (blankWindows.get(i).getQuality() == 999) {
                size = blankWindows.get(i).getDataPoints().size();
                double actualSamples = (double) size / expectedSamples;
                if (actualSamples < Double.parseDouble(Config.get("MINIMUM_ACCEPTABLE_PACKET_LOSS"))) {
                    blankWindows.get(i).setQuality(Integer.parseInt(Config.get("DATA_LOST")));
                }
            }

        }
        markedWindows.addAll(blankWindows);
    }

}
