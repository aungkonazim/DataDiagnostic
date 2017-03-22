package org.md2k.datadiagnostic.configurations;

public class DDT_PARAMETERS {

	// stream name to process (values = rip, ecg, motionsense)
	public static final String STREAM_NAME = "ecg";

	// Initial window size in milliseconds
	public static final int WINDOW_SIZE = 60000;

	// Sampling rate. This is used to calculate packet loss
	public static final double RESPIRATION_SAMPLING_RATE = 21.33;
	public static final double ECG_SAMPLING_RATE = 64;
	public static final double MOTIONSENSE_SAMPLING_RATE = 16;
	public static final double MICROSOFT_BAND_SAMPLING_RATE = 6;

	// Value is in percentage of battery remaining. Min=0 and Max=100
	// It is used to calculate whether phone was off due to battery down or was
	// powered off
	public static final int PHONE_BATTERY_DOWN = 0;
	public static final int PHONE_POWERED_OFF = 10;

	// Value in battery voltage. Min=0 and Max=6
	// It is used to calculate whether sensor was off due to battery down or was
	// powered off
	public static final int MOTIONSENSE_BATTERY_DOWN = 1;
	public static final int AUTOSENSE_BATTERY_DOWN = 1;
	public static final int AUTOSENSE_POWERED_OFF = 4;

	// Signal quality threshold. 0.00003 for microsoft/motionsense band and for
	// autosense 1000
	public static final double VARIANCE_THRESHOLD = 0.00003;

	// Threshold to distinguish between a wireless disconnection and sensor-off
	public static final double VARIANCE_THRESHOLD_AUTOSENSE_UNAVAILABLE = 4000;
	public static final double VARIANCE_THRESHOLD_MOTIONSENSE_UNAVAILABLE = 0.005;


	// min 0 and max 1
	public static final double MINIMUM_ACCEPTABLE_PACKET_LOSS = 0.33;


}
