package pl.breku.backend.server.invoice.input.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by brekol on 17.01.16.
 */
public class InvoiceDetails {

	private String number;

	private String name;

	private String numberOfDays;

	private String salary;

	private String value;

	private String projectName;

	public InvoiceDetails(String number, String name, String numberOfDays, String salary, String value, String projectName) {
		this.number = number;
		this.name = name;
		this.numberOfDays = numberOfDays;
		this.salary = salary;
		this.value = value;
		this.projectName = projectName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(String numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("number", number)
				.append("name", name)
				.append("numberOfDays", numberOfDays)
				.append("salary", "#hashed#")
				.append("value", "#hashed#")
				.append("projectName", projectName)
				.toString();
	}
}
