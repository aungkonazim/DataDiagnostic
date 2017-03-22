package org.md2k.datadiagnostic.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataStatistics {
	List<Double> data;
	int size;

	public DataStatistics(List<Double> data) {
		this.data = data;
		size = data.size();
	}

	public double getMean() {
		double sum = 0.0;
		for (double a : data)
			sum += a;
		return sum / size;
	}

	// https://en.wikipedia.org/wiki/Chauvenet's_criterion
	public List<Double> StatisticalOutLierAnalysis() {
		if (data.size() == 0)
			return null;

		List<Double> normalNumbers = new ArrayList<Double>();
		List<Double> outLierNumbers = new ArrayList<Double>();
		double median = median();
		double standardDeviation = getStdDev();
		for (int i = 0; i < data.size(); i++) {
			if ((Math.abs(data.get(i) - median)) > (standardDeviation))
				outLierNumbers.add(data.get(i));
			else
				normalNumbers.add(data.get(i));
		}

		return normalNumbers;
	}

	public double getVariance() {
		double mean = getMean();
		double temp = 0;
		for (double a : data)
			temp += (a - mean) * (a - mean);
		return temp / size;
	}

	public double getStdDev() {
		return Math.sqrt(getVariance());
	}

	public double median() {
		Collections.sort(data);
		if (data.size() != 0) {
			if (data.size() % 2 == 0) {
				return (data.get((data.size() / 2) - 1) + data.get(data.size() / 2)) / 2.0;
			} else {
				return data.get(data.size() / 2);
			}
		}else{
			return 0;
		}
	}
}