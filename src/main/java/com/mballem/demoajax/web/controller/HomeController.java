package com.mballem.demoajax.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String init() {
		return "redirect:/promocao/add";
	}
}
