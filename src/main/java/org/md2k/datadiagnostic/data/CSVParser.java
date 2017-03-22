package org.md2k.datadiagnostic.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import org.md2k.datadiagnostic.struct.DataPoints;

public class CSVParser implements Iterable<DataPoints> {

    private final List<DataPoints> data;


    public CSVParser() {
        this.data = new ArrayList<DataPoints>();
    }

    /**
     * Import data from a CSV file. Format of a file shall be: <br>
     * Timestamp, value, sensor-signal-value
     * @param filename CSV file name.
     * @return {@link DataPoints}
     */
    public List<DataPoints> importData(String filename) {

        DataPoints tempPacket;

        String[] tokens;
        double data;
        long timestamp;

        File file = new File(filename);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                tokens = scanner.nextLine().split(",");
                double ts = Double.parseDouble(tokens[0]);
                timestamp = (long) ts;
                data = Double.parseDouble(tokens[2]);

                tempPacket = new DataPoints(timestamp, data);
                this.data.add(tempPacket);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Collections.sort(this.data);
        return this.data;
    }
    
    /**
     * Import data from a CSV file and calculate magnitude. Format of a file shall be: <br>
     * Timestamp, value, x,y,z
     * @param filename CSV file name.
     * @return {@link DataPoints}
     */
    public List<DataPoints> importWristData(String filename) {

        DataPoints tempPacket;

        String[] tokens;
       // double data;
        long timestamp;

        File file = new File(filename);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                tokens = scanner.nextLine().split(",");
                double ts = Double.parseDouble(tokens[0]);
                timestamp = (long) ts;
                //data = Double.parseDouble(tokens[2]);
                double magnitude = Math.sqrt(Math.pow(Double.parseDouble(tokens[2]), 2) + Math.pow(Double.parseDouble(tokens[3]), 2) + Math.pow(Double.parseDouble(tokens[4]), 2));
                tempPacket = new DataPoints(timestamp, magnitude);
                this.data.add(tempPacket);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Collections.sort(this.data);
        return this.data;
    }



    public void sort() {
        Collections.sort(this.data);
    }

    @Override
    public Iterator<DataPoints> iterator() {
        return this.data.iterator();
    }
}