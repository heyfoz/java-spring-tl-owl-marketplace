package org.springframework.samples.owlmarketplace.controller;
// Local project imports

import org.springframework.samples.owlmarketplace.model.Listing;
import org.springframework.samples.owlmarketplace.service.LovedListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ListingController {

	@Autowired
	LovedListingService lovedListingService;

	public Listing createListing() {
		Listing listing = new Listing();
		return listing;
	}

}
