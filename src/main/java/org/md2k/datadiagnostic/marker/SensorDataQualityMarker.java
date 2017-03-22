package org.md2k.datadiagnostic.marker;

import java.util.ArrayList;
import java.util.List;

import org.md2k.datadiagnostic.signalquality.algorithms.VarianceBasedDataQuality;
import org.md2k.datadiagnostic.struct.MarkedDataPoints;

public class SensorDataQualityMarker {

	public final List<MarkedDataPoints> markedWindows;

	public SensorDataQualityMarker() {
		markedWindows = new ArrayList<MarkedDataPoints>();
	}
	
	/**
	 * 
	 * @param markedWindows {@link MarkedDataPoints}
	 */
	public void markWindowsQulaity(List<MarkedDataPoints> markedWindows) {
		VarianceBasedDataQuality varianceBasedDataQuality = new VarianceBasedDataQuality();
		int quality;
		
		for (int i = 0; i < markedWindows.size(); i++) {
			if (markedWindows.get(i).getQuality() == 999) {
				quality = varianceBasedDataQuality.dataQualityMarker(markedWindows.get(i).getDataPoints());
				markedWindows.get(i).setQuality(quality);
			}
		}
		this.markedWindows.addAll(markedWindows);
	}
}
