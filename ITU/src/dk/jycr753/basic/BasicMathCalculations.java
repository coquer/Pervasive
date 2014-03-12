package dk.jycr753.basic;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BasicMathCalculations {

	public static double cutDoubleDecimals(double doubleNumber){
		NumberFormat numberFormat = DecimalFormat.getInstance();
		numberFormat.setRoundingMode(RoundingMode.FLOOR);
		numberFormat.setMinimumFractionDigits(0);
		numberFormat.setMaximumFractionDigits(4);
		String finalNumberFormatted = numberFormat.format(doubleNumber);
		double finalValue = Double.parseDouble(finalNumberFormatted);
		return finalValue;
	}


}
