package org.springframework.samples.owlmarketplace.controller;

//Local project imports
import org.springframework.samples.owlmarketplace.model.Customer;
import org.springframework.samples.owlmarketplace.model.Listing;
import org.springframework.samples.owlmarketplace.service.CustomerService;
import org.springframework.samples.owlmarketplace.service.LovedListingService;
import org.springframework.samples.owlmarketplace.service.SearchHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
// Misc imports
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthenticationController {

	@Autowired // To access list of all customers
	private CustomerService customerService;

	@Autowired // To access list of loved listings repo
	private LovedListingService lovedListingService;

	@Autowired // To access search history repo
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

	public void setCustomerAuthenticated() {
		customerAuthentication = true;
	}

	public void setCustomerNotAuthenticated() {
		customerAuthentication = false;
	}

	Boolean customerAuthentication = false;

	@GetMapping("/")
	public String goHome() {
		if (currentCustomer != null) {
			return "redirect:/showLovedListings/?cId=" + currentCustomer.getcId();
		}
		else {
			return "index";
		}
	}

	@PostMapping("/customerLogin")
	public String customerLogin(@ModelAttribute("customer") Customer customer) {
		List<Customer> listCustomers = customerService.getAllCustomers();
		// System.out.println(customer.toString());
		for (Customer c : listCustomers) { // Enhanced for loop to check all customers
			// If login email and password match iteration in customer list
			if (c.getEmail().equals(customer.getEmail()) && c.getPassword().equals(customer.getPassword())) {
				setCurrentCustomer(c); // Authenticate/set the current customer object
				setCustomerAuthenticated();
				customerService.saveCustomer(c);
				System.out.println("Current Customer: " + getCurrentCustomer().toString());
				// Show dashboard upon authentication
				return "redirect:/showLovedListings/?cId=" + c.getcId();
			}
		}
		System.out.println("Not authenticated");
		return "customer_login"; // Return to login form if not authenticated
	}

	@PostMapping("/customerLogout")
	public String logoutCustomer() {
		setCurrentCustomer(null);
		setCustomerNotAuthenticated();
		return "redirect:/";
	}

	@GetMapping("/showCustomerLoginForm")
	public String showCustomerLoginForm(Model model) {
		Customer customer = new Customer();
		// To use customer object for login authentication
		model.addAttribute("customer", customer);
		return "customer_login";
	}

	@RequestMapping("/showLovedListings/")
	public String showLovedListings(@RequestParam(value = "cId") long customerId, Model model) {
		if (customerAuthentication = false) {
			Customer customer = new Customer();
			// To use customer object for login authentication
			model.addAttribute("customer", customer);
			return "customer_login";
		}
		List<Listing> allListings = lovedListingService.getAllListings();
		ArrayList<Listing> propertyArrayList = new ArrayList(allListings);
		ArrayList<Listing> lovedPropertyArrayList = new ArrayList();
		// Pass customer loan array list to Customer Loan Dashboard
		model.addAttribute("lovedPropertyArrayList", allListings);
		// Pass current customer attribute to HTML template to load customer data
		model.addAttribute("customer", customerService.getCustomerById(customerId));
		model.addAttribute("cId", customerId);
		return "loved_property_list";
	}

	@GetMapping("/showBingMapsSearch")
	public String showBingMapsSearch(Model model) {
		Customer customer = new Customer();
		// To use customer object for login authentication
		model.addAttribute("customer", customer);
		return "bing_maps_search";
	}

	@GetMapping("/showPrivacyPolicy")
	public String showPrivacyPolicy(Model model) {
		Customer customer = getCurrentCustomer();
		model.addAttribute("customer", customer);
		return "privacy_policy";
	}
}
// Misc code not used in final project
// return "redirect:/showCustomerDashboard/" + c.getcId();
// for (Loan l : propertyArrayList) {
// System.out.println(l.getCustomers().toString());
// if (l.getCustomers().toString().contains(("[" +
// getCurrentCustomer().toString() + "]"))) {
// lovedPropertyArrayList.add(l);
// }
// }
