/**
 *
 */
package org.md2k.datadiagnostic.main;

import org.md2k.datadiagnostic.configurations.Config;
import org.md2k.datadiagnostic.util.Util;

import java.io.File;

public class main {

    public static String currDir = "";
    public static String uid = "";
    /**
     * @param args
     */
    public static void main(String[] args) {

        long startTime = Util.getStartDayTime(Long.parseLong(Config.get("START_TIME")));
        long endTime = Util.getEndDayTime(Long.parseLong(Config.get("END_TIME")));
        String[] pids_rice = {
                "9770b2b6-f613-337f-ab3b-9c6363d03f65" //2006
                , "8be4f601-70ce-3e13-a321-b85ee84b37ce" // 2007
                , "58710f02-ac51-3f84-b863-1f6c7431030b" // 2008
                , "425bc129-f0f4-3d2d-a8a0-ef9cda7b8a9b" // 2009
                , "53172e1e-9787-3abf-846c-4d2f109e601f" // 2010
                , "9701a358-2a0e-335f-946b-750dba046cc9" // 2011
                , "24f579e5-a3e9-3895-aaaa-38953f91b6d1" // 2012
                , "47bf72de-2c23-38d6-a6ec-3f9d594cc04c" // 2013
                , "c4a349f7-8377-36b6-90aa-4cfd5efaa7d6" // 2014
                , "0b54f909-edfe-35f8-9ddd-661defe6533e" // 2015
                , "60f77474-ec57-32a5-906e-9ad19b2d9282" // 2015
                , "c1281762-1978-3512-811a-982c88c6d9fb" // 2016
                , "47b6462e-920b-3690-9519-e00e1cc7655d" // 2017
                , "c5264eec-309c-399d-9acd-5839d424296c" // 2018
        };
        String[] pids = {
                "0556b1df-0853-3662-a550-36808ef4546b", "e7e4ddec-ebf8-377d-8bb6-9c5415c28073"
        };


//        new DiagnoseData(startTime, endTime);
        String dir = Config.get("PATH");

//        processDiagnoseData(dir);

        processDiagnoseData(dir, pids);
    }

    private static void processDiagnoseData(String dir, String[] pids) {

        for (String pid : pids) {
            System.out.println("--------------------- PID = " +pid + "-------------------------------");
            currDir = dir+pid+"\\";
            uid = pid;
            new DiagnoseData(uid, 0, System.currentTimeMillis());
        }
        System.out.println("Diagnostic results are in: " + Config.get("OUTPUT_PATH"));
    }

    private static void processDiagnoseData(String dir) {
        File dirFile = new File(dir);
        for (File f:dirFile.listFiles()) {
            if (f.getName().length() < 30) continue;
            System.out.println("--------------------- PID = " +f.getName() + "-------------------------------");

            currDir = dir+f.getName()+"\\";
            uid = f.getName();
            new DiagnoseData(uid, 0, System.currentTimeMillis());
        }
        System.out.println("Diagnostic results are in: " + Config.get("OUTPUT_PATH"));
    }
}
