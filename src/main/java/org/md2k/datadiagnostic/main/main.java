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

		long startTime = Util.getStartDayTime(1479491476135l);
		long endTime = Util.getEndDayTime(1479951776137l);
		new DiagnoseData(startTime, endTime);

		System.out.println("Diagnostic results are in: "+SampleData.OUTPUT_PATH);
	}
}
