package org.md2k.datadiagnostic.marker.wireless;

import java.util.ArrayList;
import java.util.List;

import org.md2k.datadiagnostic.configurations.DDT_PARAMETERS;
import org.md2k.datadiagnostic.configurations.METADATA;
import org.md2k.datadiagnostic.struct.MarkedDataPoints;

public class DataLossMarker {
	public final List<MarkedDataPoints> markedWindows;

	public DataLossMarker() {
		markedWindows = new ArrayList<MarkedDataPoints>();
	}

	/**
	 * Marks a window as data loss if packets contained in a window is less than a threshold.
	 * 
	 * @param blankWindows {@link MarkedDataPoints}
	 * @param windowSize double
	 * @param samplingRate double
	 */
	public void packetLoss(List<MarkedDataPoints> blankWindows, double windowSize, double samplingRate) {
		long expectedSamples = 0;
		int size;

		expectedSamples = (long) ((windowSize/1000) * samplingRate);

		for (int i = 0; i < blankWindows.size(); i++) {
			if (blankWindows.get(i).getQuality() == 999) {
				size = blankWindows.get(i).getDataPoints().size();
				double actualSamples = (double)size/expectedSamples;
				if(actualSamples<DDT_PARAMETERS.MINIMUM_ACCEPTABLE_PACKET_LOSS){
					blankWindows.get(i).setQuality(METADATA.DATA_LOST);
				}
			}

		}
		markedWindows.addAll(blankWindows);
	}

}
