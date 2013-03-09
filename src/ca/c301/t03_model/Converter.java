package ca.c301.t03_model;

import android.widget.EditText;

public class Converter {
	public double convertDouble(EditText input, int decimals) {
		double output = Double.valueOf(input.getText().toString());
		
		int val = 10;
		decimals--;
		
		while (decimals > 0) {
			val *= 10;
			decimals--;
		}
		
		return (double)Math.round(output * val) / val;
	}
	
	public String convertString(EditText input) {
		return input.getText().toString();
	}
}
