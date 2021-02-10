package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

/**
 * User Repository for CRUD operation.
 *
 * @author smrutipragyan.mishra
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
