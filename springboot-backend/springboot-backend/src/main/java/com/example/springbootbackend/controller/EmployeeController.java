package com.example.springbootbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootbackend.exception.ResourceNotFoundException;
import com.example.springbootbackend.model.Employee;
import com.example.springbootbackend.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		var employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Employee with id : " + id));
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("employees/{id}")
	public ResponseEntity<Employee> updateEmployee (@PathVariable Long id, @RequestBody Employee employee) {
		var newEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Employee with id : " + id));
		
		newEmployee.setFirstName(employee.getFirstName());
		newEmployee.setLastName(employee.getLastName());
		newEmployee.setEmailId(employee.getEmailId());
		
		var updatedEmployee = employeeRepository.save(newEmployee);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		var employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Employee with id : " + id));
		employeeRepository.delete(employee);
		var response = new HashMap<String, Boolean>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
}
