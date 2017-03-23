package org.md2k.datadiagnostic.signalquality.algorithms;

import org.md2k.datadiagnostic.configurations.Config;
import org.md2k.datadiagnostic.struct.DataPoints;
import org.md2k.datadiagnostic.util.DataStatistics;

import java.util.ArrayList;
import java.util.List;


public class VarianceBasedDataQuality {

    // 0.00003 for microsoft band

    /**
     * This method uses variance of a window to evaluate the quality of signals in it.
     *
     * @param timestampsAndValues {@link DataPoints}
     * @return int data quality
     */
    public int dataQualityMarker(List<DataPoints> timestampsAndValues) {
        List<Double> normalValues = new ArrayList<Double>();
        List<Double> values = new ArrayList<Double>();

        for (int i = 0; i < timestampsAndValues.size(); i++) {
            values.add(timestampsAndValues.get(i).getValue());
        }

        DataStatistics statistics = new DataStatistics(values);
        normalValues.addAll(statistics.StatisticalOutLierAnalysis());

        // TO-DO
        // Mark window as jerks if outliers are 70% of a window.

        DataStatistics statistics2 = new DataStatistics(normalValues);
        double variance = statistics2.getVariance();

        if (variance < Double.parseDouble(Config.get("VARIANCE_THRESHOLD"))) {
            System.out.println("Off-body   -> " + variance);
            return Integer.parseInt(Config.get("SENSOR_OFF_BODY"));
        }
        return Integer.parseInt(Config.get("SENSOR_ON_BODY"));

    }
}
