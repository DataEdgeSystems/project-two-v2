package net.kzn.collaborationbackend.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "index";
	}

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

}
