package com.chainsys.springmvc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chainsys.springmvc.dao.EmployeesDao;
import com.chainsys.springmvc.pojo.Employees;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@GetMapping("/list")
	public String getAllEmployees(Model model) {
		List<Employees> emplist = EmployeesDao.getAllEmployees();
		model.addAttribute("allemployees", emplist);
		return "list-employees";
	}

	@GetMapping("/addform")
	public String showAddForm(Model model) {
		Employees theEmp = new Employees();
		model.addAttribute("addemployee", theEmp);
		return "add-employee-form";
	}

	@PostMapping("/add")
	public String addNewEmployees(@ModelAttribute("addemployee") Employees theEmp) {
		EmployeesDao.insertEmployees(theEmp);
		return "redirect:/employee/list";
	}

	@GetMapping("/updateform")
	public String showUpdateForm(@RequestParam("empid") int id, Model model) {
		Employees theEmp = EmployeesDao.getEmployeesById(id);
		model.addAttribute("updateemployee", theEmp);
		return "update-employee-form";

	}

	@PostMapping("/updateemp")
	public String updateEmployees(@ModelAttribute("updateemployee") Employees theEmp) {
		EmployeesDao.updateEmployees(theEmp);
		return "redirect:/employee/list";
	}

	@GetMapping("/deleteemployee")
	public String deleteEmployees(@RequestParam("empid") int id) {
		EmployeesDao.deleteEmployees(id);
		return "redirect:/employee/list";
	}

	@GetMapping("/findemployeebyid")
	public String findEmployeeById(@RequestParam("empid") int id, Model model) {
		Employees theEmp = EmployeesDao.getEmployeesById(id);
		model.addAttribute("findemployeebyid", theEmp);
		return "find-employee-id-form";
	}
}
