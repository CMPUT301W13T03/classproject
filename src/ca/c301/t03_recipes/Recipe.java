package ca.c301.t03_recipes;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String instructions;
	private ArrayList<String> ingredients;
	private ArrayList<String> amounts;
	
	public Recipe() {
		this.name = "";
		this.instructions = "";
		this.ingredients = new ArrayList<String>();
		this.amounts = new ArrayList<String>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getInstructions() {
		return this.instructions;
	}
	
	public ArrayList<String> getIngredients() {
		return this.ingredients;
	}
	
	public ArrayList<String> getAmounts() {
		return this.amounts;
	}
	
	public String getIngredient(int i) {
		return this.ingredients.get(i);
	}
	
	public String getAmount(int i) {
		return this.amounts.get(i);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public void addIngredient(String ingredient) {
		this.ingredients.add(ingredient);
	}
	
	public void addAmount(String amount) {
		this.amounts.add(amount);
	}
	
	public void setIngredient(int i, String ingredient) {
		this.ingredients.set(i, ingredient);
	}
	
	public void setAmount(int i, String amount) {
		this.amounts.set(i, amount);
	}
	
	public void deleteIngredient(int i) {
		this.ingredients.remove(i);
		this.amounts.remove(i);
	}

}
