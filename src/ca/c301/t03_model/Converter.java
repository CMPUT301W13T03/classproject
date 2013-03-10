package ca.c301.t03_model;

import java.util.ArrayList;

public class Converter {

	public String[] convertList(ArrayList<Ingredient> ingredients) {
		
		ArrayList<String> stringList = new ArrayList<String>();
		
		for (int i = 0; i < ingredients.size(); i++) {
			stringList.add(ingredients.get(i).getName() + "\n" + String.valueOf(ingredients.get(i).getAmount()) + " " + ingredients.get(i).getUnitOfMeasurement());
		}
		
		String[] outArray = new String[stringList.size()];
		stringList.toArray(outArray);
		
		return outArray;
	}
}
