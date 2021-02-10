package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Employee.
 * @author smrutipragyan.mishra
 */
@Entity
@Table(name = "employee")
public class Employee {
	
	/**
	 * Instantiates a new employee.
	 */
	public Employee() {

	}

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/** The name. */
	private String name;
	
	/** The email. */
	private String email;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Instantiates a new employee.
	 *
	 * @param name the name
	 * @param email the email
	 */
	public Employee(String name, String email) {
		this.name = name;
		this.email = email;
	}

	/**
	 * Instantiates a new employee.
	 *
	 * @param id the id
	 * @param name the name
	 * @param email the email
	 */
	public Employee(long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

}
