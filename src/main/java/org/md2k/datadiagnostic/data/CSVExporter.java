package org.md2k.datadiagnostic.data;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.md2k.datadiagnostic.struct.MarkedDataPoints;
import org.md2k.datadiagnostic.struct.DataPoints;

public class CSVExporter {


	/**
	 * Write data to a CSV format.
	 * If no filename is provided then it will generate temp.csv file in an output folder
	 * @param windows {@link MarkedDataPoints}
	 * @param outputFolderPath String
	 * @param fileName String
	 */
	public static void writeMarkedDataPointsToCSV(List<MarkedDataPoints> windows, String outputFolderPath, String fileName) {
		
		if(fileName.equals("")){
			fileName = outputFolderPath+"temp.csv";
		}else{
			fileName = outputFolderPath+fileName;
		}
		List<DataPoints> dp = new ArrayList<DataPoints>();
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
			for (int i = 0; i < windows.size(); i++) {

				dp = windows.get(i).getDataPoints();
					writer.write(dp.get(0).getStartTimestamp() + ", " + dp.get(dp.size()-1).getStartTimestamp()+", "+windows.get(i).getQuality() + "\r");

			}

		} catch (UnsupportedEncodingException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Write data to a CSV format.
	 * If no filename is provided then it will generate temp.csv file in an output folder
	 * @param windows {@link DataPoints}
	 * @param outputFolderPath String
	 * @param fileName String
	 */
	public static void writeDataPointsToCSV(List<DataPoints> windows, String outputFolderPath, String fileName) {
		if(fileName.equals("")){
			fileName = outputFolderPath+"temp.csv";
		}else{
			fileName = outputFolderPath+fileName;
		}
		List<DataPoints> dp = new ArrayList<DataPoints>();
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"))) {
			for (int i = 0; i < windows.size(); i++) {
					writer.write(windows.get(i).getStartTimestamp() + ", " + windows.get(i).getValue() + "\r");

			}

		} catch (UnsupportedEncodingException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
