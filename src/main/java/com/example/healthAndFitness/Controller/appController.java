package com.example.healthAndFitness.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class appController {
	
	@RequestMapping("/")
	
	public String welcome() {
		
		return "index.html";
	}

}
