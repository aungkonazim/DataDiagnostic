/**
 *
 */
package org.md2k.datadiagnostic.main;

import org.md2k.datadiagnostic.configurations.Config;
import org.md2k.datadiagnostic.util.Util;

public class main {

    /**
     * @param args
     */
    public static void main(String[] args) {

        long startTime = Util.getStartDayTime(Long.parseLong(Config.get("START_TIME")));
        long endTime = Util.getEndDayTime(Long.parseLong(Config.get("END_TIME")));

//        new DiagnoseData(startTime, endTime);
        new DiagnoseData(0, System.currentTimeMillis());

        System.out.println("Diagnostic results are in: " + Config.get("OUTPUT_PATH"));
    }
}
