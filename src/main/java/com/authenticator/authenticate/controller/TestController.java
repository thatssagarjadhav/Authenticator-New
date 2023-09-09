package com.authenticator.authenticate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author sagar.jadhav
 *
 */
@Controller
public class TestController {

	@GetMapping(value = { "/" })
	public String showWelcomePage() {
		return "welcome.html";
	}

}
