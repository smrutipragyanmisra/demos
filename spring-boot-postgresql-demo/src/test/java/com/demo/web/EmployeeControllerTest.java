package com.demo.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.demo.SpringBootPostgresqlDemoApplication;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.web.EmployeeController;

/**
 * The Class EmployeeControllerTest.
 * @author smrutipragyan.mishra
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringBootPostgresqlDemoApplication.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Autowired
	EmployeeController employeeController;

	@MockBean
	private EmployeeRepository mockEmployeeRepository;

	@LocalServerPort
	private int localPort;

	@Autowired
	private TestRestTemplate testRestTemplate;

	/**
	 * Context loads.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void contexLoads() throws Exception {
		assertThat(employeeController).isNotNull();
	}

	
	/**
	 * Test add employee.
	 */
	@Test
	public void testAddEmployee() {
		Employee employee = new Employee(4L, "test4", "test4@domain.com");
		Mockito.when(mockEmployeeRepository.save(ArgumentMatchers.any())).thenReturn(employee);

		ResponseEntity<Employee> responseEntity = this.testRestTemplate
				.postForEntity("http://localhost:" + localPort + "/api/employees", employee, Employee.class);

		assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
		assertEquals(responseEntity.getBody().getId(), employee.getId());
		assertEquals(responseEntity.getBody().getName(), employee.getName());
		assertEquals(responseEntity.getBody().getEmail(), employee.getEmail());
	}

	/**
	 * Test get employee by id.
	 */
	@Test
	public void testGetEmployeeById() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		Employee employee = new Employee(5L, "test4", "test4@domain.com");
		Mockito.when(mockEmployeeRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(employee));

		ResponseEntity<Employee> responseEntityGET = this.testRestTemplate
				.getForEntity("http://localhost:" + localPort + "/api/employees/5", Employee.class);
		assertEquals(HttpStatus.OK.value(), responseEntityGET.getStatusCodeValue());
		assertEquals(responseEntityGET.getBody().getId(), employee.getId());
		assertEquals(responseEntityGET.getBody().getName(), employee.getName());
		assertEquals(responseEntityGET.getBody().getEmail(), employee.getEmail());
	}

	/**
	 * Test get all employees.
	 */
	@Test
	public void testGetAllEmployees() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		// given
		Employee employee1 = new Employee(1L, "test1", "test1@domain.com");
		Employee employee2 = new Employee(2L, "test2", "test2@domain.com");

		List<Employee> list = new ArrayList<Employee>();
		list.addAll(Arrays.asList(employee1, employee2));
		Mockito.when(mockEmployeeRepository.findAll()).thenReturn(list);

		ResponseEntity<Employee[]> response = this.testRestTemplate
				.getForEntity("http://localhost:" + localPort + "/api/employees", Employee[].class);
		Employee[] employees = response.getBody();

		assertThat(employees.length == 2);
		Collection<Employee> employeeCollection = new ArrayList<Employee>();

		// Iterate through the iterable to add each element into the collection
		for (Employee emp : employees)
			employeeCollection.add(emp);
		// then
		assertThat(employeeCollection.stream().findFirst().get().getName()).isEqualTo(employee1.getName());
	}
	
	/**
	 * Test delete employee by id.
	 */
	@Test
	public void testDeleteEmployeeById() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		Employee employee = new Employee(5L, "test5", "test5@domain.com");
		Mockito.when(mockEmployeeRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(employee));

		this.testRestTemplate.delete("http://localhost:" + localPort + "/api/employees/5");
		assertFalse(mockEmployeeRepository.existsById(employee.getId()));
	}
	
	/**
	 * Test update employee by id.
	 */
	@Test
	public void testUpdateEmployeeById() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		Employee employee = new Employee(5L, "test5", "test5@domain.com");
		Mockito.when(mockEmployeeRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(employee));

		this.testRestTemplate.put("http://localhost:" + localPort + "/api/employees/{id}", employee, 5);
	}

}
