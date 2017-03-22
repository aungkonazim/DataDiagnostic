package org.md2k.datadiagnostic.marker;

import java.util.ArrayList;
import java.util.List;

import org.md2k.datadiagnostic.configurations.METADATA;
import org.md2k.datadiagnostic.data.DataLoader;
import org.md2k.datadiagnostic.struct.MarkedDataPoints;
import org.md2k.datadiagnostic.struct.DataPoints;
import org.md2k.datadiagnostic.util.DataStatistics;

import demo.SampleData;

public class SensorAttachmentMarker {

	public final List<MarkedDataPoints> markedWindows;

	public SensorAttachmentMarker() {
		markedWindows = new ArrayList<MarkedDataPoints>();
	}

	/**
	 * This algorithm is based on a threshold to determine whether sensor is properly attached.
	 * @param markedWindows {@link MarkedDataPoints}
	 */
	public void improperOrNoAttachmentRIP(List<MarkedDataPoints> markedWindows) {
		DataLoader dataLoader = new DataLoader();

		long startTime = 0, endTime = 0;
		List<Double> tmp = new ArrayList<Double>();
		List<DataPoints> galvanicSkingResponse = new ArrayList<DataPoints>();
		galvanicSkingResponse = dataLoader.loadCSV(SampleData.AUTOSENSE_GSR);
		
		for (int i = 0; i < markedWindows.size(); i++) {
			if (markedWindows.get(i).getQuality() == 999) {

				
				startTime = markedWindows.get(i).getDataPoints().get(0).getStartTimestamp();
				endTime = markedWindows.get(i).getDataPoints().get(markedWindows.get(i).getDataPoints().size() - 1).getStartTimestamp();
				for (int j = 0; j < galvanicSkingResponse.size(); j++) {
					if (galvanicSkingResponse.get(j).getStartTimestamp() > startTime
							&& galvanicSkingResponse.get(j).getStartTimestamp() < endTime) {
						tmp.add(galvanicSkingResponse.get(j).getValue());
					}
				}
				DataStatistics statistics = new DataStatistics(tmp);
				if (statistics.median() < 750) {
					markedWindows.get(i).setQuality(METADATA.IMPROPER_ATTACHMENT);
				}
				if (statistics.median() > 1800) {
					markedWindows.get(i).setQuality(METADATA.SENSOR_OFF_BODY);
				}
			}

			tmp.clear();
		}
		this.markedWindows.addAll(markedWindows);
	}

	/**
	 * This algorithm is based on a threshold to determine whether sensor is properly attached.
	 * @param markedWindows {@link MarkedDataPoints}
	 */
	public void improperOrNoAttachmentMotionSense(List<MarkedDataPoints> markedWindows) {
		List<Double> tmp = new ArrayList<Double>();

		for (int i = 0; i < markedWindows.size(); i++) {
			int badPeaks = 0;
			if (markedWindows.get(i).getQuality() == 999) {

					for (int k = 0; k < markedWindows.get(i).getDataPoints().size(); k++) {
						tmp.add(markedWindows.get(i).getDataPoints().get(k).getValue());
						if (markedWindows.get(i).getDataPoints().get(k).getValue() > 3999
								|| markedWindows.get(i).getDataPoints().get(k).getValue() < 50) {
							badPeaks++;
							tmp.add(markedWindows.get(i).getDataPoints().get(k).getValue());
						}

					}
					double avg = (badPeaks) / markedWindows.get(i).getDataPoints().size();
					if (avg > 0.95) {
						markedWindows.get(i).setQuality(METADATA.SENSOR_OFF_BODY);
					} else {
						DataStatistics statistics = new DataStatistics(tmp);
						if (statistics.median() > 3800) {
							markedWindows.get(i).setQuality(METADATA.IMPROPER_ATTACHMENT);
						}

					}


			}
			tmp.clear();

		}
		this.markedWindows.addAll(markedWindows);
	}

	
	/**
	 * This algorithm is based on a threshold to determine whether sensor is properly attached.
	 * @param markedWindows {@link MarkedDataPoints}
	 */
	public void improperOrNoAttachmentECG(List<MarkedDataPoints> markedWindows) {
		List<Double> tmp = new ArrayList<Double>();

		for (int i = 0; i < markedWindows.size(); i++) {
			int badPeaks = 0;
			if (markedWindows.get(i).getQuality() == 999) {

					for (int k = 0; k < markedWindows.get(i).getDataPoints().size(); k++) {
						tmp.add(markedWindows.get(i).getDataPoints().get(k).getValue());
						if (markedWindows.get(i).getDataPoints().get(k).getValue() > 3999
								|| markedWindows.get(i).getDataPoints().get(k).getValue() < 50) {
							badPeaks++;
							tmp.add(markedWindows.get(i).getDataPoints().get(k).getValue());
						}

					}
					double avg = (badPeaks) / markedWindows.get(i).getDataPoints().size();
					if (avg > 0.95) {
						markedWindows.get(i).setQuality(METADATA.SENSOR_OFF_BODY);
					} else {
						DataStatistics statistics = new DataStatistics(tmp);
						if (statistics.median() > 3800) {
							markedWindows.get(i).setQuality(METADATA.IMPROPER_ATTACHMENT);
						}

					}


			}
			tmp.clear();

		}
		this.markedWindows.addAll(markedWindows);
	}
}
