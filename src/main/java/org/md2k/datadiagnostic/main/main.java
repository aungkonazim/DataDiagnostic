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

		long startTime = Util.getStartDayTime(1488208967076l);
		long endTime = Util.getEndDayTime(1488760488997l);

		new DiagnoseData(startTime, endTime);

		System.out.println("Diagnostic results are in: "+SampleData.OUTPUT_PATH);
	}
}
