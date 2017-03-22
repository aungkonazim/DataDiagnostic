package org.md2k.datadiagnostic.data;

import java.util.List;

import org.md2k.datadiagnostic.struct.DataPoints;

public class DataLoader {

	/**
	 * Load sensor data data
	 * 
	 * @param csvFileName . 
	 * @return {@link DataPoints}
	 */
	public List<DataPoints> loadCSV(String csvFileName, long startTime, long endTime){
		CSVParser csvParser = new CSVParser();	
		return csvParser.importData(csvFileName, startTime, endTime);
	}
	
	/**
	 * Load wrist-band accelerometer data and compute magnitude of x,y, and z values.
	 * @param csvFileName
	 * @return {@link DataPoints}
	 */
	public List<DataPoints> loadWristCSV(String csvFileName, long startTime, long endTime){
		CSVParser csvParser = new CSVParser();	
		return csvParser.importWristData(csvFileName, startTime, endTime);
	}
}
