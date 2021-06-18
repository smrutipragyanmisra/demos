
package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.example.demo.model.Employee;

import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

/**
 * Employee Repository test class.
 *
 * @author smrutipragyan.mishra
 */
@SpringBootTest
@TestPropertySource(locations = "/database.properties")
@TestConfiguration
public class EmployeeRepositoryTest {

	/** The embedded postgres. */
	private static EmbeddedPostgres embeddedPostgres;

	/** The employee repository. */
	@Autowired
	private EmployeeRepository employeeRepository;

	/** The username. */
	@Value("${postgres.datasource.username}")
	private String username;

	/** The password. */
	@Value("${postgres.datasource.password}")
	private String password;

	/** The database. */
	@Value("${postgres.datasource.database}")
	private String database;

	/** The host. */
	@Value("${postgres.datasource.host}")
	private String host;

	/** The port. */
	@Value("${postgres.datasource.port}")
	private int port;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeAll
	public static void setUp() throws Exception {
		embeddedPostgres = new EmbeddedPostgres(Version.V11_1);
	}

	/**
	 * Test case for injected components not null.
	 */
	@Test
	public void injectedComponentsAreNotNull() {
		assertNotNull(employeeRepository);
	}

	/**
	 * Test case for database connection.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void testConnection() throws IOException, SQLException {
		assertNotNull(username);
		assertNotNull(password);
		// starting Postgres final
		String url = embeddedPostgres.start(host, port, database, username, password);
		assertNotNull(url);
		// connecting to a running Postgres and feeding up the database
		final Connection connection = DriverManager.getConnection(url + "&ssl=require");
		assertNotNull(connection);
	}

	/**
	 * Test case to save employee.
	 */
	@Test
	public void testSaveEmployee() {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("emptest");
		employee.setEmail("emptestuser@entrustdatacard.com");
		employeeRepository.save(employee);

		assertTrue(employeeRepository.findById(1L) != null);
		Optional<Employee> employeeList = employeeRepository.findById(1L);
		assertEquals(employeeList.get().getName(), "emptest");

	}

	/**
	 * Test case for get employee by Id.
	 */
	@Test
	public void testGetEmployeeByIdTest() {
		Optional<Employee> employeeOptional = employeeRepository.findById(1L);

		assertThat(employeeOptional).hasValueSatisfying(employee -> {
			assertThat(employee.getId()).isNotNull();
			assertThat(employee.getName()).isEqualTo("emptest");
			assertThat(employee.getEmail()).isEqualTo("emptestuser@entrustdatacard.com");
		});
	}

	/**
	 * Test case for update employee by Id.
	 */
	@Test
	public void testUpdateEmployeeByIdTest() {
		Employee employee = new Employee();
		employee.setId(2L);
		employee.setName("emptest");
		employee.setEmail("emptestuser@entrustdatacard.com");
		final Employee persistedEmployee = employeeRepository.save(employee);

		assertEquals(persistedEmployee.getName(), "emptest");

		Employee employeeUpdate = new Employee();
		employeeUpdate.setId(2L);
		employeeUpdate.setName("testuser");
		employeeUpdate.setEmail("testuser@entrustdatacard.com");
		final Employee updatedEmployee = employeeRepository.save(employeeUpdate);

		assertEquals(updatedEmployee.getName(), "testuser");
		assertEquals(updatedEmployee.getEmail(), "testuser@entrustdatacard.com");
	}

	/**
	 * Test case for get employees.
	 */
	@Test
	public void testGetEmployeesTest() {
		Employee employee1 = new Employee();
		employee1.setId(3L);
		employee1.setName("emptest1");
		employee1.setEmail("emptestuser1@domain.com");
		employeeRepository.save(employee1);

		Iterable<Employee> employees = employeeRepository.findAll();
		assertEquals(3, StreamSupport.stream(employees.spliterator(), false).count());

		Collection<Employee> employeeCollection = new ArrayList<Employee>();
		// Iterate through the iterable to add element matching with employee Id into the collection
		for (Employee emp : employees)
			if (emp.getId() == employee1.getId()) {
				employeeCollection.add(emp);
			}
		// then
		assertThat(employeeCollection.stream().findFirst().get().getName()).isEqualTo(employee1.getName());
		assertThat(employeeCollection.stream().findFirst().get().getEmail()).isEqualTo(employee1.getEmail());
	}

	/**
	 * Test case for delete employee by Id.
	 */
	@Test
	public void testDeleteEmployeeByIdTest() {
		Employee employee = new Employee();
		employee.setId(3L);
		employee.setName("test1");
		employee.setEmail("tes1tuser@entrustdatacard.com");
		final Employee persistedEmployee = employeeRepository.save(employee);

		employeeRepository.deleteById(persistedEmployee.getId());

		assertFalse(employeeRepository.existsById(persistedEmployee.getId()));
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@AfterAll
	public static void tearDown() throws Exception {
		embeddedPostgres.getProcess().ifPresent(PostgresProcess::stop);
	}

}
