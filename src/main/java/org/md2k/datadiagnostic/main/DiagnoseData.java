package org.md2k.datadiagnostic.main;

import java.util.ArrayList;
import java.util.List;

import org.md2k.datadiagnostic.configurations.DDT_PARAMETERS;
import org.md2k.datadiagnostic.data.CSVExporter;
import org.md2k.datadiagnostic.data.DataLoader;
import org.md2k.datadiagnostic.marker.SensorAttachmentMarker;
import org.md2k.datadiagnostic.marker.SensorDataQualityMarker;
import org.md2k.datadiagnostic.marker.power.BatteryDataMarker;
import org.md2k.datadiagnostic.marker.wireless.DataLossMarker;
import org.md2k.datadiagnostic.marker.wireless.SensorUnavailableMarker;
import org.md2k.datadiagnostic.struct.DataPoints;
import org.md2k.datadiagnostic.util.Util;
import org.md2k.datadiagnostic.windowing.FixedSizeWindowing;

import demo.SampleData;

public class DiagnoseData {

	/**
	 * This class is responsible to execute all the data-diagnostic algorithms
	 */

	DataLoader dataLoader;
	List<DataPoints> sensorData;
	List<DataPoints> phoneBatteryData;
	List<DataPoints> sensorBatteryData;

	FixedSizeWindowing fixedSizeWindowing;
	double samplingRate;

	/**
	 * Load data and create blank windows for a period of 24 hours
	 */
	public DiagnoseData(long startTime, long endTime) {
		sensorData = new ArrayList<DataPoints>();
		phoneBatteryData = new ArrayList<DataPoints>();
		sensorBatteryData = new ArrayList<DataPoints>();
		fixedSizeWindowing = new FixedSizeWindowing();
		dataLoader = new DataLoader();

		//in future, get this information from a database
		//TODO
		//long startTime = Util.getStartDayTime(1479491476135l);
		//long endTime = Util.getEndDayTime(1479951776137l);

		if (DDT_PARAMETERS.STREAM_NAME.equals("rip")) {
			phoneBatteryData = dataLoader.loadCSV(SampleData.PHONE_BATTERY, startTime, endTime);
			sensorBatteryData = dataLoader.loadCSV(SampleData.AUTOSENSE_BATTERY, startTime, endTime);

			samplingRate = DDT_PARAMETERS.RESPIRATION_SAMPLING_RATE;
			sensorData = dataLoader.loadCSV(SampleData.RIP_DATA, startTime, endTime);
			fixedSizeWindowing.blankWindows(sensorData, startTime, endTime, DDT_PARAMETERS.WINDOW_SIZE);
			diagnoseRIPData();
		} else if (DDT_PARAMETERS.STREAM_NAME.equals("ecg")) {
			phoneBatteryData = dataLoader.loadCSV(SampleData.PHONE_BATTERY, startTime, endTime);
			sensorBatteryData = dataLoader.loadCSV(SampleData.AUTOSENSE_BATTERY, startTime, endTime);
			
			samplingRate = DDT_PARAMETERS.ECG_SAMPLING_RATE;
			sensorData = dataLoader.loadCSV(SampleData.ECG_DATA, startTime, endTime);
			fixedSizeWindowing.blankWindows(sensorData, startTime, endTime, DDT_PARAMETERS.WINDOW_SIZE);
			diagnoseECGData();
		}else if (DDT_PARAMETERS.STREAM_NAME.equals("motionsense")) {
			phoneBatteryData = dataLoader.loadCSV(SampleData.PHONE_BATTERY, startTime, endTime);
			sensorBatteryData = dataLoader.loadCSV(SampleData.MOTIONSENSE_BATTERY, startTime, endTime);
			
			samplingRate = DDT_PARAMETERS.MOTIONSENSE_SAMPLING_RATE;
			sensorData = dataLoader.loadWristCSV(SampleData.MOTIONSENSE_ACCELEROMETER, startTime, endTime);
			fixedSizeWindowing.blankWindows(sensorData, startTime, endTime, DDT_PARAMETERS.WINDOW_SIZE);
			diagnoseMotionsenseData();
		} 
	}

	/**
	 * Diagnose RIP data. All algorithms are executed in a sequence:<br>
	 * 1 - Check phone battery <br>
	 * 2 - Check sensor battery <br>
	 * 3 - Check sensor unavailability<br>
	 * 4 - Check sensor improper attachment<br>
	 * 5 - Calculate packet loss<br>
	 * 6 - Mark sensor data quality<br>
	 */
	private void diagnoseRIPData() {
		
		BatteryDataMarker batteryDataMarker = new BatteryDataMarker();
		SensorUnavailableMarker sensorUnavailable = new SensorUnavailableMarker();
		SensorAttachmentMarker sensorAttachmentMarker = new SensorAttachmentMarker();
		DataLossMarker dataLossMarker = new DataLossMarker();
		SensorDataQualityMarker sensorSignalQualityMarker = new SensorDataQualityMarker();
		
		batteryDataMarker.phoneBatteryMarker(phoneBatteryData, fixedSizeWindowing.blankWindows);
		batteryDataMarker.senseBatteryMarker(sensorBatteryData, batteryDataMarker.markedWindowsPhone);
		sensorUnavailable.autoSenseWirelessDC(batteryDataMarker.markedWindowsAutosense);
		sensorAttachmentMarker.improperOrNoAttachmentRIP(sensorUnavailable.markedWindows);
		dataLossMarker.packetLoss(sensorAttachmentMarker.markedWindows, DDT_PARAMETERS.WINDOW_SIZE, samplingRate);
		sensorSignalQualityMarker.markWindowsQulaity(dataLossMarker.markedWindows);
		
		
		CSVExporter csvExporter = new CSVExporter();
		csvExporter.writeMarkedDataPointsToCSV(sensorSignalQualityMarker.markedWindows, SampleData.OUTPUT_PATH, "rip_diagnostic.txt");

	}
	
	/**
	 * Diagnose ECG data. All algorithms are executed in a sequence:<br>
	 * 1 - Check phone battery <br>
	 * 2 - Check sensor battery <br>
	 * 3 - Check sensor unavailability<br>
	 * 4 - Check sensor improper attachment<br>
	 * 5 - Calculate packet loss<br>
	 * 6 - Mark sensor data quality<br>
	 */
	private void diagnoseECGData() {
		BatteryDataMarker batteryDataMarker = new BatteryDataMarker();
		SensorUnavailableMarker sensorUnavailable = new SensorUnavailableMarker();
		SensorAttachmentMarker sensorAttachmentMarker = new SensorAttachmentMarker();
		DataLossMarker dataLossMarker = new DataLossMarker();
		SensorDataQualityMarker sensorSignalQualityMarker = new SensorDataQualityMarker();
		
		batteryDataMarker.phoneBatteryMarker(phoneBatteryData, fixedSizeWindowing.blankWindows);
		batteryDataMarker.senseBatteryMarker(sensorBatteryData, batteryDataMarker.markedWindowsPhone);
		sensorUnavailable.autoSenseWirelessDC(batteryDataMarker.markedWindowsAutosense);
		sensorAttachmentMarker.improperOrNoAttachmentECG(sensorUnavailable.markedWindows);
		dataLossMarker.packetLoss(sensorAttachmentMarker.markedWindows, DDT_PARAMETERS.WINDOW_SIZE, samplingRate);
		sensorSignalQualityMarker.markWindowsQulaity(dataLossMarker.markedWindows);
		
		CSVExporter csvExporter = new CSVExporter();
		csvExporter.writeMarkedDataPointsToCSV(sensorSignalQualityMarker.markedWindows, SampleData.OUTPUT_PATH, "ecg_diagnostic.txt");

	}
	
	/**
	 * Diagnose MotionSense wrist band data. All algorithms are executed in a sequence:<br>
	 * 1 - Check phone battery <br>
	 * 2 - Check sensor battery <br>
	 * 3 - Check sensor unavailability<br>
	 * 4 - Calculate packet loss<br>
	 * 5 - Mark sensor data quality<br>
	 */
	private void diagnoseMotionsenseData() {
		BatteryDataMarker batteryDataMarker = new BatteryDataMarker();
		SensorUnavailableMarker sensorUnavailable = new SensorUnavailableMarker();
		DataLossMarker dataLossMarker = new DataLossMarker();
		SensorDataQualityMarker sensorSignalQualityMarker = new SensorDataQualityMarker();
		
		batteryDataMarker.phoneBatteryMarker(phoneBatteryData, fixedSizeWindowing.blankWindows);
		batteryDataMarker.motionSenseBatteryMarker(sensorBatteryData, batteryDataMarker.markedWindowsPhone);
		sensorUnavailable.motionsenseWirelessDC(batteryDataMarker.markedWindowsPhone);
		dataLossMarker.packetLoss(batteryDataMarker.markedWindowsMotionsense, DDT_PARAMETERS.WINDOW_SIZE, samplingRate);
		sensorSignalQualityMarker.markWindowsQulaity(dataLossMarker.markedWindows);
		
		CSVExporter csvExporter = new CSVExporter();
		csvExporter.writeMarkedDataPointsToCSV(sensorSignalQualityMarker.markedWindows, SampleData.OUTPUT_PATH, "motionsense_diagnostic.txt");

	}
	
}
