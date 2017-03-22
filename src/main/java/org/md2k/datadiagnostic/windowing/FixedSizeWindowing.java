package org.md2k.datadiagnostic.windowing;

import java.util.ArrayList;
import java.util.List;
import org.md2k.datadiagnostic.struct.*;

/**
 * Create fixed equal size windows of time-series data
 */
public class FixedSizeWindowing {

	
	public List<MarkedDataPoints> windows;
	public List<MarkedDataPoints> blankWindows;
	
	public FixedSizeWindowing(){
		windows = new ArrayList<MarkedDataPoints>();
		blankWindows = new ArrayList<MarkedDataPoints>();
	}
	
	/**
	 * This method will create n number of blank windows for 24 hours period
	 * and assign data to a window if there is any.
	 * 
	 * @param sensorRawData {@link DataPoints}
	 * @param startTime long start time of a day
	 * @param endTime long end time of a day
	 * @param size long size of a window in milliseconds
	 */
	public void blankWindows(List<DataPoints> sensorRawData, long startTime, long endTime, long size) {
		List<DataPoints> tempArray = new ArrayList<DataPoints>();
		
		long totalMinutes = ((endTime - startTime)/size);
		Math.round(totalMinutes);
		for(int i=0;i<totalMinutes;i++){
			long windowStartTime=startTime;
			 startTime = startTime+size;
			
			tempArray.add(new DataPoints(windowStartTime, 000)); 
			for (int j = 0; j < sensorRawData.size(); j++) {
				if(sensorRawData.get(j).getStartTimestamp()>=windowStartTime && sensorRawData.get(j).getStartTimestamp()<=startTime){
					tempArray.add(new DataPoints(sensorRawData.get(j).getStartTimestamp(), sensorRawData.get(j).getValue()));
				}
			}
			tempArray.add(new DataPoints(startTime, 000));
			
			 if(!tempArray.isEmpty()){
				 blankWindows.add(new MarkedDataPoints(tempArray, 999));
			 }
			tempArray.clear();
			
		}
	}

	/**
	 * Creates larger timestamp windows by merging small consecutive windows
	 * 
	 * @param windows timestamp windows 
	 * @param size Time difference between two windows to merge. For example, merge two windows if they are are 1 minute (size=60000) apart.
	 * @return ArrayList<DataPoints> merged windows in larger windows
	 */
	public List<DataPoints> mergeDataPointsWindows(List<DataPoints> windows, long size) {
		List<Long> temp = new ArrayList<Long>();
		List<DataPoints> mergedWindows = new ArrayList<DataPoints>();

		if (windows.size() == 1) {
			mergedWindows.add(new DataPoints(windows.get(0).getStartTimestamp(), windows.get(0).getEndTimestamp()));
		}
		for (int i = 0; i < windows.size() - 1; i++) {

			if (windows.get(i + 1).getStartTimestamp() - windows.get(i).getEndTimestamp() > size) {

				if (temp.size() == 0) {
					mergedWindows.add(new DataPoints(windows.get(i).getStartTimestamp(), windows.get(i).getEndTimestamp()));
				} else {
					mergedWindows.add(new DataPoints(temp.get(0), windows.get(i).getEndTimestamp()));
				}

				temp.clear();
			} else {
				temp.add(windows.get(i).getStartTimestamp());
				if (i == windows.size() - 2) {
					mergedWindows.add(new DataPoints(temp.get(0), windows.get(i + 1).getEndTimestamp()));
				}
			}
		}
		return mergedWindows;

	}
	
}
