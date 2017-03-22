package org.md2k.datadiagnostic.struct;


public class DataPoints  implements Comparable<DataPoints>{


    public long startTimestamp;
    public long endTimestamp;
    public double value;


    /**
     * DataPoint Constructor
     *
     * @param timestamp Time in milliseconds since Jan 1st, 1970
     * @param value a value corresponding to a timestamp
     */
    public DataPoints(long timestamp, double value) {
        this.startTimestamp = timestamp;
        this.value = value;
    }
    
    /**
     * DataPoint Constructor
     *
     * @param startTimestamp Start Time in milliseconds since Jan 1st, 1970
     * @param endTimestamp     End Time in milliseconds since Jan 1st, 1970
     */
    public DataPoints(long startTimestamp, long endTimestamp) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }


    /**
     * Constructor
     *
     * @param other DataPointArray object
     */
    public DataPoints(DataPoints other) {
        this.value = other.value;
        this.startTimestamp = other.startTimestamp;
    }

    /**
     * @return long start timestamp of a window
     */
    public long getStartTimestamp(){
		return startTimestamp;
	}
	
    /**
     * @return double 
     */
	public double getValue(){
		return value;
	}
	
	/**
	 * @return long end time stamp of a window
	 */
	public long getEndTimestamp(){
		return endTimestamp;
	}
	
	/**
	 * @param timestamp long
	 */
	public void setStartTimestamp(long timestamp){
		this.startTimestamp = timestamp;
	}
	
	/**
	 * @param value {@link Double} 
	 */
	public void setValue(double value){
		this.value = value;
	}
	
	/**
	 * @param endTimestamp {@link Long} end timestamp of a window
	 */
	public void setEndTimestamp(long endTimestamp){
		 this.endTimestamp = endTimestamp;
	}
	
    /**
     * @return String representation of a DataPoint object
     */
    @Override
    public String toString() {
        return "DP:(" + this.startTimestamp + "," + this.value + ")";
    }



	@Override
	public int compareTo(DataPoints o) {
		// TODO Auto-generated method stub
		 if (this.startTimestamp < o.startTimestamp) {
	            return -1; //"this" is before "o"
	        }else if (this.startTimestamp > o.startTimestamp) {
	            return 1; //"this" is before "o"
	        } else {
	            return 0; //"this" is after "o"
	        }
	}
	
	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof DataPoints)) {
	        return false;
	    }

	    DataPoints that = (DataPoints) other;

	    // Custom equality check here.
	    return this.startTimestamp==that.startTimestamp
	        && this.endTimestamp==that.endTimestamp;
	}
}
