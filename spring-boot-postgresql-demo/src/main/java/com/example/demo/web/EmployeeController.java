package com.example.demo.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.resource.EmployeeDTO;

/**
 * The Class EmployeeController.
 * @author smrutipragyan.mishra
 */
@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	/**
	 * Add employee.
	 *
	 * @param employee the employee
	 * @return the response entity
	 */
	@PostMapping("/employees")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		/*
		 * Employee persistentEmployee = new Employee();
		 * persistentEmployee.setEmail(employee.getEmail());
		 * persistentEmployee.setName(employee.getName());
		 */
		return new ResponseEntity<Employee>(employeeRepository.save(employee), HttpStatus.CREATED);
	}

	/**
	 * Get all the employees details.
	 *
	 * @return the all employees
	 */
	@GetMapping("/employees")
	public Iterable<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	/**
	 * Get employee details by employee id.
	 *
	 * @param id the id
	 * @return the employee by id
	 * @throws Exception the exception
	 */
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long id) throws Exception {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new Exception("Employee " + id + " not found"));
		return ResponseEntity.ok().body(employee);
	}

	/**
	 * Update employee details.
	 *
	 * @param id the id
	 * @param employee the employee
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long id, @RequestBody EmployeeDTO employee)
			throws Exception {
		Employee persistentEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new Exception("Employee " + id + " not found"));

		persistentEmployee.setName(employee.getName());
		persistentEmployee.setEmail(employee.getEmail());

		final Employee updatedEmployee = employeeRepository.save(persistentEmployee);
		return ResponseEntity.ok(updatedEmployee);
	}

	/**
	 * Delete employee details.
	 *
	 * @param id the id
	 * @return the map
	 * @throws Exception the exception
	 */
	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long id) throws Exception {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new Exception("Employee " + id + " not found"));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Employee deleted", Boolean.TRUE);
		return response;
	}

}
