package org.springframework.samples.owlmarketplace.controller;

// Local project imports
import org.springframework.samples.owlmarketplace.model.Customer;
import org.springframework.samples.owlmarketplace.model.SearchObject;
import org.springframework.samples.owlmarketplace.service.CustomerService;
import org.springframework.samples.owlmarketplace.service.LovedListingService;
import org.springframework.samples.owlmarketplace.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController {

	@Autowired // To access list of customers
	private CustomerService customerService;

	@Autowired // To access list of loved listings
	private LovedListingService lovedListingService;

	@Autowired // To access list of search history
	private SearchHistoryService searchHistoryService;

	// Track authenticated customer to pass to dashboard template, etc.
	// Autowired not required for this variable
	private Customer currentCustomer;

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	Boolean customerAuthentication = false;

	@GetMapping("/showCustomerSignUpForm")
	public String showCustomerSignUpForm(Model model) {
		Customer customer = new Customer();
		// To use form fields to create customer object
		model.addAttribute("customer", customer);
		return "customer_signup";
	}

	@PostMapping("/customerSignUp")
	public String customerSignUp(@ModelAttribute("customer") Customer customer) {
		List<Customer> customerList = customerService.getAllCustomers();
		for (Customer c : customerList) {
			if (c.getEmail().equals(customer.getEmail())) {
				System.out.println("Customer email already in use");
				// Can potentially add a different template with error message.
				// Currently redirects to same form
				// if email is already in use
				return "redirect:/showCustomerSignUpForm";
			}
			else if (c.getPassword().length() > 6) {
				// return "redirect:/showCustomerSignUpForm";
			}
		} // Save customer if email/id not already in use
		customerService.saveCustomer(customer);
		System.out.println("CHECKING CUSTOMER MODEL DATA: " + customer.toString());
		// Return to login so customer can log in to dashboard with new credentials
		return "redirect:/showCustomerLoginForm";
	}

	@RequestMapping("/showSavedSearches/")
	public String showSavedSearches(@RequestParam(value = "cId") long customerId, Model model) {
		Customer customer = customerService.getCustomerById(customerId);

		List<SearchObject> allSearchObjects = searchHistoryService.getAllSearchHistory();
		ArrayList<SearchObject> savedSearchArrayList = new ArrayList();
		ArrayList<SearchObject> searchObjectArrayList = new ArrayList(allSearchObjects);
		for (SearchObject l : searchObjectArrayList) {
			// System.out.println(l.toString());
			// If the customer id matches the customer id associated with search object
			if (l.getCustomerId() == customer.getcId()) {
				// There is a customer match inside array list - add to customer's array
				// list
				savedSearchArrayList.add(l);
			}
		}
		// Pass customer loved property array list model attribute
		model.addAttribute("savedSearchArrayList", savedSearchArrayList);
		// Pass current customer attribute to HTML template to load customer data
		model.addAttribute("customer", customer);
		model.addAttribute("cId", customerId);
		return "saved_search_list";
	}

	// @RequestMapping("showLoanAppDetails/")
	// public String showLoanAppDetails(@RequestParam(value = "loanAppId") long loanAppId,
	// Model model) {
	// model.addAttribute("loanApp", searchHistoryService.getSearchById(loanAppId));
	// model.addAttribute("loanAppId", loanAppId);
	// return "work_in_progress";
	// }

	public void setCustomerAuthenticated() {
		customerAuthentication = true;
	}

	public void setCustomerNotAuthenticated() {
		customerAuthentication = false;
	}

}
// Code used during dev/testing
// @GetMapping("/showCustomerAppDashboard/{customer_id}")
// public String showCustomerAppDashboard(@PathVariable(value="customer_id") long
// customerId, Model model) {
// Customer customer = customerService.getCustomerById(customerId);
// List<LoanApp> allLoanApps = loanAppService.getAllLoanApps();
// ArrayList<LoanApp> lovedPropertyArrayList = new ArrayList();
// ArrayList<LoanApp> loanAppArrayList = new ArrayList(allLoanApps);
// for (LoanApp l : loanAppArrayList) {
// System.out.println(l.getCustomers().toString());
// // If Set<Customer> for loanApp contains current customer (toString will be printed to
// string with brackets)
// if (l.getCustomers().toString().contains(("[" + customer.toString() + "]"))) {
// lovedPropertyArrayList.add(l); // There is a customer match inside array list - add
// loan to array list
// }
// }
// // Pass customer loanApp array list to Customer Loan App Dashboard
// model.addAttribute("lovedPropertyArrayList", lovedPropertyArrayList);
// // Pass current customer attribute to HTML template to access customer specific data
// model.addAttribute("customer", customer);
// return "customer_app_dashboard";
// }

// @GetMapping("showLoanAppDetails/{loan_app_id}")
// public String showLoanAppDetails(@PathVariable(value="loan_app_id") long loanAppId,
// Model model){
// model.addAttribute("loanApp", loanAppService.getLoanAppById(loanAppId));
// return "work_in_progress";
// }

// @PostMapping("/logoutCustomer")
// public String logoutCustomer(){
// setCurrentCustomer(null);
// setCustomerNotAuthenticated();
// return "redirect:/";
// }
// @GetMapping("/showCustomerLoginForm")
// public String showCustomerLoginForm(Model model) {
// Customer customer = new Customer();
// model.addAttribute("customer", customer); // To use customer object for login
// authentication
// return "customer_login";
// @PostMapping ("/customerLogin")
// public String customerLogin(@ModelAttribute("customer")Customer customer) {
// List<Customer> listCustomers = customerService.getAllCustomers();
// // System.out.println(customer.toString());
// for (Customer c : listCustomers) { // Enhanced for loop to check all customers
// // If login email and password match iteration in customer list
// if (c.getEmail().equals(customer.getEmail()) &&
// c.getPassword().equals(customer.getPassword())) {
// setCurrentCustomer(c); // Authenticate/set the current customer object
// setCustomerAuthenticated();
// customerService.saveCustomer(c);
// System.out.println("Current Customer: " + getCurrentCustomer().toString());
// return "redirect:/showCustomerDashboard/?cId=" + c.getcId(); // Show dashboard upon
// authentication
//// return "redirect:/showCustomerDashboard/" + c.getcId(); // Show dashboard upon
// authentication
// }
// }
// System.out.println("Not authenticated");
// return"customer_login"; // Return to login form if not authenticated
// }

// @RequestMapping("/showCustomerDashboard/")
// public String showCustomerDashboard(@RequestParam(value="cId") long customerId,
// Model model) {
// List<Loan> allLoans = lovedListingService.getAllLoans();
//
// ArrayList<Loan> propertyArrayList = new ArrayList(allLoans);
// ArrayList<Loan> lovedPropertyArrayList = new ArrayList();
// for (Loan l : propertyArrayList) {
// System.out.println(l.getCustomers().toString());
// if (l.getCustomers().toString().contains(("[" + getCurrentCustomer().toString() +
// "]"))) {
// lovedPropertyArrayList.add(l);
// }
// }
// // Pass customer loan array list to Customer Loan Dashboard
// model.addAttribute("lovedPropertyArrayList", lovedPropertyArrayList);
// // Pass current customer attribute to HTML template to access customer specific
// data
// model.addAttribute("customer", customerService.getCustomerById(customerId));
// model.addAttribute("cId", customerId);
// return "loved_property_list";
// }
