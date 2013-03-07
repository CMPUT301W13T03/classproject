package ca.c301.t03_model;

public class Ingredient {

	/**
	 * @uml.property  name="name"
	 */
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

}
