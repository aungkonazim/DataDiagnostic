/**
 * 
 */
package org.md2k.datadiagnostic.main;

import demo.SampleData;
import org.md2k.datadiagnostic.util.Util;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		long startTime = Util.getStartDayTime(1489051974250l);
		long endTime = Util.getEndDayTime(1489404734171l);

		new DiagnoseData(startTime, endTime);

		System.out.println("Diagnostic results are in: "+SampleData.OUTPUT_PATH);
	}
}
