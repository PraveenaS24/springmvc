package com.chainsys.springmvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.chainsys.springmvc.pojo.Appointment;
import com.chainsys.springmvc.pojo.Doctor;
import com.chainsys.springmvc.pojo.DoctorAppointmentsDTO;
import com.chainsys.springmvc.service.DoctorService;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
	@Autowired
	DoctorService drservice;

	@GetMapping("/doclist")
	public String getAllDoctor(Model model) {
		List<Doctor> doclist = drservice.getAllDoctor();
		model.addAttribute("alldoctor", doclist);
		return "list-doctors";
	}

	@GetMapping("/finddoctorid")
	public String findDoctorById(@RequestParam("docId") int id, Model model) {
		Doctor thedoc = drservice.findDoctorById(id);
		model.addAttribute("finddoctorbyid", thedoc);
		return "find-doctor-id-form";
	}

	@GetMapping("/adddocform")
	public String showAddForm(Model model) {
		Doctor thedoc = new Doctor();
		model.addAttribute("adddoctor", thedoc);
		return "add-doctor-form";
	}

	@PostMapping("/adddoc")
	public String addNewEmployees(@ModelAttribute("adddoctor") Doctor thedoc) {
		drservice.save(thedoc);
		return "redirect:/doctor/doclist";
	}

	@GetMapping("/updatedocform")
	public String showUpdateForm(@RequestParam("docId") int id, Model model) {
		Doctor thedoc = drservice.findDoctorById(id);
		model.addAttribute("updatedoctor", thedoc);
		return "update-doctor-form";
	}

	@PostMapping("/updatedoc")
	public String updateDoctor(@ModelAttribute("updatedoctor") Doctor thedoc) {
		drservice.save(thedoc);
		return "redirect:/doctor/doclist";
	}

	@GetMapping("/deletedoctor")
	public String deleteDoctors(@RequestParam("docId") int id) {
		drservice.deleteDoctors(id);
		return "redirect:/doctor/doclist";
	}

	@GetMapping("/getdocapp")
	public String getApppointments(@RequestParam("id") int id, Model model) {
		DoctorAppointmentsDTO dto = drservice.getDoctorAndAppointments(id);
		model.addAttribute("getdoc", dto.getDoctor());
		model.addAttribute("applist", dto.getAppointments());
		return "list-doctor-appointments";
	}

//localhost:8080/doctor/trans?id=22
	@GetMapping("/trans")
	public void testTransaction(@RequestParam("id") int id) {
		DoctorAppointmentsDTO dto = new DoctorAppointmentsDTO();
		Doctor dr = new Doctor();
		dr.setDoc_id(id);
		dr.setDoc_name("sheeni");
		Date drdob = new Date(82, 10, 14);
		dr.setDob(drdob);
		dr.setCity("vellore");
		dr.setFees(3500);
		dr.setPhone_no(9876424675L);
		dr.setFees(200);
		dr.setSpeciality("ENT");
		dto.setDoctor(dr);
		List<Appointment> applist = new ArrayList<Appointment>();
		for (int i = 0; i <= 2; i++) {
			Appointment app = new Appointment();
			app.setApp_id(i);
			Date appdt = new Date(24, 10, 2000);
			app.setApp_date(appdt);
			app.setDoc_id(id);
			app.setPatient_name("arivu");
			app.setFees_collected(i * 500);
			dto.addAppointment(app);

		}
		drservice.addDoctorAndAppointments(dto);
		System.out.println("Successfully Completed");
	}

}