package org.springframework.samples.owlmarketplace.controller;
// Local project imports

import org.springframework.samples.owlmarketplace.model.Customer;
import org.springframework.samples.owlmarketplace.model.SearchObject;
import org.springframework.samples.owlmarketplace.service.CustomerService;
import org.springframework.samples.owlmarketplace.service.LovedListingService;
import org.springframework.samples.owlmarketplace.service.PersonnelService;
import org.springframework.samples.owlmarketplace.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class ListingSearchController {

	// SimpleDateFormat documentation (Java 17)
	// https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/text/SimpleDateFormat.html
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("E - MM/dd/yyyy h:m a (z)");

	@Autowired // To access list of all customers
	private CustomerService customerService;

	@Autowired // To access list of loan apps
	private SearchHistoryService searchHistoryService;

	@Autowired // To access list of all loans
	private LovedListingService lovedListingService;

	@Autowired // To access list of all personnel
	private PersonnelService personnelService;

	@RequestMapping("/searchForm/")
	public String searchForm(@RequestParam(value = "cId") long customerId, Model model) {
		Customer customer = customerService.getCustomerById(customerId);
		SearchObject searchObject = new SearchObject();

		searchObject.setCustomerId(customerId);
		// Set<Customer> customerSet = searchObject.getCustomers();
		// Add current customer to HashSet, which will be passed to template
		// customerSet.add(customer);
		// Pass model attributes to loanApp request template
		// model.addAttribute("customerSet", customerSet);
		// model.addAttribute("customer", customer);

		model.addAttribute("custFirstName", customer.getFirstName());
		model.addAttribute("cId", customerId);
		model.addAttribute("searchObject", searchObject);
		Date date = new Date(); // Today's date
		// Calendar used to add days to today's date
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return "listing_search_form";
	}

	@PostMapping("/searchListing")
	public String searchListing(@ModelAttribute("searchObject") SearchObject searchObject) {
		SearchObject searchObjectCopy = searchObject;
		Date date = new Date(); // Date of the search
		// Format the date as "DayOfTheWeek - MM/dd/yyyy h:m AM/PM (TimeZone)"
		String formattedDate = DATE_FORMAT.format(date);
		// Convert formatted date to string in search object
		searchObject.setDate(formattedDate);
		// Save the object to the repo using the service
		searchHistoryService.saveSearch(searchObject);
		return "redirect:/showSavedSearches/?cId=" + searchObjectCopy.getCustomerId();
	}

	// @RequestMapping("/showLoanAppProcess/")
	// public String showLoanAppProcess(@RequestParam(value = "lAId") long lAId,
	// @RequestParam(value = "pId") long PIDD,
	// Model model) {
	// Personnel personnel = personnelService.getPersonnelById(PIDD);
	// LoanApp loanApp = searchHistoryService.getSearchById(lAId);
	// System.out.println("Personnel ID: " + PIDD);
	// model.addAttribute("PIDD", PIDD);
	// model.addAttribute("personnel", personnel);
	// model.addAttribute("loanApp", loanApp);
	// return "show_loan_app_process";
	// }
	// @PostMapping("/processLoanApp/{l}/{p}")
	// public String processLoanApp(@ModelAttribute("loanApp") LoanApp loanApp,
	// @PathVariable("p") long personnelId) {
	// System.out.println("PERSONNEL ID (p): " + personnelId);
	// System.out.println("LOAN APP ID (Fro loanApp): " + loanApp.getlAId());
	// loanApp.setPersonnelId(personnelId);
	// loanApp.setDateOfLastUpdate(formatDate());
	// Personnel personnel = personnelService.getPersonnelById(personnelId);
	// if (loanApp.getStatus().equals("Approved")) {
	// loanApp.setApprovedPersonnelId(personnelId);
	// loanApp.setStatus("Approved");
	// searchHistoryService.saveSearchHistory2(loanApp);
	// Loan loan = new Loan();
	// loan.setlId(loanApp.getlAId());
	// System.out.println("Approved Loan: " + loan.toString());
	// lovedListingService.saveLoan(loan);
	// }
	// else if (loanApp.getStatus().equals("Documents Requested")) {
	// loanApp.setStatus("Documents Requested");
	// System.out.println("Documents Requested: " + loanApp.toString());
	// searchHistoryService.saveSearchHistory2(loanApp);
	// }
	// else if (loanApp.getStatus().equals("Not Approved")) {
	// loanApp.setStatus("Not Approved");
	// System.out.println("Loan App Not Approved: " + loanApp.toString());
	// searchHistoryService.saveSearchHistory2(loanApp);
	// }
	// else if (loanApp.getStatus().equals("Pending Review")) {
	// loanApp.setPersonnelId(0);
	// loanApp.setDateOfLastUpdate("Not yet processed");
	// System.out.println("Pending Review: " + loanApp.toString());
	// searchHistoryService.saveSearchHistory2(loanApp);
	// }
	// return "redirect:/showPersonnelLoanDashboard/?pId=" + personnelId;
	//
	// }
	//
	// public String formatDate() {
	// Date date = new Date();
	// String formattedDate = dateFormat.format(date);
	// return formattedDate;
	// }

}
// MISC CODE USED DURING DEV/TESTING
// @PostMapping("/loanApp")
// public String loanApp(@ModelAttribute("loanApp") LoanApp loanApp) {
// Date date = new Date();
// String formattedDate = dateFormat.format(date);
// System.out.println("Today's loan app date: " + formattedDate);
// loanApp.setDate(formattedDate);
// loanApp.calculateLoanAppIncome();
// loanAppService.saveLoanApp(loanApp);
// System.out.println("Loan App created: " + loanApp.toString());
// return "redirect:/showCustomerAppDashboard/?cId=" +
// loanApp.getPrimaryCustomer().getcId();
// }
// loan.setPrincipal(loanApp.getPrincipal());
// loan.setAPR(loanApp.getAPR());
// loan.setTerm(loanApp.getTerm());
// loan.setCustomers(loanApp.getCustomers());
// loan.setStartDate(loanApp.getRequestedStartDate());
// loan.setPersonnelId(personnelId);
// loan.calculateLoanValues();
