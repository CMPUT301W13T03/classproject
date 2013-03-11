package ca.c301.t03_model;

import java.io.IOException;
import java.io.Serializable;

/**
 * Ingredient object used in both recipes and virtual pantry to store name, amount, and unit of measurement
 */
public class Ingredient implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6601357183564369003L;
	/**
	 * @uml.property  name="name"
	 */
	
	/**
	 * Constructor with no given arguments
	 */
	public Ingredient(){
	}
	
	/**
	 * Constructor using a given name
	 * @param name Is the name of the ingredient
	 */
	public Ingredient(String name){
		this.name = name;
	}
	
	private String name;

	/**
	 * Getter of the property <tt>name</tt>
	 * @return  Returns the name.
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter of the property <tt>name</tt>
	 * @param name  The name to set.
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @uml.property  name="amount"
	 */
	private double amount;

	/**
	 * Getter of the property <tt>amount</tt>
	 * @return  Returns the amount.
	 * @uml.property  name="amount"
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Setter of the property <tt>amount</tt>
	 * @param amount  The amount to set.
	 * @uml.property  name="amount"
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @uml.property  name="unitOfMeasurement"
	 */
	private String unitOfMeasurement;

	/**
	 * Getter of the property <tt>unitOfMeasurement</tt>
	 * @return  Returns the unitOfMeasurement.
	 * @uml.property  name="unitOfMeasurement"
	 */
	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	/**
	 * Setter of the property <tt>unitOfMeasurement</tt>
	 * @param unitOfMeasurement  The unitOfMeasurement to set.
	 * @uml.property  name="unitOfMeasurement"
	 */
	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	/**
	 * @uml.property  name="id"
	 */
	private int id;

	/**
	 * Getter of the property <tt>id</tt>
	 * @return  Returns the id.
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter of the property <tt>id</tt>
	 * @param id  The id to set.
	 * @uml.property  name="id"
	 */
	public void setId(int id) {
		this.id = id;
	}

}
